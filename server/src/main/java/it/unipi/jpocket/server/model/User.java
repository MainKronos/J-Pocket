package it.unipi.jpocket.server.model;

import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;


@Entity
@Table(name="user", schema="user")
public class User{

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="username", nullable = false, unique = true)
	private String username;

	@Column(name="password", nullable = false)
	private String password;

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	@JsonIncludeProperties("id")
	private List<Transaction> transactions;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(){
		Random rnd = new Random();
		this.username = "User " + rnd.nextInt(1000);
		this.password = "Password " + rnd.nextInt(1000);
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void addTransactions(Transaction transactions) {
		this.transactions.add(transactions);
		if(transactions.getUser() != this) {
			transactions.setUser(this);
		}
	}
}
