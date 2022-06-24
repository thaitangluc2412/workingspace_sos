package com.cnpm.workingspace.utils;

import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.mapper.ImageMapper;
import com.cnpm.workingspace.model.Image;
import com.cnpm.workingspace.model.ImageStorage;
import com.cnpm.workingspace.repository.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

public final class ImageStorageUtils {
    private ImageStorageUtils() {}

    public static ImageStorage createOrUpdateImageStorageWithImages(ImageStorage imageStorage, List<ImageDto> imageDtos, ImageMapper imageMapper) {
        List<Image> images = imageDtos.stream().map(imageMapper::toEntity).collect(Collectors.toList());

        if (imageStorage.getId() == null) {
            imageStorage = new ImageStorage();
        }
        images.forEach(imageStorage::addImage);

        return imageStorage;
    }

    public static List<ImageDto> getImageDtos(ImageRepository imageRepository, Integer imageStorageId, ImageMapper imageMapper) {
        List<Image> images = imageRepository.getByImageStorageId(imageStorageId);
        return images.stream().map(imageMapper::toDto).collect(Collectors.toList());
    }
}
