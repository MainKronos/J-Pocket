package it.unipi.jpocket.client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import it.unipi.jpocket.client.transaction.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class PrimaryController implements Initializable{

	@FXML private Text userTxt;
	@FXML private Text balanceTxt;
	@FXML private Text incomeTxt;
	@FXML private Text expenseTxt;

	@FXML private VBox modal;
	@FXML private TextField titleInput;
	@FXML private Spinner<Currency> amountInput;
	@FXML private DatePicker dateInput;
	@FXML private ChoiceBox<InOutType> typeInput;

	@FXML private TableView<Transaction> TransactionTable;
	@FXML private TableColumn<Transaction,String> TitleCol;
	@FXML private TableColumn<Transaction,Float> AmountCol;
	@FXML private TableColumn<Transaction,Date> DateCol;
	@FXML private TableColumn<Transaction,InOutType> TypeCol;

	private final TransactionList data = new TransactionList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		App.LOGGER.info("Start");

		userTxt.setText("MainKronos");

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

		balanceTxt.textProperty().bind(data.balanceProperty().asString());
		incomeTxt.textProperty().bind(data.incomProperty().asString());
		expenseTxt.textProperty().bind(data.expenseProperty().asString());

		amountInput.setValueFactory(Currency.valueFactory);
		typeInput.getItems().addAll(InOutType.values());

	}

	@FXML
	public void removeItem() {
		data.remove(TransactionTable.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void openModal(){
		modal.setVisible(true);
	}

	@FXML
	public void closeModal(){
		modal.setVisible(false);
		titleInput.clear();
		amountInput.getValueFactory().setValue(Currency.ZERO);
		dateInput.setValue(null);
		typeInput.setValue(null);
	}

	@FXML
	public void addItem() {

		if(titleInput.getText().isEmpty() || amountInput.getValue().equals(Currency.ZERO) || dateInput.getValue() == null || typeInput.getValue() == null)
			return;

		data.add(new Transaction(
			titleInput.getText(),
			amountInput.getValue(),
			dateInput.getValue(),
			typeInput.getValue()
		));

		closeModal();
	}
}
