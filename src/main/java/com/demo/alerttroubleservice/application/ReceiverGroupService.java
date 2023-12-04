package com.demo.alerttroubleservice.application;

import com.demo.alerttroubleservice.domain.ReceiverGroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReceiverGroupService {

    ReceiverGroup createReceiverGroup(ReceiverGroup receiverGroup);

    Optional<ReceiverGroup> findByGroupname(String groupname);

    List<ReceiverGroup> findAll();

    void deleteReceiverGroup(String groupname);

    void delete(ReceiverGroup receiverGroup);
}
