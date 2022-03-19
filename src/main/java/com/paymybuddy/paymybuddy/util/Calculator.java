package com.paymybuddy.paymybuddy.util;

import com.paymybuddy.paymybuddy.model.Person;
import com.paymybuddy.paymybuddy.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Calculator {

    @Autowired
    PersonRepository personRepository;

    public void updateAmountAvailable(Integer recipientId, Integer senderId, Integer amount) {
        Person recipient = personRepository.findById(recipientId).get();
        Person sender = personRepository.findById(senderId).get();
        if (sender.getAmountAvailable() >= amount) {
            sender.setAmountAvailable(sender.getAmountAvailable() - amount);
            recipient.setAmountAvailable(recipient.getAmountAvailable() + amount);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
