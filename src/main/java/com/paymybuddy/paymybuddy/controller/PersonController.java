package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.service.IPersonService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class PersonController {

    @Autowired
    IPersonService personService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/persons")
    public Iterable<PersonFullDto> getPersons() {
        LOGGER.info("Persons successfully found - code : {}", HttpStatus.OK);
        return personService.getPersons();
    }

    @GetMapping(value = "/person")
    public ResponseEntity<PersonFullDto>
    getPersonById (@RequestParam final Integer personId) {
        if (personService.getPersonById(personId) != null) {
           LOGGER.info("Person successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(personService.getPersonById(personId));
        } else {
            LOGGER.error("Person not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(value = "/person")
    public ResponseEntity<String> createPerson(@RequestBody final PersonFullDto personFullDto) {
        try {
            personService.addPerson(personFullDto);
            LOGGER.info("Person successfully created - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Person created");
        } catch (Exception e) {
            LOGGER.error("Person can't be create - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Person can't be create. May already exist");
        }

    }

    @PutMapping (value = "person/connection/add")
    public ResponseEntity<String> addConnection(@RequestParam final Integer personId, @RequestParam final Integer connectionId) {
        try {
            personService.addConnection(personId, connectionId);
            LOGGER.info("Connection successfully added - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Connection added");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Connection can't be added - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Connection can't be added. May already exist");
        }

    }

    @PutMapping(value = "person/connection/remove")
    public ResponseEntity<String> removeConnection(@RequestParam final Integer personId, @RequestParam final Integer connectionId) {
        try {
            personService.removeConnection(personId, connectionId);
            LOGGER.info("Connection successfully remove - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Connection remove");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Connection can't be removed - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Connection can't be removed. May not exist");
        }

    }

    @GetMapping (value = "/connections")
    public Set<PersonConnectionDto> getConnectionFromPerson(@RequestParam final Integer personId){
        return personService.getConnectionsFromPerson(personId);
    }

    @DeleteMapping(value = "/person")
    public ResponseEntity<String>
    deletePerson(@RequestParam final Integer personId) {
        try {
            personService.deletePersonById(personId);
            LOGGER.info("Person successfully deleted - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully deleted");

        } catch (Exception e) {
            LOGGER.error("Person can't be delete - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cant delete! Entity not exist");
        }

    }
}
