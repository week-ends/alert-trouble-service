package com.demo.alerttroubleservice.presentation;

import com.demo.alerttroubleservice.application.ReceiverGroupService;
import com.demo.alerttroubleservice.application.ReceiverService;
import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.ReceiverGroup;
import com.demo.alerttroubleservice.domain.ReceiverGroupMembersAssociation;
import com.demo.alerttroubleservice.domain.repository.ReceiverGroupMembersAssociationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNullElseGet;

@RestController
@RequestMapping("/api/receiver-groups")
public class ReceiverGroupController {

    @Autowired
    private ReceiverGroupService receiverGroupService;
    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private ReceiverGroupMembersAssociationRepository receiverGroupMembersAssociationRepository;




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

    @DeleteMapping("/{groupname}")
    public ResponseEntity<String> deleteReceiverGroup(@PathVariable String groupname) {
        receiverGroupService.deleteReceiverGroup(groupname);
        return ResponseEntity.ok(groupname + "는 정상적으로 탈퇴 처리되었습니다.");
    }

    @PostMapping("{receiverGroupName}/join")
    public ResponseEntity<?> joinReceivers(@PathVariable String receiverGroupName, @RequestBody List<String> receiverNicknames) {

        Optional<ReceiverGroup> receiverGroup = receiverGroupService.findByGroupname(receiverGroupName);
        if (receiverGroup.isEmpty()) {
            throw new EntityNotFoundException("Receiver group not found: " + receiverGroupName);
        }

        List<Receiver> receivers = receiverService.findByNicknames(receiverNicknames);
        if (receivers.size() == 0) {
            throw new EntityNotFoundException("Any Receiver not found: " + receiverNicknames);
        }


        for (Receiver receiver : receivers) {
            ReceiverGroupMembersAssociation association = new ReceiverGroupMembersAssociation(receiverGroup.orElseGet(()->null), receiver);
            receiverGroupMembersAssociationRepository.save(association);
        }

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{receiverGroupName}/leave")
    public ResponseEntity<?> leaveReceiverGroup(@PathVariable String receiverGroupName, @RequestParam String receiverNickname) {

        Optional<ReceiverGroup> receiverGroup = receiverGroupService.findByGroupname(receiverGroupName);
        if (receiverGroup.isEmpty()) {
            throw new EntityNotFoundException("Receiver group not found: " + receiverGroupName);
        }

        Optional<Receiver> receiver = receiverService.findByNickname(receiverNickname);
        if (receiver.isEmpty()) {
            throw new EntityNotFoundException("Receiver not found: " + receiverNickname);
        }

        // ReceiverGroupMembersAssociation에서 데이터 삭제
        List<ReceiverGroupMembersAssociation> associations = receiverGroupMembersAssociationRepository.findByReceiverGroupAndReceiver(receiverGroup.get(), receiver.get());
        receiverGroupMembersAssociationRepository.deleteAll(associations);

        // ReceiverGroup과 Receiver 삭제
        receiverGroupService.delete(receiverGroup.get());
        receiverService.delete(receiver.get());

        return ResponseEntity.ok().build();
    }
}
