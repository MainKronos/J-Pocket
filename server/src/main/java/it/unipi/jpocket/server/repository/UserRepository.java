package it.unipi.jpocket.server.repository;

import org.springframework.data.repository.CrudRepository;

import it.unipi.jpocket.server.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	User findByUsername(String username);
	
}
