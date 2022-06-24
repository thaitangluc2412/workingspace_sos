package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.PropertyTypeDto;
import com.cnpm.workingspace.model.PropertyType;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(PropertyTypeMapperDecorator.class)
public interface PropertyTypeMapper {

    @Mapping(source = "imageStorageId", target = "imageStorage.id")
    PropertyType toEntity(PropertyTypeDto propertyTypeDto);

    @Mapping(source = "imageStorage.id", target = "imageStorageId")
    PropertyTypeDto toDto(PropertyType propertyType);
}
