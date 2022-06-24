package com.cnpm.workingspace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "saved_room")
public class SavedRoom {
    @EmbeddedId
    private SavedRoomId id = new SavedRoomId();

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Embeddable
    public static class SavedRoomId implements Serializable {
        private static final long serialVersionUID = 5077399201266402384L;
        @Column(name = "customer_id", nullable = false)
        private Integer customerId;

        @Column(name = "room_id", nullable = false)
        private Integer roomId;
    }
}