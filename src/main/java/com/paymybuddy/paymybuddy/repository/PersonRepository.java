package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

import javax.persistence.Id;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

@Override
Set<Person> findAll();

}
