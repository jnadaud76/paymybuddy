package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.util.Calculator;
import com.paymybuddy.paymybuddy.util.IConversion;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IConversion conversionService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private Calculator calculator;


    public Set<TransactionFullDto> getTransactions() {
        Set<Transaction> transactionSet = transactionRepository.findAll();
        Set<TransactionFullDto> transactionFullDtoSet = new HashSet<>();
        for (Transaction transaction : transactionSet) {
            TransactionFullDto transactionFullDto = conversionService.transactionToFullDto(transaction);
            transactionFullDtoSet.add(transactionFullDto);
        }
        return transactionFullDtoSet;
    }

    public Set<TransactionFullDto> getTransactionsBySender(Integer senderId) {
        return getTransactions().stream()
                .filter(transactionFullDto -> transactionFullDto.getSender() == senderId)
                .collect(Collectors.toSet());
    }

    public TransactionFullDto getTransactionById(Integer id) {
        if (transactionRepository.existsById(id)) {
            Transaction transaction = transactionRepository.findById(id).get();
            return conversionService.transactionToFullDto(transaction);
        } else {
            return null;
        }
    }

    public Transaction addTransaction(TransactionFullDto transactionFullDto) {
        if (personService.isConnectionOf(transactionFullDto.getSender(), transactionFullDto.getRecipient())) {
            Transaction transaction = conversionService.fullDtoToTransaction(transactionFullDto);
            calculator.updateAmountAvailable(transactionFullDto.getRecipient()
                    , transactionFullDto.getSender()
                    , transactionFullDto.getAmount());
            return transactionRepository.save(transaction);
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void deleteTransactionById(Integer id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        } else {
            //LOGGER.error("Person doesn't exist in Set", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }

    }
}
