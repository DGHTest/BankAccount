package com.banktest.account.web;

import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions-type")
public class TransactionTypeController {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @PostMapping(value = "/save-deposit", consumes = {"application/json"})
    public ResponseEntity<TransactionDomain> saveDepositTransaction(TransactionDomain transactionDomain) {
        return new ResponseEntity<>(transactionTypeService.saveDepositTransaction(transactionDomain), HttpStatus.CREATED);
    }

    @PostMapping(value = "/save-check", consumes = {"application/json"})
    public ResponseEntity<TransactionDomain> saveCheckTransaction(TransactionDomain transactionDomain) {
        return new ResponseEntity<>(transactionTypeService.saveCheckTransaction(transactionDomain), HttpStatus.CREATED);
    }

    @PostMapping(value = "/save-online-payment", consumes = {"application/json"})
    public ResponseEntity<TransactionDomain> saveOnlinePaymentTransaction(TransactionDomain transactionDomain) {
        return new ResponseEntity<>(transactionTypeService.saveOnlinePaymentTransaction(transactionDomain), HttpStatus.CREATED);
    }

    @PostMapping(value = "/save-transfer", consumes = {"application/json"})
    public ResponseEntity<TransactionDomain> saveWireTransferTransaction(TransactionDomain transactionDomain) {
        return new ResponseEntity<>(transactionTypeService.saveWireTransferTransaction(transactionDomain), HttpStatus.CREATED);
    }
}
