package com.cnpm.workingspace.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.workingspace.configuration.CloudinaryConfig;
import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.ImageDto;
import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.mapper.PropertyMapper;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.sdo.ObjectSdo;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.PropertyService;
import com.cnpm.workingspace.utils.PathUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/properties")
public class PropertyController {
    private PropertyService propertyService;
    private PropertyMapper propertyMapper;

    public PropertyController(PropertyService propertyService, PropertyMapper propertyMapper) {
        this.propertyService = propertyService;
        this.propertyMapper = propertyMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ErrorResponse> getProperty(@PathVariable int id) {
        PropertyDto propertyDto = propertyService.getById(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, propertyDto), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ErrorResponse> getAllProperty() {
        List<PropertyDto> properties = propertyService.getAllProperty();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, properties), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> insertPropertyWithImage(@RequestPart PropertyDto propertyDto, @RequestPart("files") MultipartFile[] files) {
        try {

            propertyService.insert(propertyDto, files);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updateProperty(@PathVariable int id, @RequestPart PropertyDto propertyDto, @RequestPart(value = "files", required = false) MultipartFile[] files) {
//        PropertyDto curPropert
        if (propertyService.update(id, propertyDto, files)) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteProperty(@PathVariable int id) {
        propertyService.delete(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping("/getByCity")
    @ResponseBody
    public ResponseEntity<ErrorResponse> getPriceOrder(@RequestParam String city){
        List<Property> properties = propertyService.getByCity(city);
        List<PropertyDto> propertyDtos=properties.stream().map(property -> propertyMapper.toDto(property)).collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, propertyDtos), HttpStatus.OK);
    }

    @GetMapping("/getByCityTypeName")
    @ResponseBody
    public ResponseEntity<ErrorResponse> getPropertyByCityTypeName(@RequestParam String city, @RequestParam(defaultValue = "0") int typeId, @RequestParam String name){
        try{
            List<Property> properties = propertyService.getByCityTypeName(city, typeId, name);
            List<PropertyDto> propertyDtos=properties.parallelStream().map(property -> propertyMapper.toDto(property)).collect(Collectors.toList());
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, propertyDtos), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<ErrorResponse> getByCustomerId(@PathVariable int customerId){
        System.out.println("get by customer id");
        List<Property> properties = propertyService.getByCustomerCustomerId(customerId);
        List<PropertyDto> propertyDtos=properties.stream().map(property -> propertyMapper.toDto(property)).collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, propertyDtos), HttpStatus.OK);
    }
    @GetMapping("/page")
    @ResponseBody
    ResponseEntity<ErrorResponse> findPropertyPage(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                   @RequestParam(name = "size", required = false, defaultValue = "6") int size) {
        Page<Property> properties = propertyService.findPropertyPage(page, size);
        List<PropertyDto> propertyList = properties.toList().stream().map(property -> propertyMapper.toDto(property)).collect(Collectors.toList());
        List<Object> prObjectList = Arrays.asList(propertyList.toArray());

        List<PropertyDto> allProperties = propertyService.getAllProperty();
        int count = allProperties.size();

        ObjectSdo objectSdo = new ObjectSdo(count, prObjectList);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, objectSdo), HttpStatus.OK);
    }

    @GetMapping("/getTotal")
    public ResponseEntity<ErrorResponse> getTotal() {
        int total = propertyService.countTotalProperty();
        try {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, total), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

}
