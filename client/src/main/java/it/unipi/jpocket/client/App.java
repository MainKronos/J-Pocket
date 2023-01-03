package it.unipi.jpocket.client;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

	public static final StringProperty user_name = new SimpleStringProperty();
	public static final IntegerProperty user_id = new SimpleIntegerProperty();

	private static Scene scene;

    @Override
    public void start(Stage stage) {

		scene = new Scene(loadFXML("login"));
		
        stage.setScene(scene);
		stage.setTitle("J-Pocket");
		stage.getIcons().add(new Image(App.class.getResourceAsStream("assets/coin.png")));
		stage.sizeToScene();
        stage.show();
		stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

	private static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		Parent ret = null;
        try {
			ret = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
    }

	public static void switchToPrimary() {
		scene.setRoot(loadFXML("primary"));
	}
		

    public static void main(String[] args) {
        launch();
    }

}