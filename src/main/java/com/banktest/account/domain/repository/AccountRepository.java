package com.banktest.account.domain.repository;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.persistence.entity.AccountEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {

    Optional<AccountDomain> getAccountById(long id);

    Optional<AccountDomain> getAccountByEmail(String email);

    AccountDomain saveAccount(AccountDomain accountDomain);

    void updateBalance(BigDecimal bigDecimal, long id);
}
