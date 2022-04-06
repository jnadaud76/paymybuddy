package com.paymybuddy.paymybuddy.unit;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.controller.PersonController;
import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.PersonMailDto;

import com.paymybuddy.paymybuddy.service.IPersonService;
import com.paymybuddy.paymybuddy.service.MyUserDetailsService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

@WithMockUser
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MyUserDetailsService userDetailsService;
    @MockBean
    private IPersonService personService;

    @Test
    void TestGetPersons() throws Exception {
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetPossibleConnection() throws Exception {
        Set<PersonMailDto> personMailDtoSet = new HashSet<>();
        PersonMailDto personMailDto = new PersonMailDto();
        personMailDto.setId(2);
        personMailDto.setEmail("test@test.fr");
        personMailDtoSet.add(personMailDto);
        when(personService.getPossibleConnection(1)).thenReturn(personMailDtoSet);
        mockMvc.perform(get("/api/possibleconnections")
                        .queryParam("personId", "1"))
                .andExpect(status().isOk());


    }

    @Test
    void TestGetPossibleConnectionWithBadId() throws Exception {
        Set<PersonMailDto> personMailDtoSet = new HashSet<>();
        when(personService.getPossibleConnection(123)).thenReturn(personMailDtoSet);
        mockMvc.perform(get("/api/possibleconnections")
                        .queryParam("personId", "123"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void TestGetPersonById(int ints) throws Exception {
        PersonFullDto personFullDto = new PersonFullDto();
        personFullDto.setId(ints);
        when(personService.getPersonById(ints)).thenReturn(personFullDto);
        mockMvc.perform(get("/api/person")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 11, 12, 13})
    void TestGetPersonByIdWithBadId(int ints) throws Exception {
        when(personService.getPersonById(ints)).thenReturn(null);
        mockMvc.perform(get("/api/person")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestCreatePerson() throws Exception {
        PersonFullDto personFullDto = new PersonFullDto();
        personFullDto.setFirstName("test");
        personFullDto.setLastName("test");
        personFullDto.setAmountAvailable(0);
        personFullDto.setEmail("test@test.com");
        personFullDto.setPassword("Me1234567!");
        personFullDto.setIban("FR12345678912345678912345678");
        String personAsString = objectMapper.writeValueAsString(personFullDto);
        when(personService.addPerson(personFullDto)).thenReturn(personFullDto);

        mockMvc.perform(post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personAsString))
                .andExpect(status().isCreated());
    }

    @Test
    void TestCreatePersonWithEmailAlreadyInDataBase() throws Exception {
        PersonFullDto personFullDto = new PersonFullDto();
        personFullDto.setFirstName("test");
        personFullDto.setLastName("test");
        personFullDto.setAmountAvailable(0);
        personFullDto.setEmail("johndoe@hotmail.com");
        personFullDto.setPassword("Me1234567!");
        personFullDto.setIban("FR12345678912345678912345678");
        doThrow(new IllegalArgumentException()).when(personService).addPerson(personFullDto);

        mockMvc.perform(post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON))
                               .andExpect(status().isBadRequest());
    }

    @Test
    void TestAddConnection() throws Exception {
        personService.addConnection(1,3);
        mockMvc.perform(put("/api/person/connection/add")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "3"))
                .andExpect(status().isCreated());
    }

    @Test
    void TestAddConnectionWhichAlreadyExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(personService).addConnection(1,2);
        mockMvc.perform(put("/api/person/connection/add")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestAddConnectionWithBadArgument() throws Exception {
        doThrow(new IllegalArgumentException()).when(personService).addConnection(17,12);
        mockMvc.perform(put("/api/person/connection/add")
                        .queryParam("personId", "17")
                        .queryParam("connectionId", "12"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestRemoveConnection() throws Exception {
        personService.removeConnection(1,2);
        mockMvc.perform(put("/api/person/connection/remove")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void TestRemoveConnectionWhichAlreadyExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(personService).removeConnection(1,19);
        mockMvc.perform(put("/api/person/connection/remove")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "19"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestRemoveConnectionWithBadArgument() throws Exception {
        doThrow(new IllegalArgumentException()).when(personService).removeConnection(17,12);
        mockMvc.perform(put("/api/person/connection/remove")
                        .queryParam("personId", "17")
                        .queryParam("connectionId", "12"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestGetConnectionFromPerson() throws Exception {
        Set<PersonConnectionDto> personConnectionDtoSet = new HashSet<>();
        PersonConnectionDto personConnectionDto = new PersonConnectionDto();
        personConnectionDto.setId(2);
        personConnectionDto.setFirstName("test");
        personConnectionDto.setLastName("test");
        personConnectionDto.setEmail("test@test.fr");
        personConnectionDto.setAmountAvailable(0);
        personConnectionDtoSet.add(personConnectionDto);
        when(personService.getConnectionsFromPerson(1)).thenReturn(personConnectionDtoSet);
        mockMvc.perform(get("/api/connections")
                        .queryParam("personId", "1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 300, 1000})
    void TestToIban(int ints) throws Exception {
        personService.toIbanTransfer(1, ints);
        mockMvc.perform(put("/api/toiban")
                .queryParam("personId", "1")
                .queryParam("amount", String.valueOf(ints)))
        .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 300000})
    void TestToIbanWithBadValue(int ints) throws Exception {
        doThrow(new IllegalArgumentException()).when(personService).toIbanTransfer(1, ints);
        mockMvc.perform(put("/api/toiban")
                        .queryParam("personId", "1")
                        .queryParam("amount", String.valueOf(ints)))
                .andExpect(status().isBadRequest());

    }

    @ParameterizedTest
    @ValueSource(ints = {100, 300, 1000})
    void TestFromIban(int ints) throws Exception {
        personService.fromIbanTransfer(1, ints);
        mockMvc.perform(put("/api/fromiban")
                        .queryParam("personId", "1")
                        .queryParam("amount", String.valueOf(ints)))
                .andExpect(status().isOk());

    }


    @ParameterizedTest
    @ValueSource(ints = {17, 28, 99})
    void TestGetConnectionFromPersonWithBadId(int ints) throws Exception {
        Set<PersonConnectionDto> emptySet = new HashSet<>();
        when(personService.getConnectionsFromPerson(ints)).thenReturn(emptySet);
        mockMvc.perform(get("/api/connections")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestDeletePerson() throws Exception {
        personService.deletePersonById(5);
        mockMvc.perform(delete("/api/person")
                        .queryParam("personId", "5"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 32, 99})
    void TestDeletePersonWithBadId(int ints) throws Exception {
        doThrow(new IllegalArgumentException()).when(personService).deletePersonById(ints);
        mockMvc.perform(delete("/api/person")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isBadRequest());

    }


}