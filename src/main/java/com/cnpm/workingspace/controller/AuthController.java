package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.CustomerAccount;
import com.cnpm.workingspace.dto.Message;
import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Customer;
import com.cnpm.workingspace.payload.request.LoginRequest;
import com.cnpm.workingspace.repository.PersonRepository;
import com.cnpm.workingspace.security.jwt.JwtUtils;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.security.response.LoginAuthenticator;
import com.cnpm.workingspace.service.AccountService;
import com.cnpm.workingspace.service.CustomerService;
import com.cnpm.workingspace.service.MyUserDetailsService;
import com.cnpm.workingspace.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PersonService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    AccountService accountService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
            );
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.USERNAME_OR_PASSWORD_INCORRECT,null),HttpStatus.OK);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtils.generateTokenFromName(userDetails.getUsername());
        try{
            Account account=accountService.findAccountByUsername(userDetails.getUsername());
            Customer customer=customerService.getCustomerByUserName(account);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,new LoginAuthenticator(jwt,customer)),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR,e.getMessage()),HttpStatus.OK);
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody CustomerAccount customerAccount){
        System.out.println("### customerAccount : "+customerAccount);
        Account account=customerAccount.getAccount();
        if(accountService.existsUsername(account.getUsername())){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.USERNAME_ALREADY_EXISTS,null),HttpStatus.OK);
        }
        System.out.println("not exists");
        Customer customer=customerAccount.getCustomer();
        customer.setAccount(account);
        System.out.println("### Customer : "+customer);
        System.out.println("### Account : "+account);
        try{
            int status=customerService.insertCustomer(customer);
            if(status==1) return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,null),HttpStatus.OK);
            else return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,null),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,null),HttpStatus.OK);
        }
    }

    @GetMapping("fake_info")
    public ResponseEntity<?> fakeInfo(){
        Account account=new Account("tri","14022000");
        Customer customer=new Customer(
                "tranvantri2000@gmail.com",
                "tri tran",
                "201818010"
        );
        customer.setAccount(account);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,customer),HttpStatus.OK);
    }
}