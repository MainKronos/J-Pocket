package it.unipi.jpocket.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

    private static Scene scene;

	public static final Logger LOGGER = Logger.getLogger("DungeonArchitect");
	static {LOGGER.setLevel(Level.FINE);for (Handler handler : Logger.getLogger("").getHandlers()) {handler.setLevel(Level.ALL);}}

    @Override
    public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
		stage.setTitle("J-Pocket");
		stage.getIcons().add(new Image(App.class.getResourceAsStream("assets/coin.png")));
        stage.show();
		stage.sizeToScene();
		stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }

}