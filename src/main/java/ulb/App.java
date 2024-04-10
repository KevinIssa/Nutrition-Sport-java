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

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		setupStage(primaryStage);
		new MainAppController(primaryStage).loadWelcomeView();
		primaryStage.show();
	}

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
