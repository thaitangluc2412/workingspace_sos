package com.cnpm.workingspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyTypeDto {
    private Integer propertyTypeId;
    private Integer imageStorageId;
    private String propertyTypeName;
    private List<ImageDto> images;
}
