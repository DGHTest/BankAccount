package com.banktest.account.persistence;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.persistence.crud.AccountCrudRepository;
import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private AccountCrudRepository accountCrudRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Optional<AccountDomain> getAccountById(long id) {
        return accountCrudRepository.findById(id)
                .map(account -> accountMapper.toAccountDomain(account));
    }

    @Override
    public Optional<AccountDomain> getAccountByEmail(String email) {
        return accountCrudRepository.findByEmail(email)
                .map(account -> accountMapper.toAccountDomain(account));
    }

    @Override
    public AccountDomain saveAccount(AccountDomain accountDomain) {
        AccountEntity accountEntity = accountMapper.toAccountEntity(accountDomain);
        return accountMapper.toAccountDomain(accountCrudRepository.save(accountEntity));
    }

    @Override
    public void updateBalance(BigDecimal bigDecimal, long id) {
        accountCrudRepository.updateBalance(bigDecimal, id);
    }
}
