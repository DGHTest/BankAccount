package com.banktest.account.crud;

import com.banktest.account.constants.Role;
import com.banktest.account.persistence.crud.AccountCrudRepository;
import com.banktest.account.persistence.entity.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("dev")
@Sql("/db/bankaccount_data.sql")
class AccountCrudRepositoryTest {

    @Autowired
    private AccountCrudRepository accountCrudRepository;

    @Test
    @DisplayName("Should return a accountEntity with a specific id of the data.sql")
    public void findById() {
        AccountEntity accountEntity = accountCrudRepository.findById(1L).get();

        Optional<AccountEntity> errorAccountEntity = accountCrudRepository.findById(453L);

        assertAll(
                () -> assertFalse(errorAccountEntity.isPresent()),
                () -> assertThat(accountEntity.getIdAccount()).isEqualTo(1),
                () -> assertThat(accountEntity.getAccountName()).isEqualTo("Random1"),
                () -> assertThat(accountEntity.getEmail()).isEqualTo("random1@names.com"),
                () -> assertThat(accountEntity.getPassword()).isEqualTo("123456"),
                () -> assertThat(accountEntity.getCurrentBalance().toString()).isEqualTo("10000.00"),
                () -> assertThat(accountEntity.getRole()).isEqualTo(Role.USER),
                () -> assertThat(accountEntity.getEnabled()).isEqualTo(true)
        );
    }

    @Test
    @DisplayName("Should return a accountEntity with a specific email of the data.sql")
    public void findByEmail() {
        AccountEntity accountEntity = accountCrudRepository.findByEmail("random3@names.com").get();

        Optional<AccountEntity> errorAccountEntity = accountCrudRepository.findByEmail("ranfagrsgh@names.com");

        assertAll(
                () -> assertFalse(errorAccountEntity.isPresent()),
                () -> assertThat(accountEntity.getIdAccount()).isEqualTo(3),
                () -> assertThat(accountEntity.getAccountName()).isEqualTo("Random3"),
                () -> assertThat(accountEntity.getEmail()).isEqualTo("random3@names.com"),
                () -> assertThat(accountEntity.getPassword()).isEqualTo("123456"),
                () -> assertThat(accountEntity.getCurrentBalance().toString()).isEqualTo("4000.00"),
                () -> assertThat(accountEntity.getRole()).isEqualTo(Role.USER),
                () -> assertThat(accountEntity.getEnabled()).isEqualTo(true)
        );
    }

    @Test
    @DisplayName("Should save an account in the database")
    public void saveAccount() {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountName("Random6")
                .email("random6@names.com")
                .password("1234567")
                .currentBalance(new BigDecimal(1.00))
                .role(Role.USER)
                .build();

        AccountEntity accountSave = accountCrudRepository.save(accountEntity);

        assertAll(
                () -> assertThat(accountEntity.getIdAccount()).isEqualTo(6),
                () -> assertEquals(accountEntity.getAccountName(), accountSave.getAccountName()),
                () -> assertEquals(accountEntity.getEmail(), accountSave.getEmail()),
                () -> assertEquals(accountEntity.getPassword(), accountSave.getPassword()),
                () -> assertEquals(accountEntity.getCurrentBalance(), accountSave.getCurrentBalance()),
                () -> assertEquals(accountEntity.getRole(), accountSave.getRole()),
                () -> assertThat(accountEntity.getEnabled()).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("Should update the balance of a specific account with his id in the database")
    public void updateBalance() {
        accountCrudRepository.updateBalance(new BigDecimal("6000.00"), 3);

        AccountEntity accountEntity = accountCrudRepository.findById(3L).get();

        assertAll(
                () -> assertThat(accountEntity.getIdAccount()).isEqualTo(3),
                () -> assertThat(accountEntity.getCurrentBalance().toString()).isEqualTo("6000.00")
        );
    }
}