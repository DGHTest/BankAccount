package com.banktest.account.domain.repository;

import com.banktest.account.persistence.entity.PasswordResetToken;

public interface PasswordResetTokenRepository {

    PasswordResetToken getByToken(String token);

    PasswordResetToken savePasswordResetToken(PasswordResetToken passwordResetToken);

    void delete(PasswordResetToken passwordResetToken);
}
