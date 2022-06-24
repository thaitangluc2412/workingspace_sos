package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public boolean existsUsername(String username) {
        try{
            Account account=accountRepository.findByUsername(username);
            System.out.println("# account : "+account);
            return account!=null;
        } catch (Exception e){
            System.out.println("error : "+e.getMessage());
            return false;
        }
    }

    @Override
    public int insertAccount(Account account) {
        try{
            Account ret=accountRepository.save(account);
            if(ret==null) return 0;
        } catch (Exception e){
            System.out.println("Debug insert : "+e.getMessage());
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @Override
    public boolean updateAccount(Account account, int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()){
            Account account1 = accountOptional.get();
            account1.setUsername(account.getUsername());
            account1.setPassword(account.getPassword());
            accountRepository.save(account1);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }
}
