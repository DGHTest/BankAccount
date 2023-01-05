package com.banktest.account.domain.service;

import com.banktest.account.constants.TransactionType;
import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.repository.AccountRepository;
import com.banktest.account.domain.repository.TransactionRepository;
import com.banktest.account.persistence.entity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionTypeService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public TransactionDomain saveDepositTransaction(TransactionDomain transactionDomain) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .idTransaction(transactionDomain.getIdTransaction())
                .idAccount(transactionDomain.getIdAccount())
                .transactionAmount(transactionDomain.getTransactionAmount())
                .transactionType(TransactionType.DEPOSIT)
                .build();

        BigDecimal currentBalance = accountRepository.getAccountById(transactionDomain.getIdTransaction()).get().getCurrentBalance();
        currentBalance = currentBalance.add(transactionDomain.getTransactionAmount());

        accountRepository.updateBalance(currentBalance, transactionDomain.getIdAccount());

        return transactionRepository.saveTransaction(transactionEntity);
    }

    public TransactionDomain saveCheckTransaction(TransactionDomain transactionDomain) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .idTransaction(transactionDomain.getIdTransaction())
                .idAccount(transactionDomain.getIdAccount())
                .transactionAmount(new BigDecimal("0.00"))
                .transactionType(TransactionType.CHECK)
                .build();

        return transactionRepository.saveTransaction(transactionEntity);
    }

    public TransactionDomain saveOnlinePaymentTransaction(TransactionDomain transactionDomain) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .idTransaction(transactionDomain.getIdTransaction())
                .idAccount(transactionDomain.getIdAccount())
                .transactionAmount(transactionDomain.getTransactionAmount())
                .transactionType(TransactionType.ONLINE_PAYMENT)
                .build();

        BigDecimal currentBalance = accountRepository.getAccountById(transactionDomain.getIdTransaction()).get().getCurrentBalance();
        currentBalance = currentBalance.subtract(transactionDomain.getTransactionAmount());

        accountRepository.updateBalance(currentBalance, transactionDomain.getIdAccount());

        return transactionRepository.saveTransaction(transactionEntity);
    }

    public TransactionDomain saveWireTransferTransaction(TransactionDomain transactionDomain) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .idTransaction(transactionDomain.getIdTransaction())
                .idAccount(transactionDomain.getIdAccount())
                .transactionAmount(transactionDomain.getTransactionAmount())
                .transactionType(TransactionType.WIRE_TRANSFER)
                .build();

        BigDecimal currentBalance = accountRepository.getAccountById(transactionDomain.getIdTransaction()).get().getCurrentBalance();
        currentBalance = currentBalance.subtract(transactionDomain.getTransactionAmount());

        accountRepository.updateBalance(currentBalance, transactionDomain.getIdAccount());

        return transactionRepository.saveTransaction(transactionEntity);
    }
}
