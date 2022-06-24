package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.MyUserDetails;
import com.cnpm.workingspace.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=accountRepository.findByUsername(username);
        if(account==null){
            System.out.println("Error : User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        }
        return new User(account.getUsername(),account.getPassword(),new ArrayList<>());
    }
}
