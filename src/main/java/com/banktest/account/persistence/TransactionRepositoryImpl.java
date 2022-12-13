package com.banktest.account.persistence;

import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.repository.TransactionRepository;
import com.banktest.account.persistence.crud.TransactionCrudRepository;
import com.banktest.account.persistence.entity.TransactionEntity;
import com.banktest.account.persistence.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private TransactionCrudRepository transactionCrudRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public Optional<TransactionDomain> getTransactionById(long id) {
        return transactionCrudRepository.findById(id)
                .map(transaction -> transactionMapper.toTransactionDomain(transaction));
    }

    @Override
    public List<TransactionDomain> getByIdAccount(long idAccount) {
        List<TransactionEntity> transactionEntities = transactionCrudRepository.findByIdAccount(idAccount);
        return transactionMapper.toTransactionDomains(transactionEntities);
    }

    @Override
    public List<TransactionDomain> getByAfterTransactionTimeAndIdAccount(Timestamp timestamp, long idAccount) {
        List<TransactionEntity> transactionEntities = transactionCrudRepository.findByTransactionTimestampAfterAndIdAccount(timestamp, idAccount);
        return transactionMapper.toTransactionDomains(transactionEntities);
    }

    @Override
    public TransactionDomain saveTransaction(TransactionDomain transactionDomain) {
        TransactionEntity transactionEntity = transactionMapper.toTransactionEntity(transactionDomain);
        return transactionMapper.toTransactionDomain(transactionCrudRepository.save(transactionEntity));
    }
}
