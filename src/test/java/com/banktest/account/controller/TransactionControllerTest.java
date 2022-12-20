package com.banktest.account.controller;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.service.AccountService;
import com.banktest.account.domain.service.TransactionService;
import com.banktest.account.web.AccountController;
import com.banktest.account.web.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private List<TransactionDomain> transactionDomainList;

    @BeforeEach
    void setUp() {
        TransactionDomain transactionDomain1 = TransactionDomain.builder()
                .idTransaction(564326l)
                .idAccount(885748l)
                .transactionAmount(new BigDecimal("87523.45"))
                .transactionTimestamp(LocalDateTime.of(2022, Month.OCTOBER, 12, 13, 12, 00)
                        .atZone(ZoneId.of("America/Mexico_City")).toInstant())
                .build();

        TransactionDomain transactionDomain2 = TransactionDomain.builder()
                .idTransaction(87686l)
                .idAccount(54365l)
                .transactionAmount(new BigDecimal("7657.75"))
                .transactionTimestamp(LocalDateTime.of(2022, Month.JANUARY, 20, 20, 12, 00)
                        .atZone(ZoneId.of("America/Mexico_City")).toInstant())
                .build();

        TransactionDomain transactionDomain3 = TransactionDomain.builder()
                .idTransaction(6546l)
                .idAccount(885748l)
                .transactionAmount(new BigDecimal("6546734.76"))
                .transactionTimestamp(LocalDateTime.of(2022, Month.DECEMBER, 11, 13, 12, 00)
                        .atZone(ZoneId.of("America/Mexico_City")).toInstant())
                .build();

        TransactionDomain transactionDomain4 = TransactionDomain.builder()
                .idTransaction(67582l)
                .idAccount(885748l)
                .transactionAmount(new BigDecimal("5464.76"))
                .transactionTimestamp(LocalDateTime.of(2022, Month.FEBRUARY, 11, 11, 12, 11)
                        .atZone(ZoneId.of("America/Mexico_City")).toInstant())
                .build();

        transactionDomainList = Arrays.asList(transactionDomain1, transactionDomain2, transactionDomain3, transactionDomain4);
    }

    @Test
    void getTransactionById() {
        Mockito.when(transactionService.getTransactionById(67582l))
                .thenReturn(Optional.of(transactionDomainList.get(3)));

        assertAll(
                () -> mockMvc.perform(get("/transactions/67582")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idTransaction")
                                .value(transactionDomainList.get(3).getIdTransaction()))
                        .andExpect(jsonPath("$.idAccount")
                                .value(transactionDomainList.get(3).getIdAccount()))
                        .andExpect(jsonPath("$.transactionAmount")
                                .value(transactionDomainList.get(3).getTransactionAmount()))
                        .andExpect(jsonPath("$.transactionTimestamp")
                                .value(transactionDomainList.get(3).getTransactionTimestamp().toString())),

                () -> mockMvc.perform(get("/transactions/54")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
        );
    }

    @Test
    void getByIdAccount() {
        Mockito.when(transactionService.getByIdAccount(885748l))
                .thenReturn(Arrays.asList(transactionDomainList.get(0), transactionDomainList.get(2), transactionDomainList.get(3)));

        assertAll(
                () -> mockMvc.perform(get("/transactions/account/885748")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.*", hasSize(3)))
                        .andExpect(jsonPath("$[0].idTransaction")
                                .value(transactionDomainList.get(0).getIdTransaction()))
                        .andExpect(jsonPath("$[0].idAccount")
                                .value(transactionDomainList.get(0).getIdAccount()))
                        .andExpect(jsonPath("$[0].transactionAmount")
                                .value(transactionDomainList.get(0).getTransactionAmount()))

                        .andExpect(jsonPath("$[1].idTransaction")
                                .value(transactionDomainList.get(2).getIdTransaction()))
                        .andExpect(jsonPath("$[1].idAccount")
                                .value(transactionDomainList.get(0).getIdAccount()))
                        .andExpect(jsonPath("$[1].transactionAmount")
                                .value(transactionDomainList.get(2).getTransactionAmount()))

                        .andExpect(jsonPath("$[2].idTransaction")
                                .value(transactionDomainList.get(3).getIdTransaction()))
                        .andExpect(jsonPath("$[2].idAccount")
                                .value(transactionDomainList.get(3).getIdAccount()))
                        .andExpect(jsonPath("$[2].transactionAmount")
                                .value(transactionDomainList.get(3).getTransactionAmount())),

                () -> mockMvc.perform(get("/transactions/account/7348")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
        );
    }

    @Test
    void getByTimeAndIdAccount() {
        Mockito.when(transactionService.getByTimeAndIdAccount(Instant.parse("2021-10-09T20:10:00Z"), 54365l))
                .thenReturn(Arrays.asList(transactionDomainList.get(1)));

        assertAll(
                () -> mockMvc.perform(get("/transactions/account/54365/time/2021-10-09T20:10:00Z")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.*", hasSize(1)))
                        .andExpect(jsonPath("$[0].idTransaction")
                                .value(transactionDomainList.get(1).getIdTransaction()))
                        .andExpect(jsonPath("$[0].idAccount")
                                .value(transactionDomainList.get(1).getIdAccount()))
                        .andExpect(jsonPath("$[0].transactionAmount")
                                .value(transactionDomainList.get(1).getTransactionAmount()))
                        .andExpect(jsonPath("$[0].transactionTimestamp")
                                .value(transactionDomainList.get(1).getTransactionTimestamp().toString())),

                () -> mockMvc.perform(get("/transactions/account/54365/time/2022-10-09T20:10:00Z")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
        );
    }
}