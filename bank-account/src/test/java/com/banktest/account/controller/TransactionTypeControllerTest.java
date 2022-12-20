package com.banktest.account.controller;

import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.service.TransactionTypeService;
import com.banktest.account.web.TransactionTypeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@WebMvcTest(TransactionTypeController.class)
class TransactionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionTypeService transactionTypeService;

    private TransactionDomain transactionDomain;

    @BeforeEach
    void setUp() {
        transactionDomain = TransactionDomain.builder()
                .idTransaction(564326l)
                .idAccount(885748l)
                .transactionAmount(new BigDecimal("87523.45"))
                .transactionTimestamp(LocalDateTime.of(2022, Month.OCTOBER, 12, 13, 12, 00)
                        .atZone(ZoneId.of("America/Mexico_City")).toInstant())
                .build();
    }

    @Test
    @DisplayName("Should save one transactionDomain in json format using the service or return a bad request")
    void saveDepositTransaction() throws Exception {
        Mockito.when(transactionTypeService.saveDepositTransaction(ArgumentMatchers.any()))
                .thenReturn(transactionDomain);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions-type/save-deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDomain)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should save one transactionDomain in json format using the service or return a bad request")
    void saveCheckTransaction() throws Exception {
        Mockito.when(transactionTypeService.saveCheckTransaction(ArgumentMatchers.any()))
                .thenReturn(transactionDomain);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions-type/save-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDomain)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should save one transactionDomain in json format using the service or return a bad request")
    void saveOnlinePaymentTransaction() throws Exception {
        Mockito.when(transactionTypeService.saveOnlinePaymentTransaction(ArgumentMatchers.any()))
                .thenReturn(transactionDomain);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions-type/save-online-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDomain)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should save one transactionDomain in json format using the service or return a bad request")
    void saveWireTransferTransaction() throws Exception {
        Mockito.when(transactionTypeService.saveWireTransferTransaction(ArgumentMatchers.any()))
                .thenReturn(transactionDomain);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions-type/save-transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDomain)))
                .andExpect(status().isCreated());
    }
}