package com.demo.alerttroubleservice.domain.repository;

import com.demo.alerttroubleservice.domain.ReceiverGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiverGroupRepository extends JpaRepository<ReceiverGroup, Long> {

    Optional<ReceiverGroup> findByGroupname(String groupname);

    void deleteAll();

    Optional<ReceiverGroup> deleteByGroupname(String groupname);
}
