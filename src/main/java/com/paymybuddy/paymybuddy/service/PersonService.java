package com.paymybuddy.paymybuddy.service;


import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.model.Person;

import com.paymybuddy.paymybuddy.repository.PersonRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    /*public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    public Iterable<Person> getPersons(){
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public Person addPerson(PersonFullDto personFullDto) {
        /*Person person1 = new Person();
        person1.setPassword(passwordEncoder().encode(person.getPassword()));*/
        Person person = new Person();
        person.setFirstName(personFullDto.getFirstName());
        person.setLastName(personFullDto.getLastName());
        person.setEmail(personFullDto.getEmail());
        person.setPassword(personFullDto.getPassword());
        person.setIban(personFullDto.getIban());
        person.setAmountAvailable(personFullDto.getAmountAvailable());
        return personRepository.save(person);
    }

    public void deletePersonById(Integer id) {
        personRepository.deleteById(id);
    }

    public void addConnection (Integer personId, Integer connectionId){
        Person person = getPersonById(personId).get();
        Person connection = getPersonById(connectionId).get();
        PersonConnectionDto personConnectionDto = new PersonConnectionDto();
        personConnectionDto.setPersonConnectionDtoId(connection.getPersonId());
        personConnectionDto.setFirstName(connection.getFirstName());
        personConnectionDto.setLastName(connection.getLastName());
        personConnectionDto.setEmail(connection.getEmail());
        personConnectionDto.setAmountAvailable(connection.getAmountAvailable());
        if (getConnectionsFromPerson(personId).contains(personConnectionDto)) {
            throw new IllegalArgumentException();
        } else {
            person.getConnections().add(connection);
            personRepository.save(person);

        }
    }

    public Set<PersonConnectionDto> getConnectionsFromPerson (Integer personId){
        Person person = getPersonById(personId).get();
        Set<PersonConnectionDto> connectionDtoSet = new HashSet<>();

        for (Person p : person.getConnections()){
            PersonConnectionDto personConnectionDto = new PersonConnectionDto();
            personConnectionDto.setPersonConnectionDtoId(p.getPersonId());
            personConnectionDto.setFirstName(p.getFirstName());
            personConnectionDto.setLastName(p.getLastName());
            personConnectionDto.setEmail(p.getEmail());
            personConnectionDto.setAmountAvailable(p.getAmountAvailable());
            connectionDtoSet.add(personConnectionDto);
        }
        return connectionDtoSet;
    }
}
