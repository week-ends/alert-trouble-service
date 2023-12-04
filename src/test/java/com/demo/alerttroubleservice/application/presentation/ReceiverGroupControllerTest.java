package com.demo.alerttroubleservice.application.presentation;

import com.demo.alerttroubleservice.application.ReceiverService;
import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.ReceiverGroup;
import com.demo.alerttroubleservice.domain.ReceiverGroupMembersAssociation;
import com.demo.alerttroubleservice.domain.repository.ReceiverGroupMembersAssociationRepository;
import com.demo.alerttroubleservice.domain.repository.ReceiverGroupRepository;
import com.demo.alerttroubleservice.domain.repository.ReceiverRepository;
import com.demo.alerttroubleservice.presentation.ReceiverController;
import com.demo.alerttroubleservice.presentation.ReceiverGroupController;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ReceiverGroupControllerTest {

    @Autowired
    private ReceiverController receiverController;
;
    @Autowired
    private ReceiverGroupController receiverGroupController;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private ReceiverRepository receiverRepository;
    @Autowired
    private ReceiverGroupRepository receiverGroupRepository;

    @Autowired
    private ReceiverGroupMembersAssociationRepository receiverGroupMembersAssociationRepository;

    @AfterEach
    public void afterEach() {
        receiverGroupRepository.deleteAll();
        receiverRepository.deleteAll();
    }

    @Test
    @DisplayName("알림그룹 생성")
    public void createReceiverGroup() {
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.setGroupname("testgroup");

        ResponseEntity<ReceiverGroup> response = receiverGroupController.createReceiverGroup(receiverGroup);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(receiverGroup.getGroupname(), response.getBody().getGroupname());
    }

    @Test
    @DisplayName("알림그룹 생성시 중복 그룹네임 검사")
    public void createReceiverWithDuplicatedNickname() {
        ReceiverGroup receiverGroup1 = new ReceiverGroup();
        receiverGroup1.setGroupname("testgroup");

        ReceiverGroup receiverGroup2 = new ReceiverGroup();
        receiverGroup2.setGroupname("testgroup");

        ResponseEntity<ReceiverGroup> response1 = receiverGroupController.createReceiverGroup(receiverGroup1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        try {
            receiverGroupController.createReceiverGroup(receiverGroup2);
            fail("IllegalArgumentException이 발생해야 합니다.");
        } catch (IllegalArgumentException e) {
            assertEquals("이미 존재하는 그룹이름입니다.", e.getMessage());
        }
    }
    @Test
    @DisplayName("전체 그룹 조회")
    public void getAllReceivers() {
        ReceiverGroup receiverGroup1 = new ReceiverGroup();
        receiverGroup1.setGroupname("testgroup1");

        ReceiverGroup receiverGroup2 = new ReceiverGroup();
        receiverGroup2.setGroupname("testgroup2");

        receiverGroupRepository.save(receiverGroup1);
        receiverGroupRepository.save(receiverGroup2);

        ResponseEntity<List<ReceiverGroup>> response = receiverGroupController.getAllReceiverGroups();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReceiverGroup> receivers = response.getBody();
        assertEquals(2, receivers.size());
        assertEquals("testgroup1", receivers.get(0).getGroupname());
        assertEquals("testgroup2", receivers.get(1).getGroupname());
    }

    @Test
    @DisplayName("그룹 삭제")
    public void deleteReceiver() {
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.setGroupname("testgroup");
        receiverGroupRepository.save(receiverGroup);

        receiverGroupController.deleteReceiverGroup("testgroup");

        List<ReceiverGroup> receiverGroups = receiverGroupRepository.findAll();
        assertEquals(0, receiverGroups.size());
    }


    @Test
    @DisplayName("알림그룹 가입")
    public void joinReceivers() {
        // Create a receiver group
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.setGroupname("test-group");
        receiverGroupRepository.save(receiverGroup);

        // Create receivers
        Receiver receiver1 = new Receiver();
        receiver1.setNickname("user1");
        receiverRepository.save(receiver1);
        Receiver receiver2 = new Receiver();
        receiver2.setNickname("user2");
        receiverRepository.save(receiver2);

        // Send join request
        receiverGroupController.joinReceivers("test-group", Arrays.asList("user1", "user2"));

        // Verify receiver group members association
        List<ReceiverGroupMembersAssociation> associations = receiverGroupMembersAssociationRepository.findAll();
        assertThat(associations.size()).isEqualTo(2);
//        assertThat(associations).containsExactlyInAnyOrder(
//                new ReceiverGroupMembersAssociation(receiverGroup, receiver1),
//                new ReceiverGroupMembersAssociation(receiverGroup, receiver2)
//        );
    }

    @Test
    @DisplayName("알림그룹이 없는 경우")
    public void joinReceivers_notFoundReceiverGroup() {
        // Send join request with non-existent receiver group
        try {
            receiverGroupController.joinReceivers("non-existent-receiver-group", List.of("user1"));
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Receiver group not found: non-existent-receiver-group");
        }
    }

    @Test
    @DisplayName("사용자가 없는 경우")
    public void joinReceivers_notFoundReceivers() {
        // Create a receiver group
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.setGroupname("test-group");
        receiverGroupRepository.save(receiverGroup);

        // Send join request with non-existent receiver
        try {
            receiverGroupController.joinReceivers("test-group", List.of("non-existent-receiver"));
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Any Receiver not found: [non-existent-receiver]");
        }
    }

}

