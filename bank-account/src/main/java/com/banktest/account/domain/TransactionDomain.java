package com.banktest.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class TransactionDomain {

    private long idTransaction;

    private long idAccount;

    private BigDecimal transactionAmount;

    private Instant transactionTimestamp;
}
