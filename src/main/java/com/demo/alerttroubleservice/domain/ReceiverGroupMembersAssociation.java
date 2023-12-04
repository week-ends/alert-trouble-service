package com.demo.alerttroubleservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "receiver_group_receiver_associations")
public class ReceiverGroupMembersAssociation {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "receiver_group_id")
//    private ReceiverGroup receiverGroup;
//
//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "receiver_id")
//    private Receiver receiver;
//
//    public ReceiverGroupMembersAssociation(ReceiverGroup receiverGroup, Receiver receiver) {
//        this.receiverGroup = receiverGroup;
//        this.receiver = receiver;
//    }
//}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_group_id")
    private ReceiverGroup receiverGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;

    @Builder
    public ReceiverGroupMembersAssociation(ReceiverGroup receiverGroup, Receiver receiver) {
        this.receiverGroup = receiverGroup;
        this.receiver = receiver;
    }
}