package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.service.IPersonService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/person")
    public ResponseEntity<String> createPerson(@RequestBody final PersonFullDto personFullDto) {
        try {
            personService.addPerson(personFullDto);
            //LOGGER.info("Person successfully created - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Person created");
        } catch (Exception e) {
            //LOGGER.error("Person can't be create - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Person can't be create. May already exist");
        }

    }

    @PutMapping (value = "/connection")
    public ResponseEntity<String> addConnection(@RequestParam final Integer personId, @RequestParam final Integer connectionId) {
        try {
            personService.addConnection(personId, connectionId);
            //LOGGER.info("Person successfully created - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Connection added");
        } catch (Exception e) {
            //LOGGER.error("Person can't be create - code : {}", HttpStatus.BAD_REQUEST, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Connection can't be added. May already exist");
        }

    }

    @GetMapping (value = "/connections")
    public Set<PersonConnectionDto> getConnectionFromPerson(@RequestParam final Integer personId){
        return personService.getConnectionsFromPerson(personId);
    }
}
