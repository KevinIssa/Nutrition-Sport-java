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
import ulb.repositories.*;
import ulb.services.CaloriesTrackingService;
import ulb.services.ProfileService;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		this.setupStage(primaryStage);

		MenuController menuController = this.setupController();
		menuController.show(primaryStage);
		primaryStage.show();
	}

	private MenuController setupController() {
		ProfileRepository profileRepository = new JSONProfileRepository();
		ActivityRepository activityRepository = new JSONActivityRepository();
		ConsumeMealRepository consumeMealRepository = new JSONConsumeMealRepository();

		ProfileService profileService = new ProfileService(profileRepository);
		CaloriesTrackingService caloriesTrackingService =
				new CaloriesTrackingService(activityRepository, consumeMealRepository);

		MenuController menuController = new MenuController();
		menuController.setCaloriesTrackingService(caloriesTrackingService);
		menuController.setProfileService(profileService);
		return menuController;
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
