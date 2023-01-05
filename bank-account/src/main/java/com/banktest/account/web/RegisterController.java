package com.banktest.account.web;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.event.RegistrationCompleteEvent;
import com.banktest.account.domain.service.TokenService;
import com.banktest.account.domain.service.AccountService;
import com.banktest.account.model.PasswordModel;
import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.entity.VerificationToken;
import com.banktest.account.persistence.mapper.AccountMapper;
import com.banktest.account.persistence.mapper.AccountMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

//TODO: Cambiar nombre a uno que se adapte mejor
@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<String> registerAccount(@RequestBody AccountDomain accountDomain, final HttpServletRequest request) throws Exception {
        if (!accountDomain.getPassword().equals(accountDomain.getMatchPassword())) {
            return new ResponseEntity<>("Passwords don't match", HttpStatus.BAD_REQUEST);
        }

        AccountMapper accountMapper = new AccountMapperImpl();
        AccountEntity account = accountMapper.toAccountEntity(accountService.saveAccount(accountDomain));

        publisher.publishEvent(new RegistrationCompleteEvent(
                account,
                applicationUrl(request)
        ));

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam String token) {
        String result = tokenService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "Account verifies successfully";
        }

        return "Bad account";
    }

    @GetMapping("/resendToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = tokenService.generateNewVerificationToken(oldToken);

        AccountEntity account = verificationToken.getAccountEntity();
        resendVerificationTokenEmail(account, applicationUrl(request), verificationToken);

        return "Verification Link Sent";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        Optional<AccountDomain> accountDomain = accountService.getAccountByEmail(passwordModel.getEmail());

        String url = "";

        if (accountDomain.isPresent()) {
            AccountEntity accountEntity = accountMapper.toAccountEntity(accountDomain.get());
            String token = UUID.randomUUID().toString();
            tokenService.createPasswordResetTokenForAccount(accountEntity, token);

            url = passwordResetTokenEmail(accountEntity, applicationUrl(request), token);
        }

        return url;
    }

    @PostMapping("/savePassword")
    private String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = tokenService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return "Invalid token";
        }

        Optional<AccountEntity> accountEntity = tokenService.getAccountByPasswordResetToken(token);
        if (accountEntity.isPresent()) {
            accountService.changePassword(passwordModel.getNewPassword(), accountEntity.get().getIdAccount());
            return "Password Reset Successfully";
        }

        return "Invalid token";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        Optional<AccountEntity> accountEntity = accountService.getAccountByEmail(passwordModel.getEmail())
                .map(accountDomain -> accountMapper.toAccountEntity(accountDomain));

        if (accountEntity.isPresent()) {
            if (!accountService.checkIfValidOldPassword(accountEntity.get(), passwordModel.getOldPassword())) {
                return "Invalid Old Password";
            } else {
                accountService.changePassword(passwordModel.getNewPassword(), accountEntity.get().getIdAccount());
                return "Password Changed Successfully";
            }
        }

        return "Email doesn't exist";
    }

    private String passwordResetTokenEmail(AccountEntity accountEntity, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;

        log.info("Click the link to reset your password: {}", url);

        return url;
    }

    private void resendVerificationTokenEmail(AccountEntity account, String applicationUrl, VerificationToken token) {
        String url = applicationUrl + "/verifyRegistration?token=" + token.getToken();

        log.info("Click the link to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }
}
