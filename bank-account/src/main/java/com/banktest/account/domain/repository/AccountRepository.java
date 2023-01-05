package com.banktest.account.domain.repository;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.persistence.entity.AccountEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {

    boolean emailExist(String email);

    Optional<AccountDomain> getAccountById(long id);

    Optional<AccountDomain> getAccountByEmail(String email);

    AccountDomain saveAccount(AccountEntity accountEntity);

    void updateBalance(BigDecimal bigDecimal, long id);

    void updateStatus(long id);

    void saveVerificationToken(String token, AccountEntity accountEntity);

    void updatePassword(String newPassword, long id);
}
