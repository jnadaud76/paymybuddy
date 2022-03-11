package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Person;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.repository.PersonRepository;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> getPersons(){
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePersonById(Integer id) {
        personRepository.deleteById(id);
    }
}
