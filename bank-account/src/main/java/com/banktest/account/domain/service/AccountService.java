package com.banktest.account.domain.service;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.persistence.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<AccountDomain> getAccountById(long id) {
        return accountRepository.getAccountById(id);
    }

    public Optional<AccountDomain> getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }

    public AccountDomain saveAccount(AccountDomain accountDomain) throws Exception {
        if (accountRepository.emailExist(accountDomain.getEmail())) {
            throw new Exception("There is an account with that email address: " + accountDomain.getEmail());
        }

        AccountEntity accountEntity = AccountEntity.builder()
                .accountName(accountDomain.getAccountName())
                .email(accountDomain.getEmail())
                .role("USER")
                .password(passwordEncoder.encode(accountDomain.getPassword()))
                .build();

        return accountRepository.saveAccount(accountEntity);
    }

    public void changePassword(String newPassword, Long idAccount) {
        accountRepository.updatePassword(passwordEncoder.encode(newPassword), idAccount);
    }

    public boolean checkIfValidOldPassword(AccountEntity accountEntity, String oldPassword) {
        return passwordEncoder.matches(oldPassword, accountEntity.getPassword());
    }
}
