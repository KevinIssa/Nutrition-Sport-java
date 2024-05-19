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
import ulb.services.ActivityService;
import ulb.services.CaloriesTrackingService;
import ulb.services.ConsumableService;
import ulb.services.ConsumeMealService;
import ulb.services.ProfileService;
import ulb.services.RecipeService;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		this.setupStage(primaryStage);
		MenuController menuController = new MenuController();
		this.setupController(menuController);
		menuController.show(primaryStage);
		primaryStage.show();
	}

	private void setupController(MenuController menuController) {
		ProfileRepository profileRepository = new JSONProfileRepository();
		ActivityRepository activityRepository = new JSONActivityRepository();
		ConsumeMealRepository consumeMealRepository = new JSONConsumeMealRepository();
		ConsumableRepository consumableRepository = new JSONConsumableRepository();
		RecipeRepository recipeRepository = new JSONRecipeRepository();

		ProfileService profileService = 
				new ProfileService(profileRepository);
		ActivityService activityService = 
				new ActivityService(activityRepository);
		CaloriesTrackingService caloriesTrackingService =
				new CaloriesTrackingService(activityRepository, consumeMealRepository);
		ConsumableService consumableService = 
				new ConsumableService(consumableRepository);
		ConsumeMealService consumeMealService = 
				new ConsumeMealService(consumeMealRepository, consumableRepository);
		RecipeService recipeService = 
				new RecipeService(recipeRepository, consumableRepository);

		menuController.setProfileService(profileService);
		menuController.setActivityService(activityService);
		menuController.setCaloriesTrackingService(caloriesTrackingService);
		menuController.setConsumableService(consumableService);
		menuController.setConsumeMealService(consumeMealService);
		menuController.setRecipeService(recipeService);
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
