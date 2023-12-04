package com.demo.alerttroubleservice.domain.repository;

import com.demo.alerttroubleservice.domain.Receiver;
import com.demo.alerttroubleservice.domain.ReceiverGroup;
import com.demo.alerttroubleservice.domain.ReceiverGroupMembersAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReceiverGroupMembersAssociationRepository extends JpaRepository<ReceiverGroupMembersAssociation, Long> {
    void deleteAll();

    List<Receiver> findByReceiverGroup(Optional<ReceiverGroup> actualReceiverGroup);
}
