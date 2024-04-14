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
import ulb.controllers.MenuController;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		this.setupStage(primaryStage);
		new MenuController(primaryStage).loadWelcomeView();
		primaryStage.show();
	}

	private void setupStage(Stage stage) {
		stage.setTitle("NutriSport");
		stage.setWidth(1200);
		stage.setHeight(800);
		stage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
