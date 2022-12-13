package com.banktest.account.domain.repository;

import com.banktest.account.domain.AccountDomain;
import com.banktest.account.domain.TransactionDomain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Optional<TransactionDomain> getTransactionById(long id);

    List<TransactionDomain> getByIdAccount(long idAccount);

    List<TransactionDomain> getByAfterTransactionTimeAndIdAccount(Timestamp timestamp, long idAccount);

    TransactionDomain saveTransaction(TransactionDomain transactionDomain);
}
