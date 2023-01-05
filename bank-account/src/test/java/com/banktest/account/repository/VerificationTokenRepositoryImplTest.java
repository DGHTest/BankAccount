package com.banktest.account.repository;

import com.banktest.account.domain.repository.VerificationTokenRepository;
import com.banktest.account.persistence.crud.VerificationTokenCrudRepository;
import com.banktest.account.persistence.entity.VerificationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class VerificationTokenRepositoryImplTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private VerificationTokenCrudRepository verificationTokenCrud;

    private List<VerificationToken> verificationTokenList;

    @BeforeEach
    void setUp() {
        VerificationToken verificationToken1 = VerificationToken.builder()
                .idToken(5435l)
                .token("er143ge8-9b58-41ae-8723-29d7ff675a30")
                .build();

        VerificationToken verificationToken2 = VerificationToken.builder()
                .idToken(63987l)
                .token("er143ge8-34gr-41ae-8723-237a3f675a30")
                .build();

        verificationTokenList = Arrays.asList(verificationToken1, verificationToken2);
    }

    @Test
    void getByToken() {
        Mockito.when(verificationTokenCrud.findByToken("er143ge8-34gr-41ae-8723-237a3f675a30"))
                .thenReturn(verificationTokenList.get(1));

        VerificationToken verificationToken = verificationTokenRepository.getByToken("er143ge8-34gr-41ae-8723-237a3f675a30");

        assertAll(
                () -> assertEquals(63987l, verificationToken.getIdToken()),
                () -> assertEquals("er143ge8-34gr-41ae-8723-237a3f675a30", verificationToken.getToken())
        );
    }

    @Test
    void delete() {
        Mockito.doNothing().when(verificationTokenCrud)
                .delete(verificationTokenList.get(0));

        verificationTokenRepository.delete(verificationTokenList.get(0));

        Mockito.verify(verificationTokenCrud, Mockito.times(1)).delete(verificationTokenList.get(0));
    }

    @Test
    void updateToken() {
        Mockito.doNothing().when(verificationTokenCrud)
                .updateTokenById(Mockito.isA(String.class), Mockito.eq("er143ge8-34gr-41ae-8723-237a3f675a30"));

        VerificationToken verificationTokenById = VerificationToken.builder()
                .idToken(63987l)
                .token("po43do45-34gr-41ae-8723-237a3f675a30")
                .build();

        Mockito.when(verificationTokenCrud.findByToken("po43do45-34gr-41ae-8723-237a3f675a30"))
                .thenReturn(verificationTokenById);

        VerificationToken verificationToken = verificationTokenRepository.updateToken("po43do45-34gr-41ae-8723-237a3f675a30", "er143ge8-34gr-41ae-8723-237a3f675a30");

        assertAll(
                () -> Mockito.verify(verificationTokenCrud, Mockito.times(1))
                        .updateTokenById(Mockito.isA(String.class), Mockito.eq("er143ge8-34gr-41ae-8723-237a3f675a30")),
                () -> assertEquals(verificationTokenById.getIdToken(), verificationToken.getIdToken()),
                () -> assertEquals(verificationTokenById.getToken(), verificationToken.getToken())
        );
    }
}