package it.unipi.jpocket.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipi.jpocket.server.model.User;
import it.unipi.jpocket.server.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User login(String username, String password) {
		User finded = userRepository.findByUsername(username);
		if(finded == null ||!finded.getPassword().equals(password)) {
			return null;
		}else {
			return finded;
		}
	}

	public User signup(String username, String password) {
		User finded = userRepository.findByUsername(username);
		if(finded != null) {
			return null;
		}else {
			User user = new User(username, password);
			userRepository.save(user);
			return user;
		}
	}
}
