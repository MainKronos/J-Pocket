package it.unipi.jpocket.client;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.UUID;

/**
 * JavaFX App
 */
public class App extends Application {

	public static final StringProperty user_name = new SimpleStringProperty("user"); // default value
	public static final ObjectProperty<UUID> user_id = new SimpleObjectProperty<UUID>(UUID.fromString("31000000-0000-0000-0000-000000000000")); // default value
	private static final DoubleProperty minWidth = new SimpleDoubleProperty(500); // default value
	private static final DoubleProperty minHeight = new SimpleDoubleProperty(500); // default value

	private static Scene scene;

    @Override
    public void start(Stage stage) {

		scene = new Scene(loadFXML("login"));
		
        stage.setScene(scene);
		stage.setTitle("J-Pocket");
		stage.getIcons().add(new Image(App.class.getResourceAsStream("assets/coin.png")));
		stage.sizeToScene();
		stage.minWidthProperty().bind(minWidth);
		stage.minHeightProperty().bind(minHeight);
        stage.show();
		setMinSize(stage.getWidth(), stage.getHeight());
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

	/**
	 * Cambia la scena in quella principale
	 */
	public static void switchToPrimary() {
		scene.setRoot(loadFXML("primary"));
		scene.getWindow().sizeToScene();
		setMinSize(scene.getWindow().getWidth(), scene.getWindow().getHeight());
	}

	private static void setMinSize(double width, double height) {
		minWidth.set(width);
		minHeight.set(height);	
	}
		

    public static void main(String[] args) {
        launch();
    }

}