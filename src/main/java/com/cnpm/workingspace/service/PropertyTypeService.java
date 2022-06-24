package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.PropertyTypeDto;

import java.util.List;

public interface PropertyTypeService {
    List<PropertyTypeDto> getAllPropertyType();
    void insertPropertyType(PropertyTypeDto propertyTypeDto);
    boolean updatePropertyType(PropertyTypeDto propertyTypeDto,int id);
    void deletePropertyType(int id);
    PropertyTypeDto getPropertyTypeById(int id);
}
