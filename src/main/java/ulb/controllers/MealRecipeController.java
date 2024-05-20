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
import ulb.services.ConsumableService;
import ulb.views.MealRecipeViewController;

/**
 * This class is a controller for the MealRecipe view.
 * It extends the AppController class and implements the Listener interface from the MealRecipeViewController.
 */
public class MealRecipeController extends AppController
		implements MealRecipeViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MealRecipeController.class);
	private final ConsumableService consumableService;
	private final MealRecipeController.Listener listener;

	/**
	 * Constructor for the MealRecipeController class.
	 * It initializes the ConsumableService and Listener instances.
	 *
	 * @param consumableService The ConsumableService instance to be used for consumable related operations.
	 * @param listener The Listener instance to be used for handling events triggered in the MealRecipe view.
	 */
	public MealRecipeController(
			ConsumableService consumableService, MealRecipeController.Listener listener) {
		logger.info("Initializing MealRecipeController");
		this.consumableService = consumableService;
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
		return this.consumableService.loadAllRecipes();
	}

	@Override
	public void editMeal(RecipeDTO meal) {
		logger.info("Editing a recipe");
		this.listener.editMeal(meal);
	}

	@Override
	public void deleteMeal(RecipeDTO meal) {
		logger.info("Deleting a recipe");
		this.consumableService.deleteMeal(meal);
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

	/**
	 * This is an interface for a listener that reacts to meal related events.
	 * The events are triggered when a meal is checked, edited or when the user decides to return to the home screen.
	 */
	public interface Listener {

		/**
		 * This method is called when a meal details event occurs.
		 *
		 * @param meal The meal that was selected.
		 */
		void mealDetails(RecipeDTO meal);

		/**
		 * This method is called when a meal edit event occurs.
		 *
		 * @param meal The meal that is to be edited.
		 */
		void editMeal(RecipeDTO meal);

		/**
		 * This method is called when a return home event occurs.
		 */
		void returnHome();
	}
}
