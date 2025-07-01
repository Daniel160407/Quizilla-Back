package com.quizilla.service;

import com.quizilla.dto.GroupDto;
import com.quizilla.model.Group;
import com.quizilla.repository.GroupRepository;
import com.quizilla.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addGroup(GroupDto groupDto) {
        Group convertedGroup = modelConverter.convert(groupDto);
        groupRepository.save(convertedGroup);
    }
}
