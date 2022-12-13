package com.banktest.account.domain;

import com.banktest.account.constants.TransactionType;
import com.banktest.account.persistence.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDomain {

    private long idTransaction;

    private long idAccount;

    private BigDecimal transactionAmount;

    private Timestamp transactionTimestamp;
}
