package com.demo.alerttroubleservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "receivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "receiver")
    private List<ReceiverGroupMembersAssociation> receiverGroupMembersAssociations;
}
