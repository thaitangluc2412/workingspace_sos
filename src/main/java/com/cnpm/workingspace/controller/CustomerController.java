package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.model.Customer;
import com.cnpm.workingspace.sdo.ObjectSdo;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> ret = new ArrayList<>();
        try {
            ret.addAll(customerService.getAll());
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, ret), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable int id) {
        try {
            Optional<Customer> customerOptional = customerService.getCustomerById(id);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, customer), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        try {
            boolean flag = customerService.updateCustomer(customer, id);
            return new ResponseEntity<>(new ErrorResponse(flag ? ErrorCode.SUCCESS : ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<ErrorResponse> findCustomerByCustomerNameContainingOrEmailContaining(
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "6") int size
    ) {
        if (value == null) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, new ArrayList<>()), HttpStatus.OK);
        }
        try {
            Page<Customer> customerPage = customerService.findCustomerByCustomerNameContainingOrEmailContaining(value, value, value, page, size);
            List<Customer> customers = customerPage.toList();
            int cnt = customers.size();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, new ObjectSdo(cnt, new ArrayList(customers))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/searchAll")
    @ResponseBody
    public ResponseEntity<ErrorResponse> findAllCustomerByCustomerNameContainingOrEmailContaining(@RequestParam(value = "value", required = false) String value) {
        if (value == null) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, new ArrayList<>()), HttpStatus.OK);
        }
        try {
            List<Customer> customers = customerService.findCustomerByCustomerNameContainingOrEmailContaining(value, value, value);
            int cnt = customers.size();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, new ObjectSdo(cnt, new ArrayList(customers))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/page")
    @ResponseBody
    ResponseEntity<ErrorResponse> findCustomerPage(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                   @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        Page<Customer> customers = customerService.findCustomerPage(page, size);
        List<Customer> customerList = customers.toList();
        List<Object> cusObjectList = Arrays.asList(customerList.toArray());

        List<Customer> allCustomers = customerService.getAll();
        int count = allCustomers.size();

        ObjectSdo objectSdo = new ObjectSdo(count, cusObjectList);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, objectSdo), HttpStatus.OK);
    }

    @GetMapping("/getTotal")
    public ResponseEntity<?> getTotal() {
        int total = customerService.countTotalCustomer();
        try {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, total), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

}
