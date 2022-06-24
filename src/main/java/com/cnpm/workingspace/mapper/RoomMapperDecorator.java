package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.dto.RoomWithMonthPriceDto;
import com.cnpm.workingspace.dto.RoomWithPriceDto;
import com.cnpm.workingspace.model.ImageStorage;
import com.cnpm.workingspace.model.Room;
import com.cnpm.workingspace.repository.ImageRepository;
import com.cnpm.workingspace.utils.ImageStorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class RoomMapperDecorator implements RoomMapper {

    @Autowired
    @Qualifier("delegate")
    private RoomMapper delegate;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Room toEntity(RoomDto roomDto) {
        Room room = delegate.toEntity(roomDto);

        ImageStorage imageStorage = ImageStorageUtils.createOrUpdateImageStorageWithImages(
                room.getImageStorage(),
                roomDto.getImages(),
                imageMapper
        );
        room.setImageStorage(imageStorage);

        return room;
    }

    @Override
    public Room toEntity(RoomWithPriceDto roomWithPriceDto) {
        Room room = delegate.toEntity(roomWithPriceDto);

        ImageStorage imageStorage = ImageStorageUtils.createOrUpdateImageStorageWithImages(
                room.getImageStorage(),
                roomWithPriceDto.getImages(),
                imageMapper
        );
        room.setImageStorage(imageStorage);

        return room;
    }

    @Override
    public RoomDto toDto(Room room) {
        RoomDto roomDto = delegate.toDto(room);

        List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
                imageRepository,
                roomDto.getImageStorageId(),
                imageMapper
        );
        roomDto.setImages(imageDtos);

        return roomDto;
    }

    @Override
    public RoomWithMonthPriceDto toRoomWithMonthPriceDto(Room room) {
        RoomWithMonthPriceDto roomWithMonthPriceDto = delegate.toRoomWithMonthPriceDto(room);

        List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
                imageRepository,
                roomWithMonthPriceDto.getImageStorageId(),
                imageMapper
        );
        roomWithMonthPriceDto.setImages(imageDtos);

        return roomWithMonthPriceDto;
    }
}
