package it.unipi.jpocket.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/transactions")
	public @ResponseBody Iterable<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	@GetMapping("/transactions/{id}")
	public @ResponseBody Transaction getTransaction(Integer id) {
		return transactionRepository.findById(id).get();
	}

	@DeleteMapping("/transactions")
	public @ResponseBody void deleteTransactions() {
		transactionRepository.deleteAll();
	}

	@DeleteMapping("/transactions/{id}")
	public @ResponseBody void deleteTransaction(Integer id) {
		transactionRepository.deleteById(id);
	}

	@GetMapping("/users")
	public @ResponseBody Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/user/{id}/transactions")
	public @ResponseBody Iterable<Transaction> getUserTransactions(Integer id) {
		return transactionRepository.findByUserId(id);
	}
}
