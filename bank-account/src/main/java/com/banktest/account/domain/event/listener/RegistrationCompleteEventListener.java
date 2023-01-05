package com.banktest.account.domain.event.listener;

import com.banktest.account.domain.event.RegistrationCompleteEvent;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.persistence.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        AccountEntity accountEntity = event.getAccountEntity();
        String token = UUID.randomUUID().toString();
        accountRepository.saveVerificationToken(token, accountEntity);

        //TODO: Hay que cambiar el url para que se adapte mejor al "registeController"
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        log.info("Click the link to verify your account: {}", url);
    }
}
