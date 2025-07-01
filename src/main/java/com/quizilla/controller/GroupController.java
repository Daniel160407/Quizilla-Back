package com.quizilla.controller;

import com.quizilla.dto.GroupDto;
import com.quizilla.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/quizilla/group")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<?> getGroup() {
        List<GroupDto> groupDtos = groupService.getGroups();
        return ResponseEntity.ok(groupDtos);
    }

    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody GroupDto groupDto) {
        groupService.addGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
