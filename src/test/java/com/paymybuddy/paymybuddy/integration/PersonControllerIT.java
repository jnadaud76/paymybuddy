package com.paymybuddy.paymybuddy.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.model.Person;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class PersonControllerIT {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void TestGetPersons() throws Exception {
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void TestGetPossibleConnection(int ints) throws Exception {
        mockMvc.perform(get("/api/possibleconnections")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isOk());
              // .andExpect(jsonPath("$.id", contains(4)))
               //.andExpect(jsonPath("$.email", contains("patrickdupont@gmail.com")));

    }

   @Test
    void TestGetPossibleConnectionWithBadId() throws Exception {
        mockMvc.perform(get("/api/possibleconnections")
                        .queryParam("personId", "123"))
                .andExpect(status().isNotFound());
        // .andExpect(jsonPath("$.id", contains(4)))
        //.andExpect(jsonPath("$.email", contains("patrickdupont@gmail.com")));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void TestGetPersonById(int ints) throws Exception {
        mockMvc.perform(get("/api/person")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 11, 12, 13})
    void TestGetPersonByIdWithBadId(int ints) throws Exception {
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

        mockMvc.perform(post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personAsString))
                .andExpect(status().isCreated());
    }

    @Test
    void TestCreatePersonWithEmailAlreadyInDataBase() throws Exception {
        Person person = new Person();
        person.setFirstName("test");
        person.setLastName("test");
        person.setAmountAvailable(0);
        person.setEmail("johndoe@hotmail.com");
        person.setPassword("Me1234567!");
        person.setIban("FR12345678912345678912345678");
        String personAsString = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestAddConnection() throws Exception {
        mockMvc.perform(put("/api/person/connection/add")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "3"))
                .andExpect(status().isCreated());
    }

    @Test
    void TestAddConnectionWhichAlreadyExist() throws Exception {
        mockMvc.perform(put("/api/person/connection/add")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestAddConnectionWithBadArgument() throws Exception {
        mockMvc.perform(put("/api/person/connection/add")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "12"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestRemoveConnection() throws Exception {
            mockMvc.perform(put("/api/person/connection/remove")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "3"))
                .andExpect(status().isOk());
    }

    @Test
    void TestRemoveConnectionWhichAlreadyExist() throws Exception {
              mockMvc.perform(put("/api/person/connection/remove")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "19"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestRemoveConnectionWithBadArgument() throws Exception {
              mockMvc.perform(put("/api/person/connection/remove")
                        .queryParam("personId", "17")
                        .queryParam("connectionId", "12"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void TestGetConnectionFromPerson() throws Exception {
        mockMvc.perform(get("/api/connections")
                        .queryParam("personId", "1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {17, 28, 99})
    void TestGetConnectionFromPersonWithBadId(int ints) throws Exception {
        mockMvc.perform(get("/api/connections")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestToIban() throws Exception {
               mockMvc.perform(put("/api/toiban")
                        .queryParam("personId", "1")
                        .queryParam("amount", "100"))
                .andExpect(status().isOk());

    }

    @Test
    void TestToIbanWithBadValue() throws Exception {
           mockMvc.perform(put("/api/toiban")
                        .queryParam("personId", "1")
                        .queryParam("amount", "10000"))
                .andExpect(status().isBadRequest());

    }


    @Test
    void TestFromIban() throws Exception {
            mockMvc.perform(put("/api/fromiban")
                        .queryParam("personId", "1")
                        .queryParam("amount", "1000"))
                .andExpect(status().isOk());

    }

    @Test
    void TestDeletePerson() throws Exception {
        mockMvc.perform(delete("/api/person")
                        .queryParam("personId", "5"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 32, 99})
    void TestDeletePersonWithBadId(int ints) throws Exception {
        mockMvc.perform(delete("/api/person)")
                .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isNotFound());

    }
}

