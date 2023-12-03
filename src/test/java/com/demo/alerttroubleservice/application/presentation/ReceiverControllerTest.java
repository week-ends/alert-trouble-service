package com.demo.alerttroubleservice.application.presentation;

import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.presentation.ReceiverController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ReceiverControllerTest {

    @Autowired
    private ReceiverController receiverController;

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
}

