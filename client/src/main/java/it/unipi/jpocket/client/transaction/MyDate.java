package it.unipi.jpocket.client.transaction;

import java.time.LocalDate;
import java.util.Date;

public class MyDate extends Date{
	public MyDate(Date date) {
		super(date.getTime());
	}

	@Override
	public String toString() {
		LocalDate localDate = this.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		return String.format("%02d/%02d/%04d", localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}
}
