package com.cnpm.workingspace.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomWithMonthPriceDto {
    private Integer roomId;
    private Integer propertyId;
    private Integer priceId;
    private Double monthPrice;
    private Double dayPrice;
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
