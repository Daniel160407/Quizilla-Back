package com.quizilla.service;

import com.quizilla.dto.GroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    List<GroupDto> getGroups();

    List<GroupDto> addGroup(GroupDto groupDto);

    List<GroupDto> editGroup(GroupDto groupDto);

    List<GroupDto> clearPointsForAllGroups();

    List<GroupDto> deleteGroup(Integer id);

    List<GroupDto> updatePointsFor(String groupName, double points);

    void updateCorrectAnswerFor(String groupName, boolean isCorrect);

    void resetCorrectAnswers();
}
