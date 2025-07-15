package com.quizilla.controller;

import com.quizilla.dto.GroupDto;
import com.quizilla.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quizilla/group")
@CrossOrigin(origins = "*")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<?> getGroups() {
        List<GroupDto> groupDtos = groupService.getGroups();
        return ResponseEntity.ok(groupDtos);
    }

    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody GroupDto groupDto) {
        List<GroupDto> groupDtos = groupService.addGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupDtos);
    }

    @PutMapping
    public ResponseEntity<?> editGroup(@RequestBody GroupDto groupDto) {
        List<GroupDto> groupDtos = groupService.editGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupDtos);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteGroup(@RequestParam Integer id) {
        List<GroupDto> groupDtos = groupService.deleteGroup(id);
        return ResponseEntity.ok(groupDtos);
    }
}
