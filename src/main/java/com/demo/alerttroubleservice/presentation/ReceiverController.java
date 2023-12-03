package com.demo.alerttroubleservice.presentation;

import com.demo.alerttroubleservice.application.ReceiverService;
import com.demo.alerttroubleservice.domain.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receivers")
public class ReceiverController {

    @Autowired
    private ReceiverService receiverService;

    @PostMapping
    public ResponseEntity<Receiver> createReceiver(@RequestBody Receiver receiver) {
        Receiver createdReceiver = receiverService.createReceiver(receiver);
        return ResponseEntity.ok(createdReceiver);
    }
}

