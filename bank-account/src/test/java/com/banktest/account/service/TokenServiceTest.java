package com.banktest.account.service;

import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.domain.repository.VerificationTokenRepository;
import com.banktest.account.domain.service.TokenService;
import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.entity.VerificationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private AccountRepository accountRepository;

    private List<VerificationToken> verificationTokenList;

    @BeforeEach
    void setUp() {
        VerificationToken verificationToken1 = VerificationToken.builder()
                .idToken(1l)
                .token("er143ge8-9b58-41ae-8723-29d7ff675a30")
                .accountEntity(AccountEntity.builder()
                        .idAccount(435456L)
                        .accountName("RandomSave")
                        .email("saveaccount@names.com")
                        .password("452353425")
                        .currentBalance(new BigDecimal("4376.00"))
                        .build())
                .build();

        VerificationToken verificationToken2 = VerificationToken.builder()
                .idToken(2L)
                .token("7f1a71e8-9b58-41ae-8723-29d7ff675a30")
                .accountEntity(AccountEntity.builder()
                        .idAccount(765355L)
                        .accountName("RandomSave")
                        .email("saveaccount@names.com")
                        .password("452353425")
                        .currentBalance(new BigDecimal("4376.00"))
                        .build())
                .build();

        verificationTokenList = Arrays.asList(verificationToken1, verificationToken2);
    }

    @Test
    void validateVerificationToken_invalid() {
        Mockito.when(verificationTokenRepository.getByToken("GRSEDGSGS"))
                .thenReturn(null);

        String invalid = tokenService.validateVerificationToken("GRSEDGSGS");

        assertThat(invalid).isEqualTo("invalid");
    }

    @Test
    @Disabled
    void validateVerificationToken_expired() {
        Mockito.when(verificationTokenRepository.getByToken("7f1a71e8-9b58-41ae-8723-29d7ff675a30"))
                .thenReturn(verificationTokenList.get(1));

        Mockito.doNothing().when(accountRepository).updateStatus(765355L);

        String expired = tokenService.validateVerificationToken("7f1a71e8-9b58-41ae-8723-29d7ff675a30");

        assertAll(
                () -> assertThat(expired).isEqualTo("expired"),
                () ->  Mockito.verify(accountRepository, Mockito.times(0)).updateStatus(765355L)
        );
    }

    @Test
    void validateVerificationToken_valid() {
        Mockito.when(verificationTokenRepository.getByToken("er143ge8-9b58-41ae-8723-29d7ff675a30"))
              .thenReturn(verificationTokenList.get(0));

        Mockito.doNothing().when(accountRepository).updateStatus(435456l);

        String valid = tokenService.validateVerificationToken("er143ge8-9b58-41ae-8723-29d7ff675a30");

        assertAll(
                () -> assertThat(valid).isEqualTo("valid"),
                () ->  Mockito.verify(accountRepository, Mockito.times(1)).updateStatus(435456l)
        );
    }

    @Test
    void generateNewVerificationToken() {
        VerificationToken newVerificationToken = VerificationToken.builder()
                .idToken(2L)
                .token("po43do45-34gr-41ae-8723-237a3f675a30")
                .accountEntity(AccountEntity.builder()
                        .idAccount(765355L)
                        .accountName("RandomSave")
                        .email("saveaccount@names.com")
                        .password("452353425")
                        .currentBalance(new BigDecimal("4376.00"))
                        .build())
                .build();

        Mockito.when(verificationTokenRepository.updateToken("po43do45-34gr-41ae-8723-237a3f675a30", "7f1a71e8-9b58-41ae-8723-29d7ff675a30"))
                .thenReturn(newVerificationToken);

        VerificationToken verificationToken = tokenService.generateNewVerificationToken("7f1a71e8-9b58-41ae-8723-29d7ff675a30");

        assertAll(
                () -> assertEquals(newVerificationToken.getIdToken(), verificationToken.getIdToken()),
                () -> assertEquals(newVerificationToken.getToken(), verificationToken.getToken()),
                () -> assertEquals(newVerificationToken.getAccountEntity(), verificationToken.getAccountEntity())
        );
    }
}