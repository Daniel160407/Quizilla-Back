package com.quizilla.service;

import com.quizilla.dto.GroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    List<GroupDto> getGroups();
    void addGroup(GroupDto groupDto);
}
