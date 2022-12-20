package com.banktest.account.mapper;

import com.banktest.account.constants.Role;
import com.banktest.account.domain.AccountDomain;
import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.mapper.AccountMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    @DisplayName("Should transform the entity information to domain information")
    public void testEntityToDomain() {
        AccountEntity accountEntity = AccountEntity.builder()
                .idAccount(43524l)
                .accountName("Random6")
                .email("random6@names.com")
                .password("1234567")
                .currentBalance(new BigDecimal(1.00))
                .build();

        AccountDomain accountDomain = accountMapper.toAccountDomain(accountEntity);

        assertAll(
                () -> assertEquals(accountEntity.getIdAccount(), accountDomain.getIdAccount()),
                () -> assertEquals(accountEntity.getAccountName(), accountDomain.getAccountName()),
                () -> assertEquals(accountEntity.getEmail(), accountDomain.getEmail()),
                () -> assertEquals(accountEntity.getPassword(), accountDomain.getPassword()),
                () -> assertEquals(accountEntity.getCurrentBalance(), accountDomain.getCurrentBalance())
        );
    }

    @Test
    @DisplayName("Should transform the domain information to entity information")
    public void testDomainToEntity() {
        AccountDomain accountDomain = AccountDomain.builder()
                .idAccount(43524l)
                .accountName("Random6")
                .email("random6@names.com")
                .password("1234567")
                .currentBalance(new BigDecimal(1.00))
                .build();

        AccountEntity accountEntity = accountMapper.toAccountEntity(accountDomain);

        assertAll(
                () -> assertEquals(accountDomain.getIdAccount(), accountEntity.getIdAccount()),
                () -> assertEquals(accountDomain.getAccountName(), accountEntity.getAccountName()),
                () -> assertEquals(accountDomain.getEmail(), accountEntity.getEmail()),
                () -> assertEquals(accountDomain.getPassword(), accountEntity.getPassword()),
                () -> assertEquals(accountDomain.getCurrentBalance(), accountEntity.getCurrentBalance()),
                () -> assertEquals(Role.USER, accountEntity.getRole()),
                () -> assertEquals(false, accountEntity.getEnabled())
        );
    }
}
