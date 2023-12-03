package com.demo.alerttroubleservice.application.presentation;

import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.ReceiverGroup;
import com.demo.alerttroubleservice.domain.repository.ReceiverGroupRepository;
import com.demo.alerttroubleservice.domain.repository.ReceiverRepository;
import com.demo.alerttroubleservice.presentation.ReceiverGroupController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ReceiverGroupControllerTest {

    @Autowired
    private ReceiverGroupController receiverController;

    @Autowired
    private ReceiverGroupRepository receiverGroupRepository;

    @AfterEach
    public void afterEach() {
        receiverGroupRepository.deleteAll();
    }

    @Test
    @DisplayName("알림그룹 생성")
    public void createReceiverGroup() {
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.setGroupname("testgroup");

        ResponseEntity<ReceiverGroup> response = receiverController.createReceiverGroup(receiverGroup);

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

        ResponseEntity<ReceiverGroup> response1 = receiverController.createReceiverGroup(receiverGroup1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        try {
            receiverController.createReceiverGroup(receiverGroup2);
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
        // API 호출
        ResponseEntity<List<ReceiverGroup>> response = receiverController.getAllReceiverGroups();

        // 응답 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReceiverGroup> receivers = response.getBody();
        assertEquals(2, receivers.size());
        assertEquals("testgroup1", receivers.get(0).getGroupname());
        assertEquals("testgroup2", receivers.get(1).getGroupname());
    }

    @Test
    @DisplayName("그룹 삭제")
    public void deleteReceiver() {
        // Receiver 생성
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.setGroupname("testgroup");
        receiverGroupRepository.save(receiverGroup);

        // API 호출
        receiverController.deleteReceiverGroup("testgroup");

        // receiverRepository 확인
        List<ReceiverGroup> receiverGroups = receiverGroupRepository.findAll();
        assertEquals(0, receiverGroups.size());
    }

}

