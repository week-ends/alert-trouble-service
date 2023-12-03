package com.demo.alerttroubleservice.application.presentation;

import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.repository.ReceiverRepository;
import com.demo.alerttroubleservice.presentation.ReceiverController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ReceiverControllerTest {

    @Autowired
    private ReceiverController receiverController;

    @Autowired
    private ReceiverRepository receiverRepository;

    @Test
    public void createReceiver() {
        Receiver receiver = new Receiver();
        receiver.setNickname("testuser");

        ResponseEntity<Receiver> response = receiverController.createReceiver(receiver);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(receiver.getNickname(), response.getBody().getNickname());
    }

    @Test
    public void createReceiverWithDuplicatedNickname() {
        Receiver receiver1 = new Receiver();
        receiver1.setNickname("testuser");

        Receiver receiver2 = new Receiver();
        receiver2.setNickname("testuser");

        ResponseEntity<Receiver> response1 = receiverController.createReceiver(receiver1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        try {
            receiverController.createReceiver(receiver2);
            fail("IllegalArgumentException이 발생해야 합니다.");
        } catch (IllegalArgumentException e) {
            assertEquals("이미 존재하는 닉네임입니다.", e.getMessage());
        }
    }
    @Test
    public void getAllReceivers() {
        Receiver receiver1 = new Receiver();
        receiver1.setNickname("testuser1");

        Receiver receiver2 = new Receiver();
        receiver2.setNickname("testuser2");

        receiverRepository.save(receiver1);
        receiverRepository.save(receiver2);
        // API 호출
        ResponseEntity<List<Receiver>> response = receiverController.getAllReceivers();

        // 응답 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Receiver> receivers = response.getBody();
        assertEquals(2, receivers.size());
        assertEquals("testuser1", receivers.get(0).getNickname());
        assertEquals("testuser2", receivers.get(1).getNickname());
    }

}

