package com.quizilla.service;

import com.quizilla.dto.GroupDto;
import com.quizilla.model.Group;
import com.quizilla.repository.GroupRepository;
import com.quizilla.util.ModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelConverter modelConverter) {
        this.groupRepository = groupRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<GroupDto> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Override
    public List<GroupDto> addGroup(GroupDto groupDto) {
        groupDto.setPoints(0d);
        Group convertedGroup = modelConverter.convert(groupDto);
        convertedGroup.setCorrectAnswer(2);
        groupRepository.save(convertedGroup);

        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Override
    public List<GroupDto> editGroup(GroupDto groupDto) {
        Optional<Group> groupOptional = groupRepository.findById(groupDto.getId());
        groupOptional.ifPresent(group -> {
            group.setName(groupDto.getName());
            group.setImageUrl(groupDto.getImageUrl());
            group.setPoints(groupDto.getPoints());
            groupRepository.save(group);
        });

        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Override
    public List<GroupDto> clearPointsForAllGroups() {
        List<Group> groups = groupRepository.findAll();
        groups.forEach(group -> {
            group.setPoints(0d);
            groupRepository.save(group);
        });

        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Override
    @Transactional
    public List<GroupDto> deleteGroup(Integer id) {
        groupRepository.deleteById(id);

        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Override
    public List<GroupDto> updatePointsFor(String groupName, double points) {
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        groupOptional.ifPresent(group -> {
            double currentPoints = group.getPoints();
            group.setPoints(currentPoints + points);
            groupRepository.save(group);
        });

        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Transactional
    @Override
    public void updateCorrectAnswerFor(String groupName, boolean isCorrect) {
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        groupOptional.ifPresent(group -> {
            group.setCorrectAnswer(isCorrect ? 0 : 1);
            groupRepository.save(group);
        });
    }

    @Override
    public void resetCorrectAnswers() {
        List<Group> groups = groupRepository.findAll();
        groups.forEach(group -> group.setCorrectAnswer(2));
        groupRepository.saveAll(groups);
    }
}
