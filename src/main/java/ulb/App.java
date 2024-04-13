/*
 * Ce projet est une application de santé et de bien-être développée dans le cadre du cours INFO-F-307 à l'ULB.
 *
 * Groupe : 06
 * Étudiants :
 * - Kevin ISSA
 * - Hamza CAEYMAN
 * - Alexandru MELNIC
 * - Ze-Xuan XU
 * - Bao TRAN
 * - Hà Uyên TRAN
 * - Hugo CHARELS
 * - Hodo SOULEIMAN AHMED
 * - Kevin VANDERVAEREN
 * - Arthur INSTALLÉ
 *
 * Date : 2024
 */
package ulb;

import javafx.application.Application;
import javafx.stage.Stage;
import ulb.controllers.MainAppController;

/**
 * The ViewLoader class is responsible for loading different views in the application.
 * It uses the JavaFX FXMLLoader to load the FXML files associated with each view.
 * It also sets the listener for each view controller.
 * The paths to the FXML files are stored in a string array.
 */
public class App extends Application {

	/**
	 * The start method is the main entry point for all JavaFX applications.
	 * It is called after the init method has returned, and after the system is ready for the application to begin running.
	 * It sets up the primary stage and loads the welcome view.
	 *
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {
		setupStage(primaryStage);
		new MainAppController(primaryStage).loadWelcomeView();
		primaryStage.show();
	}

	/**
	 * The setupStage method is used to set up the primary stage of the application.
	 * It sets the title, size, and resizability of the stage.
	 *
	 * @param stage the primary stage for this application.
	 */
	private void setupStage(Stage stage) {
		stage.setTitle("Fitness App");
		stage.setResizable(true);
		stage.setWidth(1200);
		stage.setHeight(800);
		stage.setMinWidth(1200);
		stage.setMaxWidth(1200);
		stage.setMinHeight(800);
		stage.setMaxHeight(800);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
