package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.model.ImageStorage;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.repository.ImageRepository;
import com.cnpm.workingspace.utils.ImageStorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class PropertyMapperDecorator implements PropertyMapper {

    @Autowired
    @Qualifier("delegate")
    private PropertyMapper delegate;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Property toEntity(PropertyDto propertyDto) {
        Property property = delegate.toEntity(propertyDto);

        ImageStorage imageStorage = ImageStorageUtils.createOrUpdateImageStorageWithImages(
                property.getImageStorage(),
                propertyDto.getImages(),
                imageMapper
        );
        property.setImageStorage(imageStorage);

        return property;
    }

    @Override
    public PropertyDto toDto(Property property) {
        PropertyDto propertyDto = delegate.toDto(property);

        List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
                imageRepository,
                propertyDto.getImageStorageId(),
                imageMapper
        );

        propertyDto.setImages(imageDtos);
        return propertyDto;
    }
}
