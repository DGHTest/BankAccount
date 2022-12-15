package com.banktest.account.service;

import com.banktest.account.constants.TransactionType;
import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.domain.repository.TransactionRepository;
import com.banktest.account.domain.service.TransactionService;
import com.banktest.account.domain.service.TransactionTypeService;
import com.banktest.account.persistence.entity.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TransactionTypeServiceTest {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountRepository accountRepository;

    private TransactionDomain transactionDomain;

    @BeforeEach
    void setUp() {
        AccountDomain accountDomain = AccountDomain.builder()
                .idAccount(87658l)
                .accountName("Random345778")
                .email("user@names.com")
                .password("1234567")
                .currentBalance(new BigDecimal("20000.00"))
                .build();

        Mockito.when(accountRepository.getAccountById(87658l))
                .thenReturn(Optional.of(accountDomain));

        transactionDomain = TransactionDomain.builder()
                .idTransaction(87658l)
                .idAccount(34654363l)
                .transactionAmount(new BigDecimal("10000.45"))
                .transactionTimestamp(Timestamp.valueOf(LocalDateTime.of(2065, Month.OCTOBER, 12, 13, 12, 00)))
                .build();

        Mockito.when(transactionRepository.saveTransaction(ArgumentMatchers.any())).thenReturn(transactionDomain);
    }

    @Test
    void saveDepositTransaction() {
        Mockito.doNothing().when(accountRepository).updateBalance(new BigDecimal("30000.45"), 87658l);

        accountRepository.updateBalance(new BigDecimal("30000.45"), 87658l);

        TransactionDomain transactionSave = transactionTypeService.saveDepositTransaction(transactionDomain);

        assertAll(
                () -> assertEquals(transactionDomain.getIdTransaction(), transactionSave.getIdTransaction()),
                () -> assertEquals(transactionDomain.getIdAccount(), transactionSave.getIdAccount()),
                () -> assertEquals(transactionDomain.getTransactionAmount(), transactionSave.getTransactionAmount()),
                () -> assertEquals(transactionDomain.getTransactionTimestamp(), transactionSave.getTransactionTimestamp()),
                () -> Mockito.verify(accountRepository, Mockito.times(1)).updateBalance(new BigDecimal("30000.45"), 87658l)
        );
    }

    @Test
    void saveCheckTransaction() {
        TransactionDomain transactionSave = transactionTypeService.saveCheckTransaction(transactionDomain);

        assertAll(
                () -> assertEquals(transactionDomain.getIdTransaction(), transactionSave.getIdTransaction()),
                () -> assertEquals(transactionDomain.getIdAccount(), transactionSave.getIdAccount()),
                () -> assertEquals(transactionDomain.getTransactionAmount(), transactionSave.getTransactionAmount()),
                () -> assertEquals(transactionDomain.getTransactionTimestamp(), transactionSave.getTransactionTimestamp())
        );
    }

    @Test
    void saveOnlinePaymentTransaction() {
        Mockito.doNothing().when(accountRepository).updateBalance(new BigDecimal("9999.55"), 87658l);

        accountRepository.updateBalance(new BigDecimal("9999.55"), 87658l);

        TransactionDomain transactionSave = transactionTypeService.saveOnlinePaymentTransaction(transactionDomain);

        assertAll(
                () -> assertEquals(transactionDomain.getIdTransaction(), transactionSave.getIdTransaction()),
                () -> assertEquals(transactionDomain.getIdAccount(), transactionSave.getIdAccount()),
                () -> assertEquals(transactionDomain.getTransactionAmount(), transactionSave.getTransactionAmount()),
                () -> assertEquals(transactionDomain.getTransactionTimestamp(), transactionSave.getTransactionTimestamp()),
                () -> Mockito.verify(accountRepository, Mockito.times(1)).updateBalance(new BigDecimal("9999.55"), 87658l)
        );
    }

    @Test
    void saveWireTransferTransaction() {
        Mockito.doNothing().when(accountRepository).updateBalance(new BigDecimal("9999.55"), 87658l);

        accountRepository.updateBalance(new BigDecimal("9999.55"), 87658l);

        TransactionDomain transactionSave = transactionTypeService.saveWireTransferTransaction(transactionDomain);

        assertAll(
                () -> assertEquals(transactionDomain.getIdTransaction(), transactionSave.getIdTransaction()),
                () -> assertEquals(transactionDomain.getIdAccount(), transactionSave.getIdAccount()),
                () -> assertEquals(transactionDomain.getTransactionAmount(), transactionSave.getTransactionAmount()),
                () -> assertEquals(transactionDomain.getTransactionTimestamp(), transactionSave.getTransactionTimestamp()),
                () -> Mockito.verify(accountRepository, Mockito.times(1)).updateBalance(new BigDecimal("9999.55"), 87658l)
        );
    }
}