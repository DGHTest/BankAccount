package com.banktest.account.domain.service;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<AccountDomain> getAccountById(long id) {
        return accountRepository.getAccountById(id);
    }

    public Optional<AccountDomain> getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }

    public AccountDomain saveAccount(AccountDomain accountDomain) {
        return accountRepository.saveAccount(accountDomain);
    }
}
