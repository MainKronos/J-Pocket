package it.unipi.jpocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class LoginController implements Initializable{

	@FXML private TextField usernameInput;
	@FXML private PasswordField passwordInput;

	@FXML private Text errorTxt;

	@FXML private HBox loadingIndicator;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Utils.LOGGER.info("Start");

	}

	@FXML
	public void login() throws InterruptedException, IOException {
		Utils.LOGGER.info("Login");
		loadingIndicator.setVisible(true);


		new Thread(new Task<Void>() {	
			@Override
			protected Void call() throws Exception {
				connect(new URL("http://localhost:8080/auth/login"));
				return null;
			}
		}).start();
		
		
	}

	@FXML
	public void singup() throws InterruptedException, IOException {
		Utils.LOGGER.info("Register");
		loadingIndicator.setVisible(true);

		new Thread(new Task<Void>() {	
			@Override
			protected Void call() throws Exception {
				connect(new URL("http://localhost:8080/auth/signup"));
				return null;
			}
		}).start();
	}
	

	private boolean connect(URL url){
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setDoInput(true);


			Gson gson = new Gson();
			Map<String, String> inputMap = new HashMap<String, String>() {{
				put("username", usernameInput.getText());
				put("password", passwordInput.getText());
			}};

			try(OutputStream out = con.getOutputStream()) {
				byte[] input = gson.toJson(inputMap, Map.class).getBytes("utf-8");
				out.write(input, 0, input.length);			
			}
			
			StringBuffer content = new StringBuffer();
			try(
				BufferedReader in = new BufferedReader(
					new InputStreamReader(
						con.getResponseCode() != 200 ? con.getErrorStream() : con.getInputStream(),
						"utf-8"
					)
				)
			){
				String responseLine = null;
				while ((responseLine = in.readLine()) != null) {
					content.append(responseLine.trim());
				}
			}

			if(con.getResponseCode() != 200) {
				con.disconnect();
				throw new IOException(content.toString());
			}

			con.disconnect();

			App.user_name.set(usernameInput.getText());
			App.user_id.set(((Double)gson.fromJson(content.toString(), Map.class).get("id")).intValue());
			
		} catch (IOException e) {
			
			errorTxt.setText(e.getMessage());
			loadingIndicator.setVisible(false);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		App.switchToPrimary();

		return true;
		
	}
}
