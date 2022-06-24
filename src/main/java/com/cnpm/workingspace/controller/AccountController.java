package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Price;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAll(){
        List<Account> accounts = accountService.getAll();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, accounts), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> insert(@RequestBody Account account){
        accountService.insertAccount(account);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @PutMapping(value = "/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable int accountId, @RequestBody Account account){
        if(accountService.updateAccount(account, accountId)){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<?> deletePrice(@PathVariable int accountId){
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getPriceById(@PathVariable int accountId){
        Optional<Account> account = accountService.getAccountById(accountId);
        if(account.isPresent()) {
            Account account1 = account.get();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, account1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
        }
    }
}
