package it.unipi.jpocket.client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import it.unipi.jpocket.client.transaction.*;
import javafx.beans.property.ObjectProperty;
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

		SpinnerValueFactory<Currency> valueFactory = new SpinnerValueFactory<Currency>() {
			@Override
			public void decrement(int val) {
				setValue(Currency.subtract(getValue(), new Currency(val*0.01f)));
			}
			@Override
			public void increment(int val) {
				setValue(Currency.sum(getValue(), new Currency(val*0.01f)));
			}
		};
		valueFactory.setValue(Currency.ZERO);
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
					return Currency.ZERO;
				}			
			}
		});

		amountInput.setValueFactory(valueFactory);

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

		data.add(new Transaction("Test", 10, new Date(), InOutType.INCOME));
	}
}
