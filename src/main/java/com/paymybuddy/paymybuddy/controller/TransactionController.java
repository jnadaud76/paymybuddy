package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.service.ITransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TransactionController {

    @Autowired
    ITransactionService transactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping("/transactions")
    public Iterable<TransactionFullDto> getTransactions() {
        LOGGER.info("Transactions successfully found - code : {}", HttpStatus.OK);
        return transactionService.getTransactions();
    }

    @GetMapping("/sender/transactions")
    public ResponseEntity<Set<TransactionFullDto>> getTransactionsBySender(@RequestParam final Integer senderId) {
        if (!(transactionService.getTransactionsBySender(senderId).isEmpty())) {
            LOGGER.info("Transactions successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionsBySender(senderId));
        } else {
            LOGGER.error("Transactions not founded - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(value = "/transaction")
    public ResponseEntity<TransactionFullDto>
    getTransactionById(@RequestParam final Integer transactionId) {
        if (transactionService.getTransactionById(transactionId) != null) {
            LOGGER.info("Transaction successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(transactionService.getTransactionById(transactionId));
        } else {
            LOGGER.error("Transaction not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<String> createTransaction(@RequestBody final TransactionFullDto transactionFullDto) {
        try {
            transactionService.addTransaction(transactionFullDto);
            LOGGER.info("Transaction successfully created - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Transaction created");
        } catch (Exception e) {
            LOGGER.error("Transaction can't be create - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Transaction can't be create. May already exist or your balance is insufficient.");
        }

    }

    @DeleteMapping(value = "/transaction")
    public ResponseEntity<String>
    deleteTransaction(@RequestParam final Integer transactionId) {
        try {
            transactionService.deleteTransactionById(transactionId);
            LOGGER.info("Transaction successfully deleted - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully deleted");

        } catch (Exception e) {
            LOGGER.error("Transaction can't be delete - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cant delete! Transaction not exist");
        }

    }
}
