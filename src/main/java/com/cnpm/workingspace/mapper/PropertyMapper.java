package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.model.Property;
import org.mapstruct.*;

@Mapper
@DecoratedWith(PropertyMapperDecorator.class)
public interface PropertyMapper {

    @Mapping(source = "customerId", target = "customer.customerId")
    @Mapping(source = "propertyTypeId", target = "propertyType.propertyTypeId")
    @Mapping(source = "imageStorageId", target = "imageStorage.id")
    Property toEntity(PropertyDto propertyDto);

    @InheritInverseConfiguration(name = "toEntity")
    PropertyDto toDto(Property property);
}
