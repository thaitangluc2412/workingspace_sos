package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.dto.RoomWithMonthPriceDto;
import com.cnpm.workingspace.dto.RoomWithPriceDto;
import com.cnpm.workingspace.model.Room;
import org.mapstruct.*;

@Mapper(uses = ServiceMapper.class)
@DecoratedWith(RoomMapperDecorator.class)
public interface RoomMapper {

    @AfterMapping
    default void checkBedroom(@MappingTarget RoomWithMonthPriceDto roomWithMonthPriceDto) {
        if (roomWithMonthPriceDto.getBedrooms() == null) {
            roomWithMonthPriceDto.setBedrooms(0);
        }
    }

    @AfterMapping
    default void checkBedroom(@MappingTarget RoomDto roomDto) {
        if (roomDto.getBedrooms() == null) {
            roomDto.setBedrooms(0);
        }
    }

    @Mapping(source = "propertyId", target = "property.propertyId")
    @Mapping(source = "priceId", target = "price.id")
    @Mapping(source = "roomStatusId", target = "roomStatus.roomStatusId")
    @Mapping(source = "imageStorageId", target = "imageStorage.id")
    Room toEntity(RoomDto roomDto);

    @Mapping(source = "property.propertyId", target = "propertyId")
    @Mapping(source = "price.monthPrice", target = "monthPrice")
    @Mapping(source = "roomStatus.roomStatusId", target = "roomStatusId")
    @Mapping(source = "imageStorage.id", target = "imageStorageId")
    @Mapping(source = "price.dayPrice", target = "dayPrice")
    @Mapping(source = "price.id", target = "priceId")
    RoomWithMonthPriceDto toRoomWithMonthPriceDto(Room room);

    @Mapping(source = "propertyId", target = "property.propertyId")
    @Mapping(source = "roomStatusId", target = "roomStatus.roomStatusId")
    @Mapping(source = "imageStorageId", target = "imageStorage.id")
    Room toEntity(RoomWithPriceDto roomWithPriceDto);

    @InheritInverseConfiguration(name = "toEntity")
    RoomDto toDto(Room room);
}
