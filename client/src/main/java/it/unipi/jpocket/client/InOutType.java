package it.unipi.jpocket.client;

public enum InOutType {
	INCOME, EXPENSE;

	public String toString() {
		switch (this) {
		case INCOME:
			return "Income";
		case EXPENSE:
			return "Expense";
		default:
			return "Unknown";
		}
	}
}
