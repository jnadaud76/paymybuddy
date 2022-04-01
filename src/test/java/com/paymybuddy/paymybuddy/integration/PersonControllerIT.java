package com.paymybuddy.paymybuddy.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.model.Person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPossibleConnection() throws Exception {
        mockMvc.perform(get("/possibleconnections")
                        .queryParam("personId", "1"))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$[0].id",is(2)))
                //.andExpect(jsonPath("$[0].email",is("jeandurant@hotmail.fr")));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void GetPersonById(int ints) throws Exception {
        mockMvc.perform(get("/person")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8})
    void GetPersonByIdWithBadId(int ints) throws Exception {
        mockMvc.perform(get("/person")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePerson() throws Exception {
        Person person = new Person();
        person.setFirstName("test");
        person.setLastName("test");
        person.setAmountAvailable(0);
        person.setEmail("test@test.com");
        person.setPassword("Me1234567!");
        person.setIban("FR12345678912345678912345678");
        String personAsString = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personAsString))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePersonWithEmailAlreadyInDataBase() throws Exception {
        Person person = new Person();
        person.setFirstName("test");
        person.setLastName("test");
        person.setAmountAvailable(0);
        person.setEmail("johndoe@hotmail.com");
        person.setPassword("Me1234567!");
        person.setIban("FR12345678912345678912345678");
        String personAsString = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddConnection () throws Exception {
        mockMvc.perform(put("/person/connection/add")
                .queryParam("personId", "1")
                .queryParam("connectionId", "2"))
                .andExpect(status().isCreated());
    }

    @Test
    void testAddConnectionWhichAlreadyExist () throws Exception {
        mockMvc.perform(put("/person/connection/add")
                        .queryParam("personId", "2")
                        .queryParam("connectionId", "4"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddConnectionWithBadArgument () throws Exception {
        mockMvc.perform(put("/person/connection/add")
                        .queryParam("personId", "1")
                        .queryParam("connectionId", "12"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testGetConnectionFromPerson (int ints) throws Exception {
        mockMvc.perform(get("/connections")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 99})
    void testGetConnectionFromPersonWithBadId (int ints) throws Exception {
        mockMvc.perform(get("/connections")
                        .queryParam("personId", String.valueOf(ints)))
                .andExpect(status().isNotFound());
    }

   /* @Test
    void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/person)")
                .queryParam("personId", "5"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 32, 99})
    void testDeletePersonWithBadId(int ints) throws Exception {
        mockMvc.perform(delete("/person)")
                        .queryParam("personId", String.valueOf(ints)))
             */
}

