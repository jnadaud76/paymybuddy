package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Person;
import com.paymybuddy.paymybuddy.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Person person = personRepository.findByEmail(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return User.withUsername(person.getEmail())
                    .password(person.getPassword())
                    .authorities("USER").build();
        }
    }
}

