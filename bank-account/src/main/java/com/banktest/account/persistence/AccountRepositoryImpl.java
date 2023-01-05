package com.banktest.account.persistence;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.persistence.crud.AccountCrudRepository;
import com.banktest.account.persistence.crud.VerificationTokenCrudRepository;
import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.entity.VerificationToken;
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
    private VerificationTokenCrudRepository tokenCrudRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean emailExist(String email) {
        return accountCrudRepository.existsByEmail(email);
    }

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
    public AccountDomain saveAccount(AccountEntity accountEntity) {
        return accountMapper.toAccountDomain(accountCrudRepository.save(accountEntity));
    }

    @Override
    public void updateBalance(BigDecimal bigDecimal, long id) {
        accountCrudRepository.updateBalanceById(bigDecimal, id);
    }

    @Override
    public void updateStatus(long id) {
        accountCrudRepository.updateStatusById(id);
    }

    @Override
    public void saveVerificationToken(String token, AccountEntity accountEntity) {
        VerificationToken verificationToken = VerificationToken.builder()
                .accountEntity(accountEntity)
                .token(token)
                .build();

        tokenCrudRepository.save(verificationToken);
    }

    @Override
    public void updatePassword(String newPassword, long id) {
        accountCrudRepository.updatePassword(newPassword, id);
    }
}
