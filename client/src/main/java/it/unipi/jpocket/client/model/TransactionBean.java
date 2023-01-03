package it.unipi.jpocket.client.model;

import java.util.Date;

public class TransactionBean {
 
	private Long id;
	private String title;
	private Float amount;
	private Date date;
	private Integer type; // 0 = income, 1 = expense

	public TransactionBean(Long id, String title, Float amount, Date date, Integer type) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.date = date;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Float getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public Integer getType() {
		return type;
	}

	
}
