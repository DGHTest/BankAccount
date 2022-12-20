package com.banktest.account.domain.repository;

import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.persistence.entity.TransactionEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Optional<TransactionDomain> getTransactionById(long id);

    List<TransactionDomain> getByIdAccount(long idAccount);

    List<TransactionDomain> getByTimeAndIdAccount(Instant timestamp, long idAccount);

    TransactionDomain saveTransaction(TransactionEntity transactionEntity);
}
