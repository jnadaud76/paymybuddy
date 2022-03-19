package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.service.ITransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    ITransactionService transactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping(value = "/transaction")
    public ResponseEntity<String> createPerson(@RequestBody final TransactionFullDto transactionFullDto) {
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
}
