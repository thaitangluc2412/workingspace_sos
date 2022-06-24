package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    List<Service> getAll();
    void insertService(Service service);
    boolean updateService(Service service, int id);
    void deleteService(int id);
    Optional<Service> getServiceById(int id);
}
