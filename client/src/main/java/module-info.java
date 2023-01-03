module it.unipi.jpocket.client {
    requires javafx.controls;
    requires javafx.fxml;

	requires transitive javafx.graphics;
	requires transitive java.logging;

	requires com.google.gson;

	opens it.unipi.jpocket.client.model to com.google.gson;

    opens it.unipi.jpocket.client to javafx.fxml;
    opens it.unipi.jpocket.client.transaction to javafx.base;
    exports it.unipi.jpocket.client;
}
