package com.banktest.account.controller;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.service.AccountService;
import com.banktest.account.web.AccountController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

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
                .idAccount(75347l)
                .accountName("Random345778")
                .email("user@names.com")
                .password("1234567")
                .matchPassword("1234567")
                .currentBalance(new BigDecimal("543256.99"))
                .build();

        accountDomainList = Arrays.asList(accountDomain1, accountDomain2);
    }

    @Test
    @DisplayName("Should return one accountDomain in json format with a specific id using the service or return a not found")
    void getAccountById() {
        Mockito.when(accountService.getAccountById(75347))
                .thenReturn(Optional.of(accountDomainList.get(1)));

        assertAll(
                () -> mockMvc.perform(get("/accounts/id/75347")
                                .contentType(MediaType.APPLICATION_JSON)
                                //.with(csrf())
                                .with(user("user").roles(BankRole.USER.toString())))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idAccount")
                                .value(accountDomainList.get(1).getIdAccount()))
                        .andExpect(jsonPath("$.accountName")
                                .value(accountDomainList.get(1).getAccountName()))
                        .andExpect(jsonPath("$.email")
                                .value(accountDomainList.get(1).getEmail()))
                        .andExpect(jsonPath("$.password")
                                .value(accountDomainList.get(1).getPassword()))
                        .andExpect(jsonPath("$.currentBalance")
                                .value(accountDomainList.get(1).getCurrentBalance().toString())),

                () -> mockMvc.perform(get("/accounts/id/54")
                                .contentType(MediaType.APPLICATION_JSON)
                                //.with(csrf())
                                .with(user("user").roles(BankRole.USER.toString())))
                        .andExpect(status().isNotFound()),

                () -> mockMvc.perform(get("/accounts/id/54")
                                .contentType(MediaType.APPLICATION_JSON))
                                //.with(csrf()))
                        .andExpect(status().isUnauthorized())
        );
    }

    @Test
    @DisplayName("Should return one accountDomain in json format with a specific email using the service or return a not found")
    void getAccountByEmail() {
        Mockito.when(accountService.getAccountByEmail("random@names.com"))
                .thenReturn(Optional.of(accountDomainList.get(0)));

        assertAll(
                () -> mockMvc.perform(get("/accounts/email/random@names.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                //.with(csrf())
                                .with(user("user").roles(BankRole.USER.toString())))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idAccount")
                                .value(accountDomainList.get(0).getIdAccount()))
                        .andExpect(jsonPath("$.accountName")
                                .value(accountDomainList.get(0).getAccountName()))
                        .andExpect(jsonPath("$.email")
                                .value(accountDomainList.get(0).getEmail()))
                        .andExpect(jsonPath("$.password")
                                .value(accountDomainList.get(0).getPassword()))
                        .andExpect(jsonPath("$.currentBalance")
                                .value(accountDomainList.get(0).getCurrentBalance().toString())),

                () -> mockMvc.perform(get("/accounts/email/ewtewtre@names.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                //.with(csrf())
                                .with(user("user").roles(BankRole.USER.toString())))
                        .andExpect(status().isNotFound()),

                () -> mockMvc.perform(get("/accounts/email/ewtewtre@names.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                //.with(csrf()))
                        .andExpect(status().isUnauthorized())
        );
    }
}