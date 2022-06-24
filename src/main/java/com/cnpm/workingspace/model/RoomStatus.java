package com.cnpm.workingspace.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "room_status")
public class RoomStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_status_id", nullable = false)
    private Integer roomStatusId;

    @Column(name = "room_status_name", nullable = false)
    private String roomStatusName;

}