package com.banktest.account.crud;

import com.banktest.account.persistence.crud.VerificationTokenCrudRepository;
import com.banktest.account.persistence.entity.VerificationToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
@Sql("/db/bankaccount_data.sql")
class VerificationTokenCrudRepositoryTest {

    @Autowired
    private VerificationTokenCrudRepository verificationTokenCrud;

    @Test
    void findByToken() {
        VerificationToken verificationToken = verificationTokenCrud.findByToken("7f1a71e8-9b58-41ae-8723-29d7ff675a30");

        assertAll(
                () -> assertEquals(2, verificationToken.getIdToken()),
                () -> assertEquals("7f1a71e8-9b58-41ae-8723-29d7ff675a30", verificationToken.getToken()),
                () -> assertEquals("2022-12-25 14:10:05.602", verificationToken.getExpirationTime().toString())
        );
    }

    @Test
    void deleteToken() {
        VerificationToken verificationToken = verificationTokenCrud.findById(1l).get();

        verificationTokenCrud.delete(verificationToken);

        assertFalse(verificationTokenCrud.existsById(1l));
    }

    @Test
    void updateToken() {
        String newToken = UUID.randomUUID().toString();

        verificationTokenCrud.updateTokenById(newToken, "7f1a71e8-9b58-41ae-8723-29d7ff675a30");

        VerificationToken verificationToken = verificationTokenCrud.findByToken(newToken);

                assertAll(
                () -> assertEquals(newToken, verificationToken.getToken()),
                () -> assertEquals(2, verificationToken.getIdToken())
        );
    }
}