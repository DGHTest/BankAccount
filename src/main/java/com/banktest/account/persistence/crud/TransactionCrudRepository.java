package com.banktest.account.persistence.crud;

import com.banktest.account.persistence.entity.AccountEntity;
import com.banktest.account.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TransactionCrudRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByIdAccount(long idAccount);

    List<TransactionEntity> findByTransactionTimestampAfterAndIdAccount(Instant transactionTimestamp, long idAccount);
}
