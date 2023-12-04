package com.demo.alerttroubleservice.application;

import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.ReceiverGroupMembersAssociation;
import com.demo.alerttroubleservice.domain.repository.ReceiverGroupMembersAssociationRepository;
import com.demo.alerttroubleservice.domain.repository.ReceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {
    @Autowired
    private ReceiverRepository receiverRepository;

    @Autowired
    private ReceiverGroupMembersAssociationRepository receiverGroupMembersAssociationRepository;
    public List<Long> getReceiverIds(List<String> receiverNicknames) {

        if (receiverNicknames.contains("@all")) {
            return receiverRepository.findAll().stream().map(Receiver::getId).collect(Collectors.toList());
        }

        List<Long> receiverIds = new ArrayList<>();
        for (String receiverNickname : receiverNicknames) {
            if (receiverNickname.startsWith("@")) {
                receiverIds.add(receiverRepository.findByNickname(receiverNickname.substring(1)).getId());
            }
        }

        // 대상자 목록에서 @@가 붙어있는 경우
        for (String receiverNickname : receiverNicknames) {
            if (receiverNickname.startsWith("@@")) {
                List<Long> groupIds = receiverGroupMembersAssociationRepository.findGroupIdsByGroupName(receiverNickname.substring(2));
                receiverIds.addAll(groupIds.stream().map(receiverGroupMembersAssociationRepository::findByGroupId).flatMap(List::stream).map(ReceiverGroupMembersAssociation::getReceiverId).collect(Collectors.toList()));
            }
        }

        return receiverIds.stream().distinct().collect(Collectors.toList());
    }
}
