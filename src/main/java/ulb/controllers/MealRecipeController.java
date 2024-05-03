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

import java.util.List;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.Meal;
import ulb.services.ConsumeMealService;
import ulb.views.MealRecipeViewController;

public class MealRecipeController extends AppController
		implements MealRecipeViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MealRecipeController.class);

	private final ConsumeMealService consumeMealService;

	private final MealRecipeController.Listener listener;

	public MealRecipeController(
			ConsumeMealService consumeMealService, MealRecipeController.Listener listener) {
		logger.info("Initializing MealRecipeController");
		this.consumeMealService = consumeMealService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing MealRecipeView");
		this.loadView("/ulb/views/MealRecipe.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public List<Meal> loadRecipes() {
		logger.info("Loading all recipe");
		return Meal.loadAll();
	}

	@Override
	public void editMeal(Meal meal) {
		logger.info("Editing a recipe");
		this.listener.editMeal(meal);
	}

	@Override
	public void deleteMeal(Meal meal) {
		logger.info("Deleting a recipe");
		meal.delete();
	}

	@Override
	public void checkMeal(Meal meal) {
		logger.info("Looking at the content of a recipe");
		this.listener.mealDetails(meal);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		void editMeal(Meal meal);

		void mealDetails(Meal meal);

		void returnHome();
	}
}
