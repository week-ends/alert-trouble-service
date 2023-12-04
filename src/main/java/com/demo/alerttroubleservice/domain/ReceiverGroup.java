package com.demo.alerttroubleservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "receiver_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupname;

    @OneToMany(mappedBy = "receiverGroup")
    private List<ReceiverGroupMembersAssociation> receiverGroupMembersAssociations;
}
