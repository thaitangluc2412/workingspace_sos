package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.dto.RoomWithMonthPriceDto;
import com.cnpm.workingspace.dto.RoomWithPriceDto;
import com.cnpm.workingspace.mapper.RoomMapper;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.model.Room;
import com.cnpm.workingspace.sdo.ObjectSdo;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RoomController {
    @Autowired
    private RoomService RoomService;

    @Autowired
    private RoomMapper roomMapper;

    @GetMapping("/rooms/{id}")
    public ResponseEntity<ErrorResponse> getRoom(@PathVariable int id) {
        RoomDto roomDto = RoomService.getRoomById(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, roomDto), HttpStatus.OK);
    }

    @GetMapping("/rooms")
    public ResponseEntity<ErrorResponse> getAllRoom() {
        List<RoomDto> rooms = RoomService.getAllRoom();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, rooms), HttpStatus.OK);
    }

    @PostMapping("/rooms")
    public ResponseEntity<ErrorResponse> insertRoom(@RequestPart RoomWithPriceDto roomWithPriceDto, @RequestPart("files") MultipartFile[] files) {
        RoomService.insertRoom(roomWithPriceDto, files);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<ErrorResponse> updateRoom(@PathVariable int id, @RequestPart RoomWithPriceDto roomWithPriceDto, @RequestPart("files") MultipartFile[] files) {
        if (RoomService.updateRoom(roomWithPriceDto, id, files)) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<ErrorResponse> deleteRoom(@PathVariable int id) {
        RoomService.deleteRoom(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping("/properties/{propertyId}/rooms")
    public ResponseEntity<?> getRoomByPropertyId(@PathVariable int propertyId) {
        List<RoomDto> rooms = RoomService.getByPropertyId(propertyId);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, rooms), HttpStatus.OK);
    }

    @GetMapping("/rooms/priceMonth/{propertyId}")
    public ResponseEntity<?> getRoomPriceMonth(@PathVariable int propertyId) {
        List<RoomWithMonthPriceDto> roomWithMonthPriceDto = RoomService.getRoomPriceMonth(propertyId);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, roomWithMonthPriceDto), HttpStatus.OK);
    }
    @GetMapping("/rooms/page")
    @ResponseBody
    ResponseEntity<ErrorResponse> findRoomPage(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                   @RequestParam(name = "size", required = false, defaultValue = "6") int size) {
        Page<Room> rooms = RoomService.findRoomPage(page, size);
        List<RoomDto> roomDtoList = rooms.toList().stream().map(room -> roomMapper.toDto(room)).collect(Collectors.toList());
        List<Object> roomObjectList = Arrays.asList(roomDtoList.toArray());

        List<RoomDto> allRooms = RoomService.getAllRoom();
        int count = allRooms.size();

        ObjectSdo objectSdo = new ObjectSdo(count, roomObjectList);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, objectSdo), HttpStatus.OK);
    }
}
