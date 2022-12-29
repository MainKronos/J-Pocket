package it.unipi.jpocket.client.transaction;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class TransactionList extends SimpleListProperty<Transaction>{

	private SimpleObjectProperty<Currency> balance = new SimpleObjectProperty<>();
	private SimpleObjectProperty<Currency> income = new SimpleObjectProperty<>();
	private SimpleObjectProperty<Currency> expense = new SimpleObjectProperty<>();

	public TransactionList() {
		super(FXCollections.observableArrayList());

		income.bind(Bindings.createObjectBinding(
			() -> this
			.stream()
			.parallel()
			.filter((e) -> e.getType() == InOutType.INCOME)
			.map((e) -> e.getAmount())
			.reduce(Currency.ZERO, Currency::sum),
			this
		));

		expense.bind(Bindings.createObjectBinding(
			() -> this
			.stream()
			.parallel()
			.filter((e) -> e.getType() == InOutType.EXPENSE)
			.map((e) -> e.getAmount())
			.reduce(Currency.ZERO, Currency::sum),
			this
		));

		balance.bind(Bindings.createObjectBinding(
			() -> this
			.stream()
			.parallel()
			.map((e) -> e.getSignedAmount())
			.reduce(Currency.ZERO, Currency::sum),
			this
		));
		loadData();
	}

	private void loadData() {
		for(int i=0; i<4; i++)
			this.add(new Transaction());
	}

	@Override
	public boolean remove(Object elm) {
		return super.remove(elm);
	}

	public SimpleObjectProperty<Currency> balanceProperty() {
		return balance;
	}

	public SimpleObjectProperty<Currency> incomProperty() {
		return income;
	}

	public SimpleObjectProperty<Currency> expenseProperty() {
		return expense;
	}
	
}
