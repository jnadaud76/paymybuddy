package com.paymybuddy.paymybuddy.controller;



import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.service.IPersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value ="api")
public class AuthController {


    @Autowired
    private IPersonService personService;


    @GetMapping("/")
    public String login() {
        return "success";
    }

    @GetMapping(value = "/user")
    public ResponseEntity<PersonFullDto>
    getPersonByEmail(@RequestParam final String email) {
       return ResponseEntity.status(HttpStatus.OK)
                .body(personService.getPersonByEmail(email));

    }
}



