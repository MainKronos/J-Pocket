package it.unipi.jpocket.client.transaction;

public class Currency extends Number{

	public static final Currency ZERO = new Currency(0);

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

	public static Currency sum(Currency a, Currency b) {
		return new Currency(a.amount + b.amount);
	}

	public static Currency subtract(Currency a, Currency b) {
		return new Currency(a.amount - b.amount);
	}

	public Currency negate() {
		return new Currency(-this.amount);
	}
}
