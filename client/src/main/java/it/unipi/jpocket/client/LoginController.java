package it.unipi.jpocket.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class LoginController implements Initializable{

	@FXML private TextField usernameInput;
	@FXML private PasswordField passwordInput;

	@FXML private HBox loadingIndicator;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		App.LOGGER.info("Start");

	}

	@FXML
	public void login() throws InterruptedException, IOException {
		App.LOGGER.info("Login");
		loadingIndicator.setVisible(true);


		new Thread(new Task<Void>() {	
			@Override
			protected Void call() throws Exception {
				Thread.sleep(1000);
				App.user.set(usernameInput.getText());
				App.loadScene("primary");
				return null;
			}
		}).start();
		
		
	}

	@FXML
	public void singup() throws InterruptedException, IOException {
		App.LOGGER.info("Register");
		loadingIndicator.setVisible(true);

		new Thread(new Task<Void>() {	
			@Override
			protected Void call() throws Exception {
				Thread.sleep(1000);
				App.user.set(usernameInput.getText());
				App.loadScene("primary");
				return null;
			}
		}).start();
	}
	
}
