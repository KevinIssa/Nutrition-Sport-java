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
import ulb.dtos.RecipeDTO;
import ulb.services.RecipeService;
import ulb.views.MealRecipeViewController;

public class MealRecipeController extends AppController
		implements MealRecipeViewController.Listener, EditMealController.Listener {
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
	public List<RecipeDTO> loadRecipes() {
		logger.info("Loading all recipe");
		return this.recipeService.loadAllRecipes();
	}

	@Override
	public void editMeal(RecipeDTO meal) {
		logger.info("Editing a recipe");
		this.listener.editMeal(meal);
	}

	@Override
	public void deleteMeal(RecipeDTO meal) {
		logger.info("Deleting a recipe");
		this.recipeService.deleteMeal(meal);
	}

	@Override
	public void checkMeal(RecipeDTO meal) {
		logger.info("Looking at the content of a recipe");
		this.listener.mealDetails(meal);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {

		void mealDetails(RecipeDTO meal);

		void editMeal(RecipeDTO meal);

		void returnHome();
	}
}
