package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.model.Transaction;

import java.util.Optional;
import java.util.Set;

public interface ITransactionService {

    Set<Transaction> getTransactions();

    Optional<Transaction> getTransactionById(Integer id);

    Transaction addTransaction(TransactionFullDto transactionFullDto);

    void deleteTransactionById(Integer id);
}
