package com.banktest.account.domain.service;

import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.domain.repository.PasswordResetTokenRepository;
import com.banktest.account.domain.repository.VerificationTokenRepository;
import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.entity.PasswordResetToken;
import com.banktest.account.persistence.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.getByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        AccountEntity accountEntity = verificationToken.getAccountEntity();
        Calendar cal = Calendar.getInstance();
        //Only for expired test: cal.add(Calendar.MINUTE, 10);

        if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        accountRepository.updateStatus(accountEntity.getIdAccount());
        return "valid";
    }

    public VerificationToken generateNewVerificationToken(String oldToken) {
        return verificationTokenRepository.updateToken(
                UUID.randomUUID().toString(), oldToken);
    }


    public void createPasswordResetTokenForAccount(AccountEntity accountEntity, String token) {
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .accountEntity(accountEntity)
                .build();

        passwordResetTokenRepository.savePasswordResetToken(passwordResetToken);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.getByToken(token);

        if (passwordResetToken == null) {
            return "invalid";
        }

        Calendar cal = Calendar.getInstance();
        //Only for expired test: cal.add(Calendar.MINUTE, 10);

        if ((passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    public Optional<AccountEntity> getAccountByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.getByToken(token).getAccountEntity());
    }
}
