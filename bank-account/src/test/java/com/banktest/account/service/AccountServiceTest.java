package com.banktest.account.service;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    private List<AccountDomain> accountDomainList;

    @BeforeEach
    void setUp() {
        AccountDomain accountDomain1 = AccountDomain.builder()
                .idAccount(687452786l)
                .accountName("Random634675")
                .email("random@names.com")
                .password("1234567")
                .matchPassword("1234567")
                .currentBalance(new BigDecimal(654316.76))
                .build();

        AccountDomain accountDomain2 = AccountDomain.builder()
                .idAccount(75347)
                .accountName("Random345778")
                .email("user@names.com")
                .password("1234567")
                .matchPassword("1234567")
                .currentBalance(new BigDecimal(543256.00))
                .build();

        accountDomainList = Arrays.asList(accountDomain1, accountDomain2);
    }

    @Test
    @DisplayName("Should return one accountDomain with the specific id' using the repository")
    void getAccountById() {
        Mockito.when(accountRepository.getAccountById(687452786l))
                .thenReturn(Optional.of(accountDomainList.get(0)));

        AccountDomain accountDomain = accountService.getAccountById(687452786l).get();

        assertEquals(687452786l, accountDomain.getIdAccount());
    }

    @Test
    @DisplayName("Should return one accountDomain with the specific email using the repository")
    void getAccountByEmail() {
        Mockito.when(accountRepository.getAccountByEmail("user@names.com"))
                .thenReturn(Optional.of(accountDomainList.get(1)));

        AccountDomain accountDomain = accountService.getAccountByEmail("user@names.com").get();

        assertEquals("user@names.com", accountDomain.getEmail());
    }

    @Test
    @DisplayName("Should pass one accountDomain to repository and return it")
    void saveAccount() throws Exception {
        AccountDomain accountDomain = AccountDomain.builder()
                .idAccount(435456l)
                .accountName("RandomSave")
                .email("saveaccount@names.com")
                .password("452353425")
                .matchPassword("452353425")
                .currentBalance(new BigDecimal(4376.65))
                .build();

        Mockito.when(accountRepository.saveAccount(ArgumentMatchers.any())).thenReturn(accountDomain);

        AccountDomain accountSave = accountService.saveAccount(accountDomain);

        assertAll(
                () -> assertEquals(accountDomain.getIdAccount(), accountSave.getIdAccount()),
                () -> assertEquals(accountDomain.getAccountName(), accountSave.getAccountName()),
                () -> assertEquals(accountDomain.getEmail(), accountSave.getEmail()),
                () -> assertEquals(accountDomain.getPassword(), accountSave.getPassword()),
                () -> assertEquals(accountDomain.getCurrentBalance(), accountSave.getCurrentBalance())
        );
    }
}