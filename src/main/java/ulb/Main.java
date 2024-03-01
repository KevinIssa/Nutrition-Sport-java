package ulb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ulb.controllers.SwitchController;
import ulb.models.Modele;

import java.io.IOException;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		SwitchController controller = new SwitchController(primaryStage);
		Modele modele = new Modele(controller);
		controller.switchFXML("/ulb/views/main.fxml", modele);
		primaryStage.setTitle("Menu App");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
