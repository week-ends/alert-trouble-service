package com.demo.alerttroubleservice.application;

import com.demo.alerttroubleservice.domain.Receiver;

import java.util.List;
import java.util.Optional;

public interface ReceiverService {

    Receiver createReceiver(Receiver receiver);

    List<Receiver> findAll();

    void deleteReceiver(String nickname);
}
