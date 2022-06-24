package com.cnpm.workingspace.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PropertyDto {
    private Integer propertyId;
    private Integer customerId;
    private Integer propertyTypeId;
    private Integer imageStorageId;
    private String propertyName;
    private String address;
    private String city;
    private Integer roomQuantity;
    private LocalDateTime createDate;
    private String description;
    private Double rating;
    private Double lat;
    private Double lon;
    private List<ImageDto> images;
}
