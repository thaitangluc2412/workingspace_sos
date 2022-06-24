package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Customer;
import com.cnpm.workingspace.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public int insertCustomer(Customer customer) {
        try{
            Customer ret=customerRepository.save(customer);
            if(ret==null) return 0;
        } catch (Exception e){
            System.out.println("Debug insert : "+e.getMessage());
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    
    @Override
    public List<Customer> getAll() {
    	return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getCustomerByUserName(Account account) {
        return customerRepository.findByAccount(account);
    }

    @Override
    public boolean updateCustomer(Customer customer, int id) {
        Optional<Customer> customerOptional=getCustomerById(id);
        if(customerOptional.isPresent()){
            Customer curCustomer=customerOptional.get();
            curCustomer.setEmail(customer.getEmail());
            curCustomer.setCustomerName(customer.getCustomerName());
            curCustomer.setCitizenId(customer.getCitizenId());
            curCustomer.setBirthday(customer.getBirthday());
            curCustomer.setNationality(customer.getNationality());
            curCustomer.setPhoneNumber(customer.getPhoneNumber());
            customerRepository.save(curCustomer);
            return true;
        }
        return false;
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
    @Override
    public Page<Customer> findCustomerByCustomerNameContainingOrEmailContaining(String customerName, String email, String phone, int page, int size){
        return customerRepository.findCustomerByCustomerNameContainingOrEmailContainingOrPhoneNumberContaining(customerName,email, phone, PageRequest.of(page, size));
    }
    @Override
    public List<Customer> findCustomerByCustomerNameContainingOrEmailContaining(String customerName, String email,String phone) {
        return customerRepository.findCustomerByCustomerNameContainingOrEmailContainingOrPhoneNumberContaining(customerName, email, phone);
    }
    @Override
    public Page<Customer> findCustomerPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = customerRepository.findAll(pageable);
        return customersPage;
    }

    @Override
    public int countTotalCustomer() {
        return customerRepository.countTotalCustomer();
    }
}
