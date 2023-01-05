package it.unipi.jpocket.server.repository;

import org.springframework.data.repository.CrudRepository;

import it.unipi.jpocket.server.model.Transaction;
import it.unipi.jpocket.server.model.User;


public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	// Trova tutte le transazioni di un utente
	Iterable<Transaction> findByUser(User user);
}
