package it.unipi.jpocket.client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

	public static final Logger LOGGER = Logger.getLogger("J-Pocket");
	static {LOGGER.setLevel(Level.FINE);for (Handler handler : Logger.getLogger("").getHandlers()) {handler.setLevel(Level.ALL);}}

	public static LocalDate convertToLocalDate(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date convertToDate(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
