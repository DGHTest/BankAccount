package com.banktest.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDomain {

    private long idAccount;

    private String accountName;

    private String email;

    private String password;

    private String matchPassword;

    private BigDecimal currentBalance;
}
