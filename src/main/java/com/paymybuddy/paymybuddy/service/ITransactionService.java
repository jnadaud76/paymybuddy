package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionLightDto;

import java.util.List;
import java.util.Set;

public interface ITransactionService {

    Set<TransactionFullDto> getTransactions();

    TransactionFullDto getTransactionById(Integer id);

    TransactionFullDto addTransaction(TransactionFullDto transactionFullDto);

    void deleteTransactionById(Integer id);

    List<TransactionLightDto> getTransactionsBySender(Integer senderId);


}
