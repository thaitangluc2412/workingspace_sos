package com.cnpm.workingspace.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.workingspace.configuration.CloudinaryConfig;
import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.dto.RoomWithMonthPriceDto;
import com.cnpm.workingspace.dto.RoomWithPriceDto;
import com.cnpm.workingspace.mapper.ImageMapper;
import com.cnpm.workingspace.mapper.RoomMapper;
import com.cnpm.workingspace.model.Image;
import com.cnpm.workingspace.model.ImageStorage;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.model.Room;
import com.cnpm.workingspace.repository.PropertyRepository;
import com.cnpm.workingspace.repository.RoomRepository;
import com.cnpm.workingspace.utils.PathUtils;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoomServiceImp implements RoomService {
    private final String FOLDER_PATH="rooms";

    private RoomRepository roomRepository;
    private RoomMapper roomMapper;
    private PropertyRepository propertyRepository;
    private Cloudinary cloudinary;
    private CloudinaryConfig cloudinaryConfig;
    private ImageMapper imageMapper;

    @Override
    public List<RoomDto> getAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> roomMapper.toDto(room))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void insertRoom(RoomWithPriceDto roomWithPriceDto, MultipartFile[] files) {
        try{
            final int propertyId=roomWithPriceDto.getPropertyId();
            List<ImageDto> images = new ArrayList<>();
            images.addAll(saveImage(propertyId, files));
            roomWithPriceDto.setImages(images);
            Room room = roomMapper.toEntity(roomWithPriceDto);
            roomRepository.save(room);
            propertyRepository.increaseRoomQuantity(room.getProperty().getPropertyId());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateRoom(RoomWithPriceDto roomWithPriceDto, int id, MultipartFile[] files) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        boolean needUpdate = files != null;
        if (roomOptional.isPresent()) {
            Room room=roomOptional.get();
            if (needUpdate) deleteFolderCloudinary(room);
            List<ImageDto> images = new ArrayList<>();
            if (needUpdate) {
                if (room.getProperty()!=null)
                    images.addAll(saveImage(room.getProperty().getPropertyId(), files));
            }
            roomWithPriceDto.setRoomId(id);
            if (needUpdate) roomWithPriceDto.setImages(images);
            else roomWithPriceDto.setImages(room
                    .getImageStorage()
                    .getImages()
                    .parallelStream()
                    .map(en -> imageMapper.toDto(en))
                    .collect(Collectors.toList())
            );
            roomWithPriceDto.setImageStorageId(room.getImageStorage().getId());
            roomRepository.save(roomMapper.toEntity(roomWithPriceDto));
            return true;
        }
        return false;
    }

    @Override
    public void deleteRoom(int id) {
        Optional<Room> roomOptional=roomRepository.findById(id);
        if(roomOptional.isPresent()){
            Room room=roomOptional.get();
            deleteFolderCloudinary(room);
        }
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDto getRoomById(int id) {
        Room room = roomRepository.getById(id);
        return roomMapper.toDto(room);
    }

    @Override
    public List<RoomDto> getByPropertyId(int propertyId) {
        List<Room> rooms = roomRepository.getByPropertyPropertyId(propertyId);
        return rooms.stream()
                .map(room -> roomMapper.toDto(room))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomWithMonthPriceDto> getRoomPriceMonth(int id) {
        List<Room> rooms = roomRepository.getByPropertyPropertyId(id);
        return rooms.stream()
                .map(room -> roomMapper.toRoomWithMonthPriceDto(room))
                .collect(Collectors.toList());
    }

    private List<ImageDto> saveImage(int propertyId, MultipartFile[] files) {
        final DateTime now = DateTime.now();
        final String path = String.format("%s/%s/%s", FOLDER_PATH, propertyId, now.getMillis());
        final String folderPath = PathUtils.decoratePath(path);

        int[] idx = new int[1];
        idx[0] = 0;
        List<ImageDto> images = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file -> {
            String fileName = "image_" + idx[0];
            try {
                Map ret = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "folder", cloudinaryConfig.getCloudPath() + folderPath,
                                "public_id", fileName
                        )
                );
                images.add(new ImageDto(null, ret.get("url").toString(), fileName));
                System.out.println("## url : " + ret.get("url"));
                ++idx[0];

            } catch (Exception e) {
                System.out.println("error : " + e.getMessage());
            }
        });
        return images;
    }

    public void deleteFolderCloudinary(Room room) {
        if (room.getImageStorage() != null) {
            ImageStorage imageStorage = room.getImageStorage();
            if (imageStorage.getImages() != null) {
                List<Image> images = imageStorage.getImages();
                if (images.size() > 0) {
                    String folderPath = PathUtils.getParentFolder(images.get(0).getUrl());
                    try {
                        System.out.println("folder Path : " + folderPath);
                        cloudinary.api().deleteResourcesByPrefix(folderPath, Map.of());
                        cloudinary.api().deleteFolder(folderPath, Map.of());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @Override
    public Page<Room> findRoomPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        return roomPage;
    }
}
