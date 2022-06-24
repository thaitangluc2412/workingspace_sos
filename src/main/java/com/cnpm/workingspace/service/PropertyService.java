package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.PropertyDto;;
import com.cnpm.workingspace.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> getAllProperty();

    void insert(PropertyDto propertyDto, MultipartFile[] files);

    boolean update(int id, PropertyDto propertyDto, MultipartFile[] files);

    void delete(int id);

    PropertyDto getById(int id);

    List<Property> getByCity(String city);

    List<Property> getByCityTypeName(String city, int typeId, String name);

    List<Property> getByCustomerCustomerId(Integer customerId);

    Page<Property> findPropertyPage(int page, int size);

    int countTotalProperty();
}