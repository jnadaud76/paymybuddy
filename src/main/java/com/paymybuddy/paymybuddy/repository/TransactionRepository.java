package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.model.Transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Override
    Set<Transaction> findAll();


}
