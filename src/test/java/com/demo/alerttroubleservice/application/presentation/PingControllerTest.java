package com.demo.alerttroubleservice.application.presentation;

import com.demo.alerttroubleservice.presentation.PingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PingControllerTest {

    @Autowired
    private PingController pingController;

    @Test
    public void ping() {
        ResponseEntity<String> response = pingController.ping();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }
}


