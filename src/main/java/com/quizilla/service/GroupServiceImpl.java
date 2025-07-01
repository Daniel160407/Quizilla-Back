package com.quizilla.service;

import com.quizilla.dto.GroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{
    @Override
    public List<GroupDto> getGroups() {
        return List.of();
    }

    @Override
    public void addGroup(GroupDto groupDto) {

    }
}
