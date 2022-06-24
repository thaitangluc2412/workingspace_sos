package com.cnpm.workingspace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", nullable = false)
    private Integer propertyId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_type_id", nullable = false)
    private PropertyType propertyType;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "room_quantity", nullable = false)
    private Integer roomQuantity;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "image_storage_id", nullable = false)
    private ImageStorage imageStorage;
}