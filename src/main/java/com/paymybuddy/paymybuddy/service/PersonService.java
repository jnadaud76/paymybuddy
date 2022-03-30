package com.paymybuddy.paymybuddy.service;


import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.PersonMailDto;
import com.paymybuddy.paymybuddy.model.Person;

import com.paymybuddy.paymybuddy.repository.PersonRepository;
import com.paymybuddy.paymybuddy.util.Calculator;
import com.paymybuddy.paymybuddy.util.IConversion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private IConversion conversionService;

    @Autowired
    private Calculator calculator;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Set<PersonFullDto> getPersons() {
        Set<Person> persons = personRepository.findAll();
        Set<PersonFullDto> personFullDtoSet = new HashSet<>();
        for (Person person : persons) {
            PersonFullDto personFullDto = conversionService.personToFullDto(person);
            personFullDtoSet.add(personFullDto);
        }
        return personFullDtoSet;
    }

    public PersonFullDto getPersonByEmail (String email){
        Person person= personRepository.findByEmail(email);
        return conversionService.personToFullDto(person);
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
        Person person = conversionService.fullDtoToPerson(personFullDto);
        person.setPassword(passwordEncoder().encode(person.getPassword()));
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

    public Set<PersonMailDto> getPossibleConnection (Integer personId) {
      Set<PersonMailDto> personMailDtoSet = new HashSet<>();
        Set<PersonFullDto> personFullDtoSet = getPersons().stream().filter(PersonFullDto -> PersonFullDto.getId() != personId
      ).collect(Collectors.toSet());
        Set<PersonConnectionDto> persons= getConnectionsFromPerson(personId);
       /* Set<PersonFullDto> persons = personFullDtoSet.stream().filter(PersonFullDto -> PersonFullDto.getId() != getConnectionsFromPerson(personId).stream().iterator().next().getId()
              ).collect(Collectors.toSet());*/
      for (PersonConnectionDto pcd : persons) {
          personFullDtoSet.removeIf(p -> pcd.getId() == p.getId());
      }
              for (PersonFullDto p : personFullDtoSet) {
              PersonMailDto personMailDto = new PersonMailDto();
              personMailDto.setEmail(p.getEmail());
              personMailDtoSet.add(personMailDto);
      }
      return personMailDtoSet;
    }

    public void addConnection(Integer personId, Integer connectionId) {
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


    public void removeConnection(Integer personId, Integer connectionId) {
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

    public Set<PersonConnectionDto> getConnectionsFromPerson(Integer personId) {
        Person person = personRepository.findById(personId).get();
        Set<PersonConnectionDto> connectionDtoSet = new HashSet<>();
        for (Person connection : person.getConnections()) {
            PersonConnectionDto personConnectionDto = conversionService.connectionToConnectionDto(connection);
            connectionDtoSet.add(personConnectionDto);
        }
        return connectionDtoSet;
    }

    public void toIbanTransfer(Integer personId, Integer amount) {
    calculator.updateAmountToIban(personId,amount);
    personRepository.save(personRepository.findById(personId).get());
    }

    public void fromIbanTransfer(Integer personId, Integer amount){
    calculator.updateAmountFromIban(personId, amount);
        personRepository.save(personRepository.findById(personId).get());
    }

    public boolean isConnectionOf(Integer personId, Integer connectionId) {
        return !getConnectionsFromPerson(personId).stream()
                .filter(personConnectionDto -> personConnectionDto
                        .getId() == connectionId)
                .collect(Collectors.toSet()).isEmpty();
    }
}

