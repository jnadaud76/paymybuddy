package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.util.Calculator;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IConversionService conversionService;

    @Autowired
    private Calculator calculator;


    public Set<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
            }

    public Transaction addTransaction(TransactionFullDto transactionFullDto) {
        Transaction transaction = conversionService.fullDtoToTransaction(transactionFullDto);
        calculator.updateAmountAvailable(transactionFullDto.getRecipient(),transactionFullDto.getSender(), transactionFullDto.getAmount());
        return transactionRepository.save(transaction);
    }

    public void deleteTransactionById(Integer id) {
        transactionRepository.deleteById(id);
    }
}
