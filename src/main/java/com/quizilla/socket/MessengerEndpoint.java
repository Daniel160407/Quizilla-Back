package com.quizilla.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizilla.dto.GroupDto;
import com.quizilla.dto.MessageDto;
import com.quizilla.dto.QuizDto;
import com.quizilla.model.Group;
import com.quizilla.repository.GroupRepository;
import com.quizilla.service.GroupService;
import com.quizilla.util.Constants;
import com.quizilla.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Component
public class MessengerEndpoint extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    private WebSocketSession adminSession;
    private final List<String> answeredGroups = new ArrayList<>();
    private final ObjectMapper objectMapper;
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final ModelConverter modelConverter;
    private LocalTime quizStartTime;
    private QuizDto actualQuiz;

    @Autowired
    public MessengerEndpoint(GroupRepository groupRepository, GroupService groupService, ModelConverter modelConverter) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
        this.modelConverter = modelConverter;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);

        if (Constants.ADMIN_ROLE_STATIC.equals(messageDto.getSender())) {
            adminSession = session;
            handleAdminMessage(messageDto);
        } else if (Constants.CLIENT_ROLE_STATIC.equals(messageDto.getSender())) {
            handlePlayerMessage(messageDto);
        } else if (Constants.PROJECTOR_ROLE_STATIC.equals(messageDto.getSender())) {
            handleAdminMessage(messageDto);
        } else {
            handleAnswerMessage(messageDto);
        }
    }

    private void handleAdminMessage(MessageDto messageDto) throws Exception {
        if (Constants.QUESTION_STATIC.equals(messageDto.getType())) {
            answeredGroups.clear();

            String quizJson = messageDto.getPayload();
            MessageDto message = new MessageDto(Constants.ADMIN_ROLE_STATIC, Constants.QUESTION_STATIC, quizJson);
            String jsonToSend = objectMapper.writeValueAsString(message);
            sendToAll(jsonToSend);
        } else if (Constants.QUESTION_CANCEL_STATIC.equals(messageDto.getType())) {
            if (actualQuiz != null) {
                List<String> answers = Arrays.asList(actualQuiz.getAnswer().split(", "));

                if (!messageDto.getPayload().isEmpty() && isUnansweredQuestion(answers)) {
                    handleUnansweredQuestionPointsAwarding(Double.parseDouble(messageDto.getPayload()));
                }
            }
            actualQuiz = null;

            List<GroupDto> groupDtos = groupService.getGroups();
            String groupsJson = objectMapper.writeValueAsString(groupDtos);

            MessageDto message = new MessageDto(Constants.ADMIN_ROLE_STATIC, Constants.QUESTION_CANCEL_STATIC, groupsJson);
            String jsonToSend = objectMapper.writeValueAsString(message);
            sendToAll(jsonToSend);
            groupService.resetCorrectAnswers();
        } else if (Constants.QUIZ_START_STATIC.equals(messageDto.getType())) {
            actualQuiz = objectMapper.readValue(messageDto.getPayload(), QuizDto.class);

            List<GroupDto> groupDtos = groupService.getGroups();
            String groupsJson = objectMapper.writeValueAsString(groupDtos);

            quizStartTime = LocalTime.now();
            MessageDto message = new MessageDto(Constants.ADMIN_ROLE_STATIC, Constants.QUIZ_START_STATIC, groupsJson);
            String jsonToSend = objectMapper.writeValueAsString(message);
            sendToAll(jsonToSend);
        }
    }

    private void handlePlayerMessage(MessageDto messageDto) throws Exception {
        if (Constants.GROUP_CREATION_STATIC.equals(messageDto.getType())) {
            String groupJson = messageDto.getPayload();
            GroupDto groupDto = objectMapper.readValue(groupJson, GroupDto.class);
            List<GroupDto> groupDtos = groupService.addGroup(groupDto);

            String jsonToSend = objectMapper.writeValueAsString(groupDtos);
            MessageDto messageToSend = new MessageDto(Constants.SERVER_ROLE_STATIC, Constants.GROUP_CREATED_STATIC, jsonToSend);
            sendToAll(objectMapper.writeValueAsString(messageToSend));
        }
    }

    private void handleAnswerMessage(MessageDto messageDto) throws Exception {
        if (Constants.ANSWER_STATIC.equals(messageDto.getType())) {
            Map<String, Object> answerData = objectMapper.readValue(messageDto.getPayload(), Map.class);
            boolean isCorrect = Boolean.parseBoolean(answerData.get("isCorrect").toString());
            QuizDto quizDto = objectMapper.convertValue(answerData.get("quiz"), QuizDto.class);
            String groupName = messageDto.getSender();

            if (!answeredGroups.contains(groupName)) {
                answeredGroups.add(groupName);
                int position = answeredGroups.size();
                double pointsAwarded = 0;

                if (isCorrect) {
                    String difficulty = quizDto.getType();
                    int maxTime = switch (difficulty) {
                        case "C" -> 30;
                        case "B" -> 20;
                        case "A" -> 10;
                        default -> 30;
                    };
                    double multiplier = switch (position) {
                        case 1 -> 1;
                        case 2 -> 0.8;
                        case 3 -> 0.7;
                        default -> 0.5;
                    };
                    if (isLoosing(groupName)) {
                        pointsAwarded = calculatePointsAwarded(maxTime, quizDto.getPoints() * 2, multiplier);
                    } else {
                        pointsAwarded = calculatePointsAwarded(maxTime, quizDto.getPoints(), multiplier);
                    }

                } else {
                    List<String> answers = Arrays.asList(quizDto.getAnswer().split(", "));
                    boolean isUnansweredQuestion = isUnansweredQuestion(answers);

                    if (isUnansweredQuestion) {
                        pointsAwarded = -quizDto.getPoints() * 0.5;
                    }
                }

                groupService.updatePointsFor(groupName, pointsAwarded);
                groupService.updateCorrectAnswerFor(groupName, isCorrect);
                Optional<Group> groupOptional = groupRepository.findByName(groupName);
                GroupDto groupDto = modelConverter.convert(groupOptional.get());
                groupDto.setCorrectAnswer(isCorrect);
                String payloadJson = objectMapper.writeValueAsString(groupDto);

                MessageDto message = new MessageDto(Constants.SERVER_ROLE_STATIC, Constants.PLAYER_ANSWERED_STATIC, payloadJson);
                String messageJson = objectMapper.writeValueAsString(message);
                adminSession.sendMessage(new TextMessage(messageJson));
            }
        }
    }

    void sendToAll(String jsonToSend) {
        for (WebSocketSession wsSession : sessions) {
            if (wsSession.isOpen()) {
                try {
                    wsSession.sendMessage(new TextMessage(jsonToSend));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int calculatePointsAwarded(int maxTime, double maxPoint, double multiplier) {
        LocalTime currentTime = LocalTime.now();
        Duration duration = Duration.between(quizStartTime, currentTime);
        long responseTime = duration.getSeconds();

        if (responseTime <= 3) {
            return (int) Math.round(maxPoint * multiplier);
        }

        double rawPoints = (maxPoint * (1 - ((double) responseTime / (maxTime * 2)))) * multiplier;
        return (int) Math.round(rawPoints);
    }

    private boolean isUnansweredQuestion(List<String> answers) {
        for (String answer : answers) {
            if (answer.contains("**")) {
                return false;
            }
        }
        return true;
    }

    private void handleUnansweredQuestionPointsAwarding(double maxPoint) {
        List<Group> groupsAnswered = new ArrayList<>();
        List<Group> allGroups = groupRepository.findAll();
        for (Group allGroup : allGroups) {
            for (String answeredGroup : answeredGroups) {
                if (allGroup.getName().equals(answeredGroup)) {
                    groupsAnswered.add(allGroup);
                }
            }
        }

        allGroups.removeAll(groupsAnswered);
        allGroups.forEach(group -> {
            double existingPoints = group.getPoints();
            double updatedPoints = existingPoints + maxPoint;
            group.setPoints(updatedPoints);
            groupRepository.save(group);
        });
    }

    private boolean isLoosing(String groupName) {
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        if (groupOptional.isEmpty()) return false;

        Group group = groupOptional.get();
        List<Group> groups = groupRepository.findAll();

        for (Group listGroup : groups) {
            if (listGroup.getName().equals(group.getName())) continue;

            if (listGroup.getPoints() - group.getPoints() <= 100) {
                return false;
            }
        }

        return true;
    }

}