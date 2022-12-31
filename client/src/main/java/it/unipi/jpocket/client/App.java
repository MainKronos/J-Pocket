package it.unipi.jpocket.client;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

	public static final StringProperty user = new SimpleStringProperty();

    private static Scene scene;

	public static final Logger LOGGER = Logger.getLogger("J-Pocket");
	static {LOGGER.setLevel(Level.FINE);for (Handler handler : Logger.getLogger("").getHandlers()) {handler.setLevel(Level.ALL);}}

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
		stage.setTitle("J-Pocket");
		stage.getIcons().add(new Image(App.class.getResourceAsStream("assets/coin.png")));
        stage.setScene(scene);
		stage.sizeToScene();
        stage.show();
		// stage.setMinWidth(stage.getWidth());
        // stage.setMinHeight(stage.getHeight());
    }

	private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

	public static void loadScene(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
		scene.getWindow().sizeToScene();
	}
		

    public static void main(String[] args) {
        launch();
    }

}