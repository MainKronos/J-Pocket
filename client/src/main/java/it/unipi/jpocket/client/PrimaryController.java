package it.unipi.jpocket.client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import it.unipi.jpocket.client.transaction.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable{

	@FXML private Text txt_user;
	@FXML private Text txt_balance;
	@FXML private Text txt_income;
	@FXML private Text txt_expense;
	@FXML private TableView<Transaction> TransactionTable;
	@FXML private TableColumn<Transaction,String> TitleCol;
	@FXML private TableColumn<Transaction,Float> AmountCol;
	@FXML private TableColumn<Transaction,Date> DateCol;
	@FXML private TableColumn<Transaction,InOutType> TypeCol;

	private final TransactionList data = new TransactionList();

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

		txt_balance.textProperty().bind(data.balanceProperty().asString());
		txt_income.textProperty().bind(data.incomProperty().asString());
		txt_expense.textProperty().bind(data.expenseProperty().asString());

	}

	@FXML
	public void removeItem() {
		data.remove(TransactionTable.getSelectionModel().getSelectedItem());
	}
}
