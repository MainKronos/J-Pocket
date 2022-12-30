package it.unipi.jpocket.client.transaction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class Transaction {

	private final String title;
	private final Currency amount;
	private final LocalDate date;
	private final InOutType type;

	private static final Random rnd = new Random();
	
	public Transaction(String title, float amount, LocalDate date, InOutType type) {
		this.title = title;
		this.amount = new Currency(amount);
		this.date = date;
		this.type = type;
	}
	public Transaction(String title, Currency amount, LocalDate date, InOutType type) {
		this(title, amount.floatValue(), date, type);
	}

	// Solo in fase di test
	public Transaction(){
		this(
			"Title " + rnd.nextInt(100), 
			rnd.nextFloat() * 1000, 
			LocalDateTime.ofInstant(Instant.ofEpochMilli(rnd.nextLong() % (new Date().getTime() - 1000000000) + 1000000000), ZoneId.systemDefault()).toLocalDate(),
			rnd.nextBoolean() ? InOutType.INCOME : InOutType.EXPENSE);	
	}

	public String getTitle() {
		return title;
	}
	public Currency getAmount() {
		return amount;
	}
	public Currency getSignedAmount() {
		return type == InOutType.INCOME ? amount : amount.negate();
	}
	public LocalDate getDate() {
		return date;
	}
	public InOutType getType() {
		return type;
	}
	
}