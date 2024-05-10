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
import ulb.dtos.MealDTO;
import ulb.models.Meal;
import ulb.services.ConsumeMealService;
import ulb.services.RecipeService;
import ulb.views.MakeMealViewController;
import ulb.views.MealRecipeViewController;

public class MealRecipeController extends AppController
		implements MealRecipeViewController.Listener, MakeMealViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MealRecipeController.class);

	private final RecipeService recipeService;

	private final MealRecipeController.Listener listener;

	public MealRecipeController(
			RecipeService recipeService, MealRecipeController.Listener listener) {
		logger.info("Initializing MealRecipeController");
		this.recipeService = recipeService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing MealRecipeView");
		this.loadView("/ulb/views/MealRecipe.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public List<MealDTO> loadRecipes() {
		logger.info("Loading all recipe");
		return this.recipeService.loadAllRecipes();
	}

	@Override
	public void editMeal(MealDTO meal) {
		logger.info("Editing a recipe");
		//TODO: Implement editMeal
	}

	@Override
	public void deleteMeal(MealDTO meal) {
		logger.info("Deleting a recipe");
		this.recipeService.delete(meal);
	}

	@Override
	public void checkMeal(MealDTO meal) {
		logger.info("Looking at the content of a recipe");
		this.listener.mealDetails(meal);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {

		void mealDetails(MealDTO meal);

		void returnHome();
	}
}
