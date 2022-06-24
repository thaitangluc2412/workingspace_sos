package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.PropertyTypeDto;
import com.cnpm.workingspace.mapper.PropertyTypeMapper;
import com.cnpm.workingspace.model.PropertyType;
import com.cnpm.workingspace.repository.PropertyTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PropertyTypeServiceImp implements PropertyTypeService{

    private PropertyTypeRepository propertyTypeRepository;
    private PropertyTypeMapper propertyTypeMapper;

    @Override
    public List<PropertyTypeDto> getAllPropertyType() {
        List<PropertyType> propertyTypes = propertyTypeRepository.findAll();
        return propertyTypes.stream()
                .map(propertyType -> propertyTypeMapper.toDto(propertyType))
                .collect(Collectors.toList());
    }

    @Override
    public void insertPropertyType(PropertyTypeDto propertyTypeDto) {
        propertyTypeRepository.save(propertyTypeMapper.toEntity(propertyTypeDto));
    }

    @Override
    public boolean updatePropertyType(PropertyTypeDto propertyTypeDto, int id) {
        Optional<PropertyType> p = propertyTypeRepository.findById(id);
        if(p.isPresent()){
            propertyTypeRepository.save(propertyTypeMapper.toEntity(propertyTypeDto));
            return true;
        }
        return false;
    }

    @Override
    public void deletePropertyType(int id) {
        propertyTypeRepository.deleteById(id);
    }

    @Override
    public PropertyTypeDto getPropertyTypeById(int id) {
        PropertyType propertyType = propertyTypeRepository.getById(id);
        return propertyTypeMapper.toDto(propertyType);
    }
}
