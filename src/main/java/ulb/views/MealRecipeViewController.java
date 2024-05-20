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
package ulb.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.RecipeDTO;
import ulb.widgets.MealBox;

/**
 * Controller de la vue MealRecipeView.
 */
public class MealRecipeViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(MealRecipeViewController.class);

	@FXML private ListView<MealBox> mealList;

	private MealRecipeViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		logger.info("Initializing MealRecipeViewController");
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealRecipeViewController.Listener) listener;
		this.showRecipes();
	}

	/**
	 * Creates a MealBox object for the given meal.
	 * This method creates a new MealBox object with the given meal and three buttons for checking, editing, and deleting the meal.
	 * It also sets the actions for the buttons to call the appropriate methods when clicked.
	 *
	 * @param meal The RecipeDTO object that represents the meal for which the MealBox is to be created.
	 * @return A MealBox object for the given meal.
	 * @throws IOException If an error occurs while creating the MealBox.
	 */
	private MealBox makeMealBox(RecipeDTO meal) throws IOException {
		Button deleteButton = new Button("");
		Button editButton = new Button("");
		Button checkButton = new Button("");

		MealBox mealBox = new MealBox(meal, checkButton, deleteButton, editButton);

		checkButton.setOnAction(e -> this.checkMeal(mealBox));
		editButton.setOnAction(e -> this.editMeal(mealBox));
		deleteButton.setOnAction(e -> this.deleteMeal(mealBox));
		return mealBox;
	}

	/**
	 * Shows the recipes in the meal list.
	 * This method clears the current meal list, loads the recipes from the listener, and adds a MealBox for each recipe to the meal list.
	 * If an error occurs while creating a MealBox, it logs the error, shows an alert, and returns to the home view.
	 */
	private void showRecipes() {
		this.mealList.getItems().clear();
		List<RecipeDTO> meals = this.loadRecipes();
		for (RecipeDTO meal : meals) {
			try {
				MealBox mealBox = this.makeMealBox(meal);
				this.mealList.getItems().add(mealBox);
			} catch (IOException e) {
				logger.error("Error while creating meal box", e);
				this.showAlert("Erreur de chargement d'Image", e.getMessage());
				this.returnHome();
			}
		}
	}

	/**
	 * Loads the recipes from the listener.
	 * This method should be called when the recipes need to be loaded.
	 *
	 * @return A list of RecipeDTO objects representing the loaded recipes.
	 */
	private List<RecipeDTO> loadRecipes() {
		return this.listener.loadRecipes();
	}

	/**
	 * Refreshes the meal list.
	 * This method should be called when the meal list needs to be refreshed.
	 * It clears the current meal list and shows the recipes again.
	 */
	private void refreshMealList() {
		this.mealList.getItems().clear();
		this.showRecipes();
	}

	/**
	 * Checks the meal.
	 * This method should be called when a meal needs to be checked.
	 * It triggers the checkMeal method of the listener with the meal's RecipeDTO object,
	 * and then refreshes the meal list.
	 *
	 * @param meal The MealBox object representing the meal to be checked.
	 */
	private void checkMeal(MealBox meal) {
		this.listener.checkMeal(meal.getMealDTO());
		this.refreshMealList();
	}

	/**
	 * Edits the meal.
	 * This method should be called when a meal needs to be edited.
	 * It deletes the meal, triggers the editMeal method of the listener with the meal's RecipeDTO object,
	 * and then refreshes the meal list.
	 *
	 * @param meal The MealBox object representing the meal to be edited.
	 */
	private void editMeal(MealBox meal) {
		this.deleteMeal(meal);
		this.listener.editMeal(meal.getMealDTO());
		this.refreshMealList();
	}

	/**
	 * Deletes the meal.
	 * This method should be called when a meal needs to be deleted.
	 * It triggers the deleteMeal method of the listener with the meal's RecipeDTO object,
	 * and then refreshes the meal list.
	 *
	 * @param meal The MealBox object representing the meal to be deleted.
	 */
	private void deleteMeal(MealBox meal) {
		this.listener.deleteMeal(meal.getMealDTO());
		this.refreshMealList();
	}

	@FXML
	private void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * Listener interface for the MealRecipeViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the MealRecipeViewController.
	 */
	public interface Listener {
		/**
		 * Loads the recipes.
		 * This method should be implemented to return a list of RecipeDTO objects.
		 *
		 * @return A list of RecipeDTO objects.
		 */
		List<RecipeDTO> loadRecipes();

		/**
		 * Checks the meal.
		 * This method should be implemented to handle the action of checking a meal.
		 *
		 * @param meal The RecipeDTO object that represents the meal to be checked.
		 */
		void checkMeal(RecipeDTO meal);

		/**
		 * Edits the meal.
		 * This method should be implemented to handle the action of editing a meal.
		 *
		 * @param meal The RecipeDTO object that represents the meal to be edited.
		 */
		void editMeal(RecipeDTO meal);

		/**
		 * Deletes the meal.
		 * This method should be implemented to handle the action of deleting a meal.
		 *
		 * @param meal The RecipeDTO object that represents the meal to be deleted.
		 */
		void deleteMeal(RecipeDTO meal);

		/**
		 * Returns to the home view.
		 * This method should be implemented to handle the action of returning to the home view.
		 */
		void returnHome();
	}
}
