package it.unipi.jpocket.client;

import java.time.LocalDate;
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
	public Number getAmount() {
		return amount;
	}
	public Date getDate() {
		return date;
	}
	public InOutType getType() {
		return type;
	}
	
}

class Currency extends Number{
	private final float amount;
	public Currency(float amount) {
		this.amount = amount;
	}
	@Override
	public int intValue() {
		return (int) amount;
	}
	@Override
	public long longValue() {
		return (long) amount;
	}
	@Override
	public float floatValue() {
		return amount;
	}
	@Override
	public double doubleValue() {
		return amount;
	}
	@Override
	public String toString() {
		return String.format("%.2f €", amount);
	}
}

class MyDate extends Date{
	public MyDate(Date date) {
		super(date.getTime());
	}

	@Override
	public String toString() {
		LocalDate localDate = this.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		return String.format("%02d/%02d/%04d", localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}
}