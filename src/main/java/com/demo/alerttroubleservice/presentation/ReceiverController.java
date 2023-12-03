package com.demo.alerttroubleservice.presentation;

import com.demo.alerttroubleservice.application.ReceiverService;
import com.demo.alerttroubleservice.domain.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Receiver>> getAllReceivers() {
        List<Receiver> receivers = receiverService.findAll();
        return ResponseEntity.ok(receivers);
    }
}

