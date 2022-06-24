package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.dto.PropertyTypeDto;
import com.cnpm.workingspace.model.ImageStorage;
import com.cnpm.workingspace.model.PropertyType;
import com.cnpm.workingspace.repository.ImageRepository;
import com.cnpm.workingspace.utils.ImageStorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class PropertyTypeMapperDecorator implements PropertyTypeMapper {

    @Autowired
    @Qualifier("delegate")
    private PropertyTypeMapper delegate;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public PropertyType toEntity(PropertyTypeDto propertyTypeDto) {
        PropertyType propertyType = delegate.toEntity(propertyTypeDto);

        ImageStorage imageStorage = ImageStorageUtils.createOrUpdateImageStorageWithImages(
                propertyType.getImageStorage(),
                propertyTypeDto.getImages(),
                imageMapper
        );
        propertyType.setImageStorage(imageStorage);

        return propertyType;
    }

    @Override
    public PropertyTypeDto toDto(PropertyType propertyType) {
        PropertyTypeDto propertyTypeDto = delegate.toDto(propertyType);

        List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
                imageRepository,
                propertyTypeDto.getImageStorageId(),
                imageMapper
        );
        propertyTypeDto.setImages(imageDtos);

        return propertyTypeDto;
    }
}
