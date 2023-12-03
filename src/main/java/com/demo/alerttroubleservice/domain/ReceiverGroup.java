package com.demo.alerttroubleservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receiverGroups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupname;
//    private String name;
}
