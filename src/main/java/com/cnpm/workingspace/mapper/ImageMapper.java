package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.model.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

    Image toEntity(ImageDto imageDto);

    ImageDto toDto(Image image);
}
