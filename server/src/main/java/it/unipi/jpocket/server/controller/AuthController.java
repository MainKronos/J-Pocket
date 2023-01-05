package it.unipi.jpocket.server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import it.unipi.jpocket.server.model.User;
import it.unipi.jpocket.server.service.UserService;

@Controller
@RequestMapping("/auth") 
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Map<String, String> credentials) throws Exception{

		User user = userService.login(credentials.get("username"), credentials.get("password"));

		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username o password errati");
		}else {
			return ResponseEntity.ok(Map.of("id", user.getId()));
		}
		
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@RequestBody Map<String, String> credentials) throws Exception{
		 
		User user = userService.signup(credentials.get("username"), credentials.get("password"));

		if(user == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username già esistente");
		}else {
			return ResponseEntity.ok(Map.of("id", user.getId()));
		}
	}
}
