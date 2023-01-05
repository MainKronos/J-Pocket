package it.unipi.jpocket.server.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unipi.jpocket.server.model.Transaction;
import it.unipi.jpocket.server.model.User;
import it.unipi.jpocket.server.repository.TransactionRepository;
import it.unipi.jpocket.server.repository.UserRepository;

@Controller
@RequestMapping("/api") 
public class MainController {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user")
	public @ResponseBody Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/transaction")
	public @ResponseBody Iterable<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	@GetMapping("/user/{user_id}")
	public @ResponseBody ResponseEntity<Object> getUser(@PathVariable UUID user_id) throws Exception {
		try{
			User user = userRepository.findById(user_id).get();
			return ResponseEntity.ok(user);
		}catch(java.util.NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
		}
	}

	@GetMapping("/user/{user_id}/transaction")
	public @ResponseBody ResponseEntity<Object> getTransactions(@PathVariable UUID user_id) throws Exception {
		try{
			User user = userRepository.findById(user_id).get();
			Iterable<Transaction> transactions = transactionRepository.findByUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(transactions);
		}catch(java.util.NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
		}
	}

	@PostMapping("/user/{user_id}/transaction")
	public @ResponseBody ResponseEntity<Object> addTransaction(@PathVariable UUID user_id, @RequestBody Transaction transaction) {
		try{
			User user = userRepository.findById(user_id).get();
			transaction.setUser(user);
			return ResponseEntity.ok(transactionRepository.save(transaction));
		}catch(java.util.NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
		}
	}

	@GetMapping("/user/{user_id}/transaction/{transaction_id}")
	public @ResponseBody ResponseEntity<Object> getTransaction(@PathVariable UUID user_id, @PathVariable Integer transaction_id) {
		try{
			User user = userRepository.findById(user_id).get();
			try{
				Transaction transaction = transactionRepository.findById(transaction_id).get();
				if(transaction.getUser().getId().compareTo(user.getId()) != 0) {
					throw new java.util.NoSuchElementException();
				}
				return ResponseEntity.ok(transaction);
			}catch(java.util.NoSuchElementException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transazione non trovata");
			}			
		}catch(java.util.NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
		}	
	}

	@DeleteMapping("/user/{user_id}/transaction/{transaction_id}")
	public @ResponseBody ResponseEntity<Object> deleteTransaction(@PathVariable UUID user_id, @PathVariable Integer transaction_id) {
		try{
			User user = userRepository.findById(user_id).get();
			try{
				Transaction transaction = transactionRepository.findById(transaction_id).get();
				if(transaction.getUser().getId().compareTo(user.getId()) != 0) {
					throw new java.util.NoSuchElementException();
				}
				transactionRepository.delete(transaction);
				return ResponseEntity.noContent().build();
			}catch(java.util.NoSuchElementException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transazione non trovata");
			}			
		}catch(java.util.NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
		}
	}
}
