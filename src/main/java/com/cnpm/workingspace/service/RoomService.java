package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.dto.RoomWithMonthPriceDto;
import com.cnpm.workingspace.dto.RoomWithPriceDto;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRoom();
    void insertRoom(RoomWithPriceDto roomWithPriceDto, MultipartFile[] files);
    boolean updateRoom(RoomWithPriceDto roomWithPriceDto, int id, MultipartFile[] files);
    void deleteRoom(int id);
    RoomDto getRoomById(int id);
    List<RoomDto> getByPropertyId(int propertyId);
    List<RoomWithMonthPriceDto> getRoomPriceMonth(int id);
    Page<Room> findRoomPage(int page, int size);
}
