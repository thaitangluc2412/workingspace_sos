package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.SavedRoomDto;

import java.util.List;

public interface SavedRoomService {
    List<SavedRoomDto> getAll();
    List<SavedRoomDto> getByCustomerId(int customerId);
    void insert(SavedRoomDto savedRoomDto);
    void delete(SavedRoomDto savedRoomDto);

    boolean isExisted(int customerId, int roomId);
}
