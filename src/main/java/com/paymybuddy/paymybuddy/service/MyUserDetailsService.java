package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Person;
import com.paymybuddy.paymybuddy.repository.PersonRepository;
import com.paymybuddy.paymybuddy.util.MyUserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String personEmail) {
        Person user = personRepository.findByEmail(personEmail);
        if (user == null) {
            throw new UsernameNotFoundException(personEmail);
        }
        return new MyUserPrincipal(user);
    }
}

