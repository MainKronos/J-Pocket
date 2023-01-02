package it.unipi.jpocket.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.*;

@Entity
@Table(name="transaction", schema="transaction")
public class Transaction{

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;

	@Column(name="title")
	private String title;

	@Column(name="amount")
	private Float amount;

	@Column(name="date")
	private Date date;

	@Column(name="type")
	private Integer type; // 0 = income, 1 = expense

	@ManyToOne(optional=false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIncludeProperties("id")
	private User user;
	
	public Transaction(String title, Float amount, Date date, Integer type) {
		this.title = title;
		this.amount = amount;
		this.date = date;
		this.type = type;
	}

	public Transaction() {
		Random rand = new Random();
		this.title = "Transaction " + rand.nextInt(1000);
		this.amount = rand.nextFloat() * 1000;
		this.date = new Date(rand.nextInt(2000000000));
		this.type = rand.nextInt(2);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if(!user.getTransactions().contains(this)) {
			user.getTransactions().add(this);
		}
	}
}
