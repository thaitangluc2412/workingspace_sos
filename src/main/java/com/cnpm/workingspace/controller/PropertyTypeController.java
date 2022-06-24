package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.PropertyTypeDto;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/property_type")
public class PropertyTypeController {
    @Autowired
    private PropertyTypeService propertyTypeService;

    @GetMapping(value = "property_type/{id}")
    public ResponseEntity<?> getPropertyType(@PathVariable(value = "id") int id){
        PropertyTypeDto propertyTypeDto = propertyTypeService.getPropertyTypeById(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, propertyTypeDto), HttpStatus.OK);
    }

    @GetMapping(value = "property_types")
    public ResponseEntity<?> getAllPropertyTypes(){
        List<PropertyTypeDto> propertyTypes=propertyTypeService.getAllPropertyType();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,propertyTypes),HttpStatus.OK);
    }

    @PostMapping(value = "property_type")
    public ResponseEntity<?> insertPropertyType(@RequestBody PropertyTypeDto propertyTypeDto){
        try{
            propertyTypeService.insertPropertyType(propertyTypeDto);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,null),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR,e.getMessage()),HttpStatus.OK);
        }
    }

    @PutMapping(value = "property_type/{id}")
    public ResponseEntity<?> updatePropertyType(@PathVariable int id,@RequestBody PropertyTypeDto propertyTypeDto){
        try{
            boolean status=propertyTypeService.updatePropertyType(propertyTypeDto,id);
            if(status){
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,null),HttpStatus.OK);
            } else{
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND,null),HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,e.getMessage()),HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "property_type/{id}")
    public ResponseEntity<?> deletePropertyType(@PathVariable int id){
        try{
            propertyTypeService.deletePropertyType(id);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,null),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,e.getMessage()),HttpStatus.OK);
        }
    }
}
