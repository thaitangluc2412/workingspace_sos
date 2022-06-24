package com.cnpm.workingspace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "property_type")
public class PropertyType {
    @Id
    @Column(name = "property_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propertyTypeId;

    @Column(name = "property_type_name")
    private String propertyTypeName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_storage_id")
    private ImageStorage imageStorage;
}
