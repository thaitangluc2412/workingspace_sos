package com.cnpm.workingspace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_status_id", nullable = false)
    private RoomStatus roomStatus;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "size")
    private String size;

    @Column(name = "capacity")
    private String capacity;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_storage_id", nullable = false)
    private ImageStorage imageStorage;

    @ManyToMany
    @JoinTable(name = "room_service",
               joinColumns = @JoinColumn(name = "room_id"),
               inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services = new LinkedHashSet<>();
}