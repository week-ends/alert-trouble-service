package com.demo.alerttroubleservice.domain.repository;

import com.demo.alerttroubleservice.domain.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    boolean existsByNickname(String nickname);

    Optional<Receiver> findByNickname(String nickname);

    void deleteAll();
}
