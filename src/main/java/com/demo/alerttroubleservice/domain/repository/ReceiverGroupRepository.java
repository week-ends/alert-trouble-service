package com.demo.alerttroubleservice.domain.repository;

import com.demo.alerttroubleservice.domain.ReceiverGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiverGroupRepository extends JpaRepository<ReceiverGroup, Long> {

    void deleteAll();
    Optional<ReceiverGroup> findByGroupname(String groupname);
    Optional<ReceiverGroup> deleteByGroupname(String groupname);
}
