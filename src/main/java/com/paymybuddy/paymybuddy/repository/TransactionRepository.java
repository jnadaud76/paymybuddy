package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
