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

    public Set<PersonFullDto> getPersons(){
        Set<Person> persons = personRepository.findAll();
        Set<PersonFullDto> personFullDtoIterable = new HashSet<>();
        for (Person p : persons){
            PersonFullDto personFullDto = new PersonFullDto();
            personFullDto.setPersonFullDtoId(p.getPersonId());
            personFullDto.setFirstName(p.getFirstName());
            personFullDto.setLastName(p.getLastName());
            personFullDto.setEmail(p.getEmail());
            personFullDto.setPassword(p.getPassword());
            personFullDto.setIban(p.getIban());
            personFullDto.setAmountAvailable(p.getAmountAvailable());
            personFullDtoIterable.add(personFullDto);
        }
        return personFullDtoIterable;
    }

    public PersonFullDto getPersonById(Integer id) {
        if (personRepository.existsById(id)) {
            Person person = personRepository.findById(id).get();
            PersonFullDto personFullDto = new PersonFullDto();
            personFullDto.setPersonFullDtoId(person.getPersonId());
            personFullDto.setFirstName(person.getFirstName());
            personFullDto.setLastName(person.getLastName());
            personFullDto.setEmail(person.getEmail());
            personFullDto.setPassword(person.getPassword());
            personFullDto.setIban(person.getIban());
            personFullDto.setAmountAvailable(person.getAmountAvailable());
            return personFullDto;
        } else {
            return null;
        }
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
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            //LOGGER.error("Person doesn't exist in Set", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }
    }

    public void addConnection (Integer personId, Integer connectionId){
        Person person = personRepository.findById(personId).get();
        Person connection = personRepository.findById(connectionId).get();
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


    public void removeConnection (Integer personId, Integer connectionId) {
        Person person = personRepository.findById(personId).get();
        Person connection = personRepository.findById(connectionId).get();
        PersonConnectionDto personConnectionDto = new PersonConnectionDto();
        personConnectionDto.setPersonConnectionDtoId(connection.getPersonId());
        personConnectionDto.setFirstName(connection.getFirstName());
        personConnectionDto.setLastName(connection.getLastName());
        personConnectionDto.setEmail(connection.getEmail());
        personConnectionDto.setAmountAvailable(connection.getAmountAvailable());
        if (!(getConnectionsFromPerson(personId).contains(personConnectionDto))) {
            throw new IllegalArgumentException();
        } else {
            person.getConnections().remove(connection);
            personRepository.save(person);

        }
    }

    public Set<PersonConnectionDto> getConnectionsFromPerson (Integer personId){
        Person person = personRepository.findById(personId).get();
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
