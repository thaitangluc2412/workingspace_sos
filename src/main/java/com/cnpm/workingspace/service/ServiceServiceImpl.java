package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.ReservationStatus;
import com.cnpm.workingspace.model.Service;
import com.cnpm.workingspace.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    ServiceRepository serviceRepository;


    @Override
    public List<Service> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void insertService(Service service) {
        serviceRepository.save(service);
    }

    @Override
    public boolean updateService(Service service, int id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()){
            Service serviceNew = serviceOptional.get();
            serviceNew.setServiceName(service.getServiceName());
            serviceNew.setIcon(service.getIcon());
            serviceRepository.save((serviceNew));
            return true;
        }
        return false;
    }

    @Override
    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public Optional<Service> getServiceById(int id) {
        System.out.println("service in serviceimpl: " + serviceRepository.findById(id).get());
        return serviceRepository.findById(id);
    }
}
