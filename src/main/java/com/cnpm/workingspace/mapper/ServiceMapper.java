package com.cnpm.workingspace.mapper;

import com.cnpm.workingspace.dto.ServiceDto;
import com.cnpm.workingspace.model.Service;
import org.mapstruct.Mapper;

@Mapper
public interface ServiceMapper {

    Service toEntity(ServiceDto serviceDto);

    ServiceDto toDto(Service service);
}
