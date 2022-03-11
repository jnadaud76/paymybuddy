package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
