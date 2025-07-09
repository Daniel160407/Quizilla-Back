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
    public void addGroup(GroupDto groupDto) {
        groupDto.setPoints(0d);
        Group convertedGroup = modelConverter.convert(groupDto);
        System.err.println(convertedGroup);
        groupRepository.save(convertedGroup);
    }

    @Override
    public List<GroupDto> editGroup(GroupDto groupDto) {
        Optional<Group>groupOptional = groupRepository.findById(groupDto.getId());
        groupOptional.ifPresent(group -> {
            group.setName(groupDto.getName());
            group.setPoints(groupDto.getPoints());
            groupRepository.save(group);
        });

        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }

    @Override
    @Transactional
    public List<GroupDto> deleteGroup(Integer id) {
        groupRepository.deleteById(id);

        List<Group> groups = groupRepository.findAll();
        return modelConverter.convertGroupsToDtoList(groups);
    }
}
