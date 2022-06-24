package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.model.Service;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<Service> serviceList = serviceService.getAll();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, serviceList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try{
            Optional<Service> serviceOptional = serviceService.getServiceById(id);
            if(serviceOptional.isPresent()){
                Service service = serviceOptional.get();
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, service),HttpStatus.OK);
            }
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND,null),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,null),HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody Service service) {
        serviceService.insertService(service);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Service service, @PathVariable int id) {
        if (serviceService.updateService(service, id)) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        serviceService.deleteService(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }
}
