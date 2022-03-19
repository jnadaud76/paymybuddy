package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.model.Person;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConversionService implements IConversionService {

    @Autowired
    PersonRepository personRepository;

    @Value("${feeRate}")
    private double feeRate;

    public PersonFullDto personToFullDto(final Person person) {
        PersonFullDto personFullDto = new PersonFullDto();
        personFullDto.setPersonFullDtoId(person.getPersonId());
        personFullDto.setFirstName(person.getFirstName());
        personFullDto.setLastName(person.getLastName());
        personFullDto.setEmail(person.getEmail());
        personFullDto.setPassword(person.getPassword());
        personFullDto.setIban(person.getIban());
        personFullDto.setAmountAvailable(person.getAmountAvailable());
        return personFullDto;
    }

    public Person fullDtoToPerson(final PersonFullDto personFullDto) {
        Person person = new Person();
        person.setFirstName(personFullDto.getFirstName());
        person.setLastName(personFullDto.getLastName());
        person.setEmail(personFullDto.getEmail());
        person.setPassword(personFullDto.getPassword());
        person.setIban(personFullDto.getIban());
        person.setAmountAvailable(personFullDto.getAmountAvailable());
        return person;
    }

    public PersonConnectionDto connectionToConnectionDto (final Person connection) {
        PersonConnectionDto personConnectionDto = new PersonConnectionDto();
        personConnectionDto.setPersonConnectionDtoId(connection.getPersonId());
        personConnectionDto.setFirstName(connection.getFirstName());
        personConnectionDto.setLastName(connection.getLastName());
        personConnectionDto.setEmail(connection.getEmail());
        personConnectionDto.setAmountAvailable(connection.getAmountAvailable());
        return personConnectionDto;
    }

   public Transaction fullDtoToTransaction (final TransactionFullDto transactionFullDto){
       Transaction transaction = new Transaction();
       transaction.setDate(LocalDateTime.now());
       transaction.setRecipient(personRepository.findById(transactionFullDto.getRecipient()).get());
       transaction.setSender(personRepository.findById(transactionFullDto.getSender()).get());
       transaction.setAmount(transactionFullDto.getAmount());
       transaction.setDescription(transactionFullDto.getDescription());
       transaction.setFeeAmount(transactionFullDto.getAmount()*feeRate);
       return transaction;
    }

    /*public TransactionFullDto transactionToFullDto (final Transaction transaction){

    }*/
}
