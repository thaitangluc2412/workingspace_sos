package com.cnpm.workingspace.service;

import java.util.List;
import java.util.Optional;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    int insertCustomer(Customer customer);
    List<Customer> getAll();
    Optional<Customer> getCustomerById(int id);
    Customer getCustomerByUserName(Account account);
    boolean updateCustomer(Customer customer, int id);
    void deleteCustomer(int id);
    Page<Customer> findCustomerByCustomerNameContainingOrEmailContaining(String customerName, String email,String phone, int page, int size);
    List<Customer> findCustomerByCustomerNameContainingOrEmailContaining(String customerName, String email,String phone);
    Page<Customer> findCustomerPage(int page, int size);
    int countTotalCustomer();
}
