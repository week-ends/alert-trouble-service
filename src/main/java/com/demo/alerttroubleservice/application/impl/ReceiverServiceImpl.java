package com.demo.alerttroubleservice.application.impl;

import com.demo.alerttroubleservice.application.ReceiverService;
import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.repository.ReceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiverServiceImpl implements ReceiverService {

    @Autowired
    private ReceiverRepository receiverRepository;

    @Override
    @Transactional
    public Receiver createReceiver(Receiver receiver) {
        String nickname = receiver.getNickname().toLowerCase();
        Optional<Receiver> existingReceiver = receiverRepository.findByNickname(nickname);
        if (existingReceiver.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        receiver.setNickname(nickname);
        System.out.println(receiver);
        return receiverRepository.save(receiver);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receiver> findAll(){
        return receiverRepository.findAll();
    };


    @Override
    @Transactional
    public void deleteReceiver(String nickname) {
        nickname = nickname.toLowerCase();
        Optional<Receiver> existingReceiver = receiverRepository.findByNickname(nickname);
        if (existingReceiver.isEmpty()) {
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        }
        receiverRepository.deleteByNickname(nickname);
    }
}

