package com.cnpm.workingspace.dto;

import com.cnpm.workingspace.model.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomWithPriceDto {
    private Integer roomId;
    private Integer propertyId;
    private Price price;
    private Integer roomStatusId;
    private Integer imageStorageId;
    private String roomName;
    private String size;
    private String capacity;
    private String description;
    private Integer bedrooms;
    private List<ImageDto> images;
    private Set<ServiceDto> services;
}
