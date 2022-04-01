package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionLightDto;
import com.paymybuddy.paymybuddy.model.Transaction;

import java.util.Set;

public interface ITransactionService {

    Set<TransactionFullDto> getTransactions();

    TransactionFullDto getTransactionById(Integer id);

    Transaction addTransaction(TransactionFullDto transactionFullDto);

    void deleteTransactionById(Integer id);

    Set<TransactionLightDto> getTransactionsBySender(Integer senderId);


}
