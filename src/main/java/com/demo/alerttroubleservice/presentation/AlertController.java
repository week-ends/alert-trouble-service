package com.demo.alerttroubleservice.presentation;

import com.demo.alerttroubleservice.application.AlertService;
import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.ReceiverGroupMembersAssociation;
import com.demo.alerttroubleservice.domain.dto.AlertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping
    public ResponseEntity<?> alert(@RequestBody AlertRequest alertRequest) {

        List<String> receiverNicknames = alertRequest.getTarget();
        List<Long> receiverIds = alertService.getReceiverIds(receiverNicknames);

        // 알림을 전송
        sendAlert(alertRequest.getSeverity(), alertRequest.getMessage(), receiverIds);

        return ResponseEntity.ok().build();
    }
}
