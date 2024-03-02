package ulb;

import javafx.application.Application;
import javafx.stage.Stage;
import ulb.controllers.SwitchController;
import ulb.models.Modele;

import java.io.IOException;

public class Main extends Application {
	Modele modele;
	@Override
	public void start(Stage primaryStage) throws IOException {
		SwitchController controller = new SwitchController(primaryStage);
		this.modele = new Modele(controller);
		controller.switchFXML("/ulb/views/main.fxml", modele);
		this.modele.createProfil();// todo need to add controller.switchFXML(path of the profil creation , modele)
		primaryStage.setTitle("Menu App");
		primaryStage.show();
	}
	@Override
	public void stop() throws Exception {
		// Code to execute when the JavaFX application is closing
		savingUser();
	}
	public void savingUser() throws IOException {
		// saving the user in a file called user.text in resources.ulb.database
		this.modele.saveUser();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
