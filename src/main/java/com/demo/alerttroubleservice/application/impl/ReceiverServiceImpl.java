package com.demo.alerttroubleservice.application.impl;

import com.demo.alerttroubleservice.application.ReceiverService;
import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.repository.ReceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiverServiceImpl implements ReceiverService {

    @Autowired
    private ReceiverRepository receiverRepository;

    @Override
    public Optional<Receiver> findByNickname(String nickname) {
        return receiverRepository.findByNickname(nickname);
    }
    @Override
    public Receiver createReceiver(Receiver receiver) {
        Optional<Receiver> existingReceiver = receiverRepository.findByNickname(receiver.getNickname());
        if (existingReceiver.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        return receiverRepository.save(receiver);
    }
}

