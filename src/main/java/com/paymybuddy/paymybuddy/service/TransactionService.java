package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionLightDto;
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

    public Set<TransactionLightDto> getTransactionsBySender(Integer senderId) {
        Set<TransactionLightDto> transactionLightDtoSet = new HashSet<>();
        Set<TransactionFullDto> transactionFullDtoSet = getTransactions().stream()
                .filter(transactionFullDto -> transactionFullDto.getSender() == senderId)
                .collect(Collectors.toSet());
         for (TransactionFullDto t : transactionFullDtoSet){
             TransactionLightDto transactionLightDto = new TransactionLightDto();
             transactionLightDto.setId(t.getId());
             transactionLightDto.setRecipient(personService.getPersonById(t.getRecipient()).getLastName()
             );
             transactionLightDto.setAmount(t.getAmount());
             transactionLightDto.setDescription(t.getDescription());
             transactionLightDtoSet.add(transactionLightDto);
         }
         return transactionLightDtoSet;
    }

    public TransactionFullDto getTransactionById(Integer id) {
        if (transactionRepository.existsById(id)) {
            Transaction transaction = transactionRepository.findById(id).get();
            return conversionService.transactionToFullDto(transaction);
        } else {
            return null;
        }
    }

    public TransactionFullDto addTransaction(TransactionFullDto transactionFullDto) {
        if (personService.isConnectionOf(transactionFullDto.getSender(), transactionFullDto.getRecipient())) {
            Transaction transaction = conversionService.fullDtoToTransaction(transactionFullDto);
            calculator.updateAmountAvailable(transactionFullDto.getRecipient()
                    , transactionFullDto.getSender()
                    , transactionFullDto.getAmount());
            transactionRepository.save(transaction);
            return transactionFullDto;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void deleteTransactionById(Integer id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException();
        }

    }
}
