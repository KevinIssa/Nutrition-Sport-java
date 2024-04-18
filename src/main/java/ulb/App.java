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
import ulb.repositories.JSONProfileRepository;
import ulb.repositories.ProfileRepository;
import ulb.services.ProfileService;
import ulb.services.ProfileServiceImpl;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		this.setupStage(primaryStage);
		ProfileRepository profileRepository = new JSONProfileRepository();
		ProfileService profileService = new ProfileServiceImpl(profileRepository);
		MenuController menuController = new MenuController();
		menuController.setProfileService(profileService);
		menuController.show(primaryStage);
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
