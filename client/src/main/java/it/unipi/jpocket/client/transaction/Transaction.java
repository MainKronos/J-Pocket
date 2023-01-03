package it.unipi.jpocket.client.transaction;

import java.time.LocalDate;
import it.unipi.jpocket.client.Utils;
import it.unipi.jpocket.client.model.TransactionBean;

public class Transaction {

	private final Integer id;
	private final String title;
	private final Currency amount;
	private final LocalDate date;
	private final InOutType type;
	

	public Transaction(TransactionBean bean) {
		this.id = bean.getId().intValue();
		this.title = bean.getTitle();
		this.amount = new Currency(bean.getAmount());
		this.date = Utils.convertToLocalDate(bean.getDate());
		this.type = bean.getType() == 0 ? InOutType.INCOME : InOutType.EXPENSE;
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
	public Integer getId() {
		return id;
	}
}