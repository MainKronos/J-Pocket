package it.unipi.jpocket.client.transaction;

import java.util.Date;
import java.util.Random;

public class Transaction {

	private final String title;
	private final Currency amount;
	private final MyDate date;
	private final InOutType type;

	private static final Random rnd = new Random();
	
	public Transaction(String title, float amount, Date date, InOutType type) {
		this.title = title;
		this.amount = new Currency(amount);
		this.date = new MyDate(date);
		this.type = type;
	}

	// Solo in fase di test
	public Transaction(){
		this(
			"Title " + rnd.nextInt(100), 
			rnd.nextFloat() * 1000, 
			new Date(rnd.nextLong() % (new Date().getTime() - 1000000000) + 1000000000),
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
	public Date getDate() {
		return date;
	}
	public InOutType getType() {
		return type;
	}
	
}