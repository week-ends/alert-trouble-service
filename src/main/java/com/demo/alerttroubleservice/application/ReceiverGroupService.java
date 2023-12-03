package com.demo.alerttroubleservice.application;

import com.demo.alerttroubleservice.domain.ReceiverGroup;

import java.util.List;
import java.util.Optional;

public interface ReceiverGroupService {

    ReceiverGroup createReceiverGroup(ReceiverGroup receiverGroup);

    List<ReceiverGroup> findAll();

    void deleteReceiverGroup(String groupname);

    void joinReceivers(String receiverGroupName, List<String> receiverNicknames);
}
