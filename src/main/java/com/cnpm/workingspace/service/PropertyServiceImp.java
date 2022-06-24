package com.cnpm.workingspace.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.workingspace.configuration.CloudinaryConfig;
import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dao.PropertyDao;
import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.mapper.ImageMapper;
import com.cnpm.workingspace.mapper.PropertyMapper;
import com.cnpm.workingspace.model.Image;
import com.cnpm.workingspace.model.ImageStorage;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.repository.*;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.utils.PathUtils;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class PropertyServiceImp implements PropertyService {

    private PropertyRepository propertyRepository;
    private PropertyMapper propertyMapper;
    private PropertyDao propertyDao;
    private Cloudinary cloudinary;
    private CloudinaryConfig cloudinaryConfig;
    private ImageMapper imageMapper;

    private List<ImageDto> saveImage(int id, MultipartFile[] files) {
        final DateTime now = DateTime.now();
        final String path = String.format("%s/%s", id, now.getMillis());
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

    private void removeImageCloudinary(String folderPath) {
        try {
            Map map = cloudinary.api().deleteFolder(folderPath, Map.of());
        } catch (Exception e) {
            System.out.println("error delete cloudinary : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<PropertyDto> getAllProperty() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream().map(property -> propertyMapper.toDto(property)).collect(Collectors.toList());
    }

    @Override
    public void insert(PropertyDto propertyDto, MultipartFile[] files) {
        final int id = propertyDto.getCustomerId();
        List<ImageDto> images = new ArrayList<>();
        images.addAll(saveImage(id, files));
        propertyDto.setImages(images);
        Property property = propertyMapper.toEntity(propertyDto);
        propertyRepository.save(property);
    }

    @Override
    public boolean update(int id, PropertyDto propertyDto, MultipartFile[] files) {
        Optional<Property> p = propertyRepository.findById(id);
        boolean needUpdate = files != null;
        if (p.isPresent()) {
            // todo : xu dung da luong bat dong bo o day
            Property property = p.get();
            if (needUpdate) deleteFolderCloudinary(property);
            List<ImageDto> images = new ArrayList<>();
            if (needUpdate) {
                if (property.getCustomer() != null && property.getCustomer().getCustomerId() != null)
                    images.addAll(saveImage(property.getCustomer().getCustomerId(), files));
            }
            propertyDto.setPropertyId(id);
            if (needUpdate) propertyDto.setImages(images);
            else propertyDto.setImages(p.get()
                    .getImageStorage()
                    .getImages()
                    .parallelStream()
                    .map(en -> imageMapper.toDto(en))
                    .collect(Collectors.toList())
            );
            propertyDto.setImageStorageId(p.get().getImageStorage().getId());
            propertyRepository.save(propertyMapper.toEntity(propertyDto));
            return true;
        }
        return false;
    }

    @Override
    public void delete(int id) {
        // todo : xu dung da luong bat dong bo o day
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isPresent()) {
            Property property = optionalProperty.get();
            deleteFolderCloudinary(property);
        }
        propertyRepository.deleteById(id);
    }

    @Override
    public PropertyDto getById(int id) {
        Property property = propertyRepository.getById(id);
        return propertyMapper.toDto(property);
    }

    @Override
    public List<Property> getByCity(String city) {
        return propertyRepository.getPropertyByCity(city);
    }

    @Override
    public List<Property> getByCityTypeName(String city, int typeId, String name) {
        return propertyDao.getPropertyByCityTypeName(city, typeId, name);
    }

    @Override
    public List<Property> getByCustomerCustomerId(Integer customerId) {
        return propertyRepository.getByCustomerCustomerId(customerId);
    }

    public void deleteFolderCloudinary(Property property) {
        if (property.getImageStorage() != null) {
            ImageStorage imageStorage = property.getImageStorage();
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
    public Page<Property> findPropertyPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> propertiesPage = propertyRepository.findAll(pageable);
        return propertiesPage;
    }

    @Override
    public int countTotalProperty() {
        return propertyRepository.countTotalProperty();
    }
}