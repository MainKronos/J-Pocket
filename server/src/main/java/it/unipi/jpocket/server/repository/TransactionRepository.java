package it.unipi.jpocket.server.repository;

import org.springframework.data.repository.CrudRepository;

import it.unipi.jpocket.server.model.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	// Trova tutte le transazioni di un utente
	Iterable<Transaction> findByUserId(Integer id);


	// Trova la transazione appartenente ad un utente con un certo id
	Transaction findByUserIdAndId(Integer user_id, Integer transaction_id);
}
