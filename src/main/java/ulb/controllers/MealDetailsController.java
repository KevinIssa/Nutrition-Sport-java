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
package ulb.controllers;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.RecipeDTO;
import ulb.services.ConsumableService;
import ulb.views.MealDetailsViewController;

public class MealDetailsController extends AppController
		implements MealDetailsViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MealDetailsController.class);
	private final ConsumableService consumableService;
	private final MealDetailsController.Listener listener;

	public MealDetailsController(
			ConsumableService consumableService, MealDetailsController.Listener listener) {
		logger.info("Initializing MealDetailsController");
		this.consumableService = consumableService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing MealDetailsView");
		this.loadView("/ulb/views/MealDetails.fxml", stage);
		this.viewController.setListener(this);
	}

	public void setMeal(RecipeDTO recipeDTO) {
		((MealDetailsViewController) this.viewController).setMeal(recipeDTO);
	}

	@Override
	public double getCaloriesConsumed(RecipeDTO recipeDTO) {
		return this.consumableService.getCaloriesConsumed(recipeDTO);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {

		void returnHome();
	}
}
