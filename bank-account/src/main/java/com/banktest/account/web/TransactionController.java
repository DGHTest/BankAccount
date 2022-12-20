package com.banktest.account.web;

import com.banktest.account.domain.TransactionDomain;
import com.banktest.account.domain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDomain> getTransactionById(@PathVariable long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<TransactionDomain>> getByIdAccount(@PathVariable("id") long idAccount) {
        List<TransactionDomain> transactionDomainList = transactionService.getByIdAccount(idAccount);

        if (!transactionDomainList.isEmpty()) {
            return new ResponseEntity<>(transactionDomainList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/account/{id}/time/{time}")
    public ResponseEntity<List<TransactionDomain>> getByTimeAndIdAccount(@PathVariable("time") Instant timestamp, @PathVariable("id") long idAccount) {
        List<TransactionDomain> transactionDomainList = transactionService.getByTimeAndIdAccount(timestamp, idAccount);

        if (!transactionDomainList.isEmpty()) {
            return new ResponseEntity<>(transactionDomainList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
