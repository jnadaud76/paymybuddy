package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.PersonMailDto;
import com.paymybuddy.paymybuddy.service.IPersonService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API for people CRUD operations.")
@RestController
@RequestMapping(value ="api")
//@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    IPersonService personService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @ApiOperation(value = "Retrieve all user.")
    @GetMapping("/persons")
    public Iterable<PersonFullDto> getPersons() {
        LOGGER.info("Persons successfully found - code : {}", HttpStatus.OK);
        return personService.getPersons();
    }

    @ApiOperation(value = "Retrieve all connections that can be added.")
    @GetMapping (value = "/possibleconnections")
    public ResponseEntity<Set<PersonMailDto>> getPossibleConnection (@RequestParam final Integer personId){
        if (!personService.getPossibleConnection(personId).isEmpty()) {
            LOGGER.info("Possible connections successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(personService.getPossibleConnection(personId));
        } else {
            LOGGER.error("Possible connections not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


   /* @GetMapping(value ="/email")
    public ResponseEntity<PersonFullDto>
    getPersonByEmail (@RequestParam final String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.getPersonByEmail(email));

    }*/

    @ApiOperation(value = "Retrieve one user by id.")
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

    @ApiOperation(value = "Create one user.")
    @PostMapping(value = "/person")
    public ResponseEntity<String> createPerson(@Valid @RequestBody final PersonFullDto personFullDto) {
        try {
            personService.addPerson(personFullDto);
            LOGGER.info("Person successfully created - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Person created");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Person can't be create - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Person can't be create. May already exist");
        }

    }

    @ApiOperation(value = "Add a connection to user.")
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

    @ApiOperation(value = "Remove a connection from user.")
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

    @ApiOperation(value = "Transfers money from user's application account to the user's bank account.")
    @PutMapping (value="/toiban")
    public ResponseEntity<String> toIban (@RequestParam final Integer personId, @RequestParam final Integer amount) {
        try {
            personService.toIbanTransfer(personId, amount);
            LOGGER.info("Transfer completed successfully - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Transfer completed successfully.");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Transfer failed - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Transfer failed. Please check that your balance is sufficient.");
        }

    }

    @ApiOperation(value = "Transfers money from user's bank account to user's application account.")
    @PutMapping (value="/fromiban")
    public ResponseEntity<String> fromIban (@RequestParam final Integer personId, @RequestParam final Integer amount) {
            personService.fromIbanTransfer(personId, amount);
            LOGGER.info("Transfer completed successfully - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Transfer completed successfully.");

    }

    @ApiOperation(value = "Retrieve all user's connections by user's id.")
    @GetMapping (value = "/connections")
    public ResponseEntity<Set<PersonConnectionDto>> getConnectionFromPerson(@RequestParam final Integer personId){
         if (!personService.getConnectionsFromPerson(personId).isEmpty()) {
            LOGGER.info("Connections successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(personService.getConnectionsFromPerson(personId));
        } else {
            LOGGER.error("Connections not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiOperation(value = "Remove a user.")
    @DeleteMapping(value = "/person")
    public ResponseEntity<String>
    deletePerson(@RequestParam final Integer personId) {
        try {
            personService.deletePersonById(personId);
            LOGGER.info("Person successfully deleted - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully deleted");

        } catch (IllegalArgumentException e) {
            LOGGER.error("Person can't be delete - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cant delete! Entity not exist");
        }

    }
}
