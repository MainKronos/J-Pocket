package it.unipi.jpocket.client.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unipi.jpocket.client.Utils;
import it.unipi.jpocket.client.model.TransactionBean;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class TransactionList extends SimpleListProperty<Transaction>{

	private SimpleObjectProperty<Currency> balance = new SimpleObjectProperty<>();
	private SimpleObjectProperty<Currency> income = new SimpleObjectProperty<>();
	private SimpleObjectProperty<Currency> expense = new SimpleObjectProperty<>();

	private final int user_id;

	public TransactionList(int user_id) throws IOException {
		super(FXCollections.observableArrayList());

		this.user_id = user_id;

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

	private void loadData() throws IOException {

		HttpURLConnection con = (HttpURLConnection) new URL("http://localhost:8080/api/user/"+user_id+"/transaction").openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");

		StringBuffer content = new StringBuffer();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
		}

		Gson gson = new Gson();

		TransactionBean[] beans = gson.fromJson(content.toString(), TransactionBean[].class);
		for(TransactionBean t : beans) {
			super.add(new Transaction(t));
		}
	}

	@Override
	public boolean remove(Object elm) {

		if(elm instanceof Transaction) {
			Transaction t = (Transaction) elm;
			try {
				HttpURLConnection con = (HttpURLConnection) new URL("http://localhost:8080/api/user/"+user_id+"/transaction/"+t.getId()).openConnection();
				con.setRequestMethod("DELETE");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				con.getResponseCode();
				con.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return super.remove(elm);
	}

	public boolean add(String title, Currency amount, LocalDate date, InOutType type) {
		
		Map<String, Object> params = new HashMap<>() {{
			put("title", title);
			put("amount", amount.floatValue());
			put("date", Utils.convertToDate(date));
			put("type", type.ordinal());
		}};

		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd").create();

		try {
			HttpURLConnection con = (HttpURLConnection) new URL("http://localhost:8080/api/user/"+user_id+"/transaction").openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			try(OutputStream out = con.getOutputStream()) {
				byte[] input = gson.toJson(params, Map.class).getBytes("utf-8");
				out.write(input, 0, input.length);			
			}

			StringBuffer content = new StringBuffer();
			try(
				BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "utf-8")
				)
			){
				String responseLine = null;
				while ((responseLine = in.readLine()) != null) {
					content.append(responseLine.trim());
				}
			}

			con.disconnect();

			TransactionBean bean = gson.fromJson(content.toString(), TransactionBean.class);

			this.add(new Transaction(bean));		
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
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
