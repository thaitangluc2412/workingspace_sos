package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByAccount(Account account);
    List<Customer> findCustomerByCustomerNameContainingOrEmailContainingOrPhoneNumberContaining(String customerName, String email,String phone);

    Page<Customer> findCustomerByCustomerNameContainingOrEmailContainingOrPhoneNumberContaining(String customerName, String email, String phone, Pageable pageable);

    @Query(value = "SELECT count(customer_id) as total FROM customer", nativeQuery = true)
    int countTotalCustomer();
}
