package it.unipi.jpocket.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public @ResponseBody User getUser(@PathVariable Integer user_id) {
		return userRepository.findById(user_id).get();
	}

	@GetMapping("/user/{user_id}/transaction")
	public @ResponseBody Iterable<Transaction> getTransactions(@PathVariable Integer user_id) {
		return transactionRepository.findByUserId(user_id);
	}

	@PostMapping("/user/{user_id}/transaction")
	public @ResponseBody Transaction addTransaction(@PathVariable Integer user_id, @RequestBody Transaction transaction) {
		transaction.setUser(userRepository.findById(user_id).get());
		return transactionRepository.save(transaction);
	}

	@GetMapping("/user/{user_id}/transaction/{transaction_id}")
	public @ResponseBody Transaction getTransaction(@PathVariable Integer user_id, @PathVariable Integer transaction_id) {
		return transactionRepository.findByUserIdAndId(user_id, transaction_id);
		
	}

	@DeleteMapping("/user/{user_id}/transaction/{transaction_id}")
	public @ResponseBody void deleteTransaction(@PathVariable Integer user_id, @PathVariable Integer transaction_id) {
		transactionRepository.delete(transactionRepository.findByUserIdAndId(user_id, transaction_id));
	}
}
