package com.demo.alerttroubleservice.presentation;

import com.demo.alerttroubleservice.application.ReceiverGroupService;
import com.demo.alerttroubleservice.domain.ReceiverGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receiver-groups")
public class ReceiverGroupController {

    @Autowired
    private ReceiverGroupService receiverGroupService;

    @PostMapping
    public ResponseEntity<ReceiverGroup> createReceiverGroup(@RequestBody ReceiverGroup receiverGroup) {
        ReceiverGroup createdReceiverGroup = receiverGroupService.createReceiverGroup(receiverGroup);
        return ResponseEntity.ok(createdReceiverGroup);
    }

    @GetMapping
    public ResponseEntity<List<ReceiverGroup>> getAllReceiverGroups() {
        List<ReceiverGroup> receiverGroups = receiverGroupService.findAll();
        return ResponseEntity.ok(receiverGroups);
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<String> deleteReceiverGroup(@PathVariable String nickname) {
        receiverGroupService.deleteReceiverGroup(nickname);
        return ResponseEntity.ok(nickname + "는 정상적으로 탈퇴 처리되었습니다.");
    }
}

