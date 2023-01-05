package it.unipi.jpocket.client.transaction;

import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

public class Currency extends Number{

	public static final Currency ZERO = new Currency(0);
	public static final Currency MAX_VALUE = new Currency(Float.MAX_VALUE);

	public static final SpinnerValueFactory<Currency> valueFactory;
	static {
		valueFactory = new SpinnerValueFactory<Currency>() {
			@Override
			public void decrement(int steps) {
				if (getValue() == null) {
					setValue(ZERO);
				} else {
					if (getValue().amount <= 0) {
						setValue(ZERO);
					} else {
						setValue(subtract(getValue(), new Currency(steps * 0.01f)));
					}
				}
			}
			@Override
			public void increment(int steps) {
				if (getValue() == null) {
					setValue(ZERO);
				} else {
					setValue(sum(getValue(), new Currency(steps * 0.01f)));
				}
			}
		};
		valueFactory.setConverter(new StringConverter<Currency>() {
			@Override
			public String toString(Currency object) {
				return object.toString();
			}
			@Override
			public Currency fromString(String string) {
				try {
					return new Currency(Float.parseFloat(string.replace(',', '.').replaceAll("[^0-9\\.]", "")));	
				} catch (NumberFormatException e) {
					return ZERO;
				}			
			}
		});
		valueFactory.setValue(ZERO);
	}

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
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Currency) {
			return ((Currency) obj).amount == this.amount;
		}
		return super.equals(obj);
	}
}
