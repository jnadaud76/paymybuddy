package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.model.Person;

import java.util.Set;

public interface IPersonService {
    Set<PersonFullDto> getPersons();

    PersonFullDto getPersonById(Integer id);

    Person addPerson(PersonFullDto personFullDto);

    void deletePersonById(Integer id);

    void addConnection(Integer personId, Integer connectionId);

    Set<PersonConnectionDto> getConnectionsFromPerson (Integer personId);

    void removeConnection (Integer personId, Integer connectionId);

}
