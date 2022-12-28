package it.unipi.jpocket.client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable{

	@FXML public Text txt_user;
	@FXML public Text txt_balance;
	@FXML public Text txt_income;
	@FXML public Text txt_expense;
	@FXML public TableView<Transaction> TransactionTable;
	@FXML public TableColumn<Transaction,String> TitleCol;
	@FXML public TableColumn<Transaction,Float> AmountCol;
	@FXML public TableColumn<Transaction,Date> DateCol;
	@FXML public TableColumn<Transaction,InOutType> TypeCol;

	public final ObservableList<Transaction> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		App.LOGGER.info("Start");

		txt_user.setText("MainKronos");

		TitleCol.prefWidthProperty().bind(TransactionTable.widthProperty().multiply(1/3f));
		AmountCol.prefWidthProperty().bind(TransactionTable.widthProperty().multiply(1/8f));
		DateCol.prefWidthProperty().bind(TransactionTable.widthProperty().multiply(1/3f));
		TypeCol.prefWidthProperty().bind(TransactionTable.widthProperty().multiply(1/8f));

		TitleCol.setReorderable(false);
		AmountCol.setReorderable(false);
		DateCol.setReorderable(false);
		TypeCol.setReorderable(false);

		TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		TransactionTable.setItems(data);

		txt_balance.textProperty().bind(
			Bindings.format("%.2f", 
				Bindings.createFloatBinding(
					() -> data
					.stream()
					.parallel()
					.reduce(0f, (acc, t) -> acc + t.getAmount().floatValue() * (t.getType() == InOutType.INCOME ? 1 : -1), Float::sum),
					data
				)
			)
		);

		txt_income.textProperty().bind(
			Bindings.format("%.2f", 
				Bindings.createFloatBinding(
					() -> data
					.stream()
					.parallel()
					.filter(t -> t.getType() == InOutType.INCOME)
					.reduce(0f, (acc, t) -> acc + t.getAmount().floatValue(), Float::sum),
					data
				)
			)
		);

		txt_expense.textProperty().bind(
			Bindings.format("%.2f", 
				Bindings.createFloatBinding(
					() -> data
					.stream()
					.parallel()
					.filter(t -> t.getType() == InOutType.EXPENSE)
					.reduce(0f, (acc, t) -> acc + t.getAmount().floatValue(), Float::sum),
					data
				)
			)
		);

		for(int i=0; i<4; i++)
			data.add(new Transaction());

		/* data.addAll(
			new Transaction("Cena", 20.00f, new Date(), InOutType.EXPENSE),
			new Transaction("Vacanza", 120.00f, new Date(), InOutType.EXPENSE),
			new Transaction("Stipendio", 1000.00f, new Date(), InOutType.INCOME)
		); */

	}
}
