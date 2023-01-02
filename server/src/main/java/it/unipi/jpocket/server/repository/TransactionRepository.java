package it.unipi.jpocket.server.repository;

import org.springframework.data.repository.CrudRepository;

import it.unipi.jpocket.server.model.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	Iterable<Transaction> findByUserId(Integer id);
	
}
