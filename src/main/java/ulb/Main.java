package ulb;

import javafx.application.Application;
import javafx.stage.Stage;
import ulb.controllers.SwitchController;
import ulb.models.Model;

import java.io.IOException;


public class Main extends Application {
	Model model;
	@Override
	public void start(Stage primaryStage) throws IOException {
		SwitchController controller = new SwitchController(primaryStage);
		this.model = new Model(controller);
		// switch to the main menu
		controller.switchFXML("/ulb/views/main.fxml", model);
		// switch to profile creation menu if profile not created else load the profile
		this.model.createProfile();
		primaryStage.setTitle("Menu App");
		primaryStage.setWidth(800); // Initial width
		primaryStage.setHeight(600); // Initial height
		// Allow the user to resize the window
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
