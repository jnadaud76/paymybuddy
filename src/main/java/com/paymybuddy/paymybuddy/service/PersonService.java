package com.paymybuddy.paymybuddy.service;


import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.model.Person;

import com.paymybuddy.paymybuddy.repository.PersonRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private IConversionService conversionService;

    /*public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    public Set<PersonFullDto> getPersons(){
        Set<Person> persons = personRepository.findAll();
        Set<PersonFullDto> personFullDtoSet = new HashSet<>();
        for (Person person : persons){
            PersonFullDto personFullDto = conversionService.personToFullDto(person);
            personFullDtoSet.add(personFullDto);
        }
        return personFullDtoSet;
    }

    public PersonFullDto getPersonById(Integer id) {
        if (personRepository.existsById(id)) {
            Person person = personRepository.findById(id).get();
            return conversionService.personToFullDto(person);
        } else {
            return null;
        }
    }

    public Person addPerson(PersonFullDto personFullDto) {
        /*Person person1 = new Person();
        person1.setPassword(passwordEncoder().encode(person.getPassword()));*/
        Person person = conversionService.fullDtoToPerson(personFullDto);
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
        PersonConnectionDto personConnectionDto = conversionService.connectionToConnectionDto(connection);
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
        PersonConnectionDto personConnectionDto = conversionService.connectionToConnectionDto(connection);
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
        for (Person connection : person.getConnections()){
            PersonConnectionDto personConnectionDto = conversionService.connectionToConnectionDto(connection);
            connectionDtoSet.add(personConnectionDto);
        }
        return connectionDtoSet;
    }
}
