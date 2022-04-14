package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.PersonMailDto;

import java.util.Set;

public interface IPersonService {

    Set<PersonFullDto> getPersons();

    PersonFullDto getPersonById(Integer id);

    PersonFullDto addPerson(PersonFullDto personFullDto);

    void deletePersonById(Integer id);

    void addConnection(Integer personId, Integer connectionId);

    Set<PersonConnectionDto> getConnectionsFromPerson(Integer personId);

    void removeConnection(Integer personId, Integer connectionId);

    boolean isConnectionOf(Integer personId, Integer connectionId);

    void toIbanTransfer(Integer personID, Integer amount);

    void fromIbanTransfer(Integer personID, Integer amount);

    PersonFullDto getPersonByEmail(String email);

    Set<PersonMailDto> getPossibleConnection(Integer personId);

}
