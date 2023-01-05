package com.banktest.account.controller;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.service.TokenService;
import com.banktest.account.domain.service.AccountService;
import com.banktest.account.web.RegisterController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("dev")
@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TokenService tokenService;


    //TODO: No funciona sin csrf y sufre del error 415
    @Test
    @DisplayName("Should save one accountDomain in json format using the service or return a bad request")
    void registerAccount() throws Exception {
        AccountDomain accountSave = AccountDomain.builder()
                .idAccount(687452786l)
                .accountName("Random634675")
                .email("random@names.com")
                .password("1234567")
                .matchPassword("1234567")
                .build();

        Mockito.when(accountService.saveAccount(ArgumentMatchers.any()))
                .thenReturn(accountSave);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .with(csrf())
                        .with(user("user").roles(BankRole.USER.toString())))
                .andExpect(content().string("Success"));
        //.andExpect(status().isCreated());
    }

    @Test
    void verifyRegistration() throws Exception {
        Mockito.when(tokenService.validateVerificationToken("2b418a21-ca4f-4dfd-ab96-011d95c0ba52"))
                .thenReturn("invalid");

        Mockito.when(tokenService.validateVerificationToken("7f1a71e8-9b58-41ae-8723-29d7ff675a30"))
                .thenReturn("valid");

        assertAll(
                () -> mockMvc.perform(MockMvcRequestBuilders.get("/verifyRegistration")
                                .with(csrf())
                                .param("token", "7f1a71e8-9b58-41ae-8723-29d7ff675a30"))
                        .andExpect(content().string("Account verifies successfully"))
                        .andDo(print()),

                () -> mockMvc.perform(MockMvcRequestBuilders.get("/verifyRegistration")
                                .with(csrf())
                                .param("token", "2b418a21-ca4f-4dfd-ab96-011d95c0ba52")
                        .content("Bad account"))
                        .andDo(print())
        );

    }
}