package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.SavedRoomDto;
import com.cnpm.workingspace.model.Customer;
import com.cnpm.workingspace.model.Room;
import com.cnpm.workingspace.model.SavedRoom;
import com.cnpm.workingspace.model.SavedRoom.SavedRoomId;
import com.cnpm.workingspace.repository.CustomerRepository;
import com.cnpm.workingspace.repository.RoomRepository;
import com.cnpm.workingspace.repository.SavedRoomRepository;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedRoomServiceImpl implements SavedRoomService {

    @Autowired
    SavedRoomRepository savedRoomRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RoomRepository roomRepository;
    ModelMapper toEntityMapper;
    ModelMapper toDtoMapper;

    public SavedRoomServiceImpl(ModelMapper toEntityMapper, ModelMapper toDtoMapper) {
        Converter<Integer, Customer> customerConverter = context -> customerRepository.getById(context.getSource());
        Converter<Integer, Room> roomConverter = context -> roomRepository.getById(context.getSource());

        this.toEntityMapper = toEntityMapper;
        this.toEntityMapper.createTypeMap(SavedRoomDto.class, SavedRoom.class)
                .addMappings(mapper -> mapper.using(customerConverter).map(SavedRoomDto::getCustomerId, SavedRoom::setCustomer))
                .addMappings(mapper -> mapper.using(roomConverter).map(SavedRoomDto::getRoomId, SavedRoom::setRoom));

        this.toDtoMapper = toDtoMapper;
        this.toDtoMapper.createTypeMap(SavedRoom.class, SavedRoomDto.class)
                .addMappings(mapper -> mapper.map(savedRoom -> savedRoom.getCustomer().getCustomerId(),SavedRoomDto::setCustomerId))
                .addMappings(mapper -> mapper.map(savedRoom -> savedRoom.getRoom().getRoomId(), SavedRoomDto::setRoomId));
    }

    @Override
    public List<SavedRoomDto> getAll() {
        List<SavedRoom> savedRooms = savedRoomRepository.findAll();
        return savedRooms.stream().map(savedRoom -> toDtoMapper.map(savedRoom, SavedRoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<SavedRoomDto> getByCustomerId(int customerId) {
        List<SavedRoom> savedRooms = savedRoomRepository.getByCustomerCustomerId(customerId);
        return savedRooms.stream().map(savedRoom -> toDtoMapper.map(savedRoom, SavedRoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public void insert(SavedRoomDto savedRoomDto) {
        SavedRoom savedRoom = toEntityMapper.map(savedRoomDto, SavedRoom.class);
        savedRoomRepository.save(savedRoom);
    }

    @Override
    public void delete(SavedRoomDto savedRoomDto) {
        savedRoomRepository.deleteById(new SavedRoomId(savedRoomDto.getCustomerId(), savedRoomDto.getRoomId()));
    }

    @Override
    public boolean isExisted(int customerId, int roomId) {
        return savedRoomRepository.existsByCustomerCustomerIdAndRoomRoomId(customerId, roomId);
    }
}
