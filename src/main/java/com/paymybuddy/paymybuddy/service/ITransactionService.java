package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Transaction;

import java.util.Optional;

public interface ITransactionService {

    Iterable<Transaction> getTransactions();

    Optional<Transaction> getTransactionById(Integer id);

    Transaction addTransaction(Transaction transaction);

    void deleteTransactionById(Integer id);
}
