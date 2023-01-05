package com.banktest.account.domain.repository;

import com.banktest.account.persistence.entity.VerificationToken;

public interface VerificationTokenRepository {
    VerificationToken getByToken(String token);

    void delete(VerificationToken verificationToken);

    VerificationToken updateToken(String newToken, String oldToken);
}
