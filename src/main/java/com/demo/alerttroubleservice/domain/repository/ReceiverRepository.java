package com.demo.alerttroubleservice.domain.repository;

import com.demo.alerttroubleservice.domain.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
    void deleteAll();

    void deleteByNickname(String nickname);
    Optional<Receiver> findByNickname(String nickname);
    @Query("SELECT new Receiver (id, nickname) FROM Receiver WHERE nickname IN :nicknames")
    List<Receiver> findByNicknames(List<String> nicknames);
}
