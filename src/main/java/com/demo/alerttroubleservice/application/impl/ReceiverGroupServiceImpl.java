package com.demo.alerttroubleservice.application.impl;

import com.demo.alerttroubleservice.application.ReceiverGroupService;
import com.demo.alerttroubleservice.domain.ReceiverGroup;
import com.demo.alerttroubleservice.domain.repository.ReceiverGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiverGroupServiceImpl implements ReceiverGroupService {

    @Autowired
    private ReceiverGroupRepository receiverGroupRepository;

    @Override
    @Transactional
    public ReceiverGroup createReceiverGroup(ReceiverGroup receiverGroup) {
        String groupname = receiverGroup.getGroupname().toLowerCase();
        Optional<ReceiverGroup> existingReceiverGroup = receiverGroupRepository.findByGroupname(groupname);
        if (existingReceiverGroup.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 그룹이름입니다.");
        }
        receiverGroup.setGroupname(groupname);
        System.out.println(receiverGroup);
        return receiverGroupRepository.save(receiverGroup);
    }

    @Override
    public Optional<ReceiverGroup> findByGroupname(String groupname) {
        return receiverGroupRepository.findByGroupname(groupname);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReceiverGroup> findAll(){
        return receiverGroupRepository.findAll();
    };


    @Override
    @Transactional
    public void deleteReceiverGroup(String groupname) {
        groupname = groupname.toLowerCase();
        Optional<ReceiverGroup> existingReceiverGroup = receiverGroupRepository.findByGroupname(groupname);
        if (existingReceiverGroup.isEmpty()) {
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        }
        receiverGroupRepository.deleteByGroupname(groupname);
    }
}

