package ulb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/ulb/views/main.fxml"));
		primaryStage.setScene(new Scene(root, 300, 200));
		primaryStage.setTitle("Menu App");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
