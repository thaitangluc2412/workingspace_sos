package com.cnpm.workingspace.service;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Price;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    boolean existsUsername(String username);
    int insertAccount(Account account);
    Account findAccountByUsername(String username);

    List<Account> getAll();

    boolean updateAccount(Account account, int id);

    void deleteAccount(int id);

    Optional<Account> getAccountById(int id);
}
