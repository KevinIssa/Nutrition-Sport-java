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

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.ConsumedMealDTO;
import ulb.widgets.FoodHistoryBox;

public class MealHistoryViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(MealHistoryViewController.class);

	private MealHistoryViewController.Listener
			listener; // Listener interface for communication with the controller

	@FXML private ListView<FoodHistoryBox> historyList;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Empty method,no initialization needed (java:S1186)
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealHistoryViewController.Listener) listener;
		this.loadConsumedMeals();
	}

	/**
	 * Loads all consumed meals from the listener and adds them to the history list.
	 * For each meal, a FoodHistoryBox is created and added to the history list.
	 * The delete button of each FoodHistoryBox is set to call the deleteFoodInHistory method when clicked.
	 */
	private void loadConsumedMeals() {
		List<ConsumedMealDTO> meals = this.listener.getAllMeals();
		for (ConsumedMealDTO meal : meals) {
			for (ConsumedFoodDTO food : meal.consumedFoods()) {
				FoodHistoryBox mealHBox = new FoodHistoryBox(food, meal.date());
				mealHBox.getDeleteFoodButton().setOnAction(event -> deleteFoodInHistory(mealHBox));
				historyList.getItems().add(mealHBox);
			}
		}
	}

	/**
	 * Deletes a food from the history list.
	 * The food and its date are retrieved from the given FoodHistoryBox, and the deleteFood method of the listener is called with these values.
	 * The FoodHistoryBox is then removed from the history list.
	 * If an exception occurs, it is handled by the handleException method.
	 *
	 * @param foodBox The FoodHistoryBox representing the food to be deleted.
	 */
	private void deleteFoodInHistory(FoodHistoryBox foodBox) {
		try {
			ConsumedFoodDTO food = foodBox.getFoodDTO();
			LocalDateTime date = foodBox.getDate();
			listener.deleteFood(food, date);
			historyList.getItems().remove(foodBox);
		} catch (Exception e) {
			handleException(e);
		}
	}

	/**
	 * Handles exceptions by logging an error message.
	 * The message depends on the type of the exception.
	 *
	 * @param e The exception to be handled.
	 */
	private void handleException(Exception e) {
		switch (e) {
			case NumberFormatException ignored ->
					logger.error("Error parsing number: {}", e.getMessage());
			case DateTimeParseException ignored ->
					logger.error("Error parsing date: {}", e.getMessage());
			case ClassCastException ignored ->
					logger.error("Error casting class: {}", e.getMessage());
			case IndexOutOfBoundsException ignored ->
					logger.error("Error accessing index: {}", e.getMessage());
			case null -> logger.error("Error is null");
			default -> logger.error("Unexpected error:{} ", e.getMessage());
		}
	}

	/**
	 * Triggers the return to the home view.
	 * This method should be called when the user wants to return to the home view.
	 */
	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * Triggers the loading of the Add Meal view.
	 * This method should be called when the user wants to add a meal.
	 */
	public void loadAddMeal() {
		this.listener.addMeal();
	}

	/**
	 * Triggers the loading of the Meal Recipe view.
	 * This method should be called when the user wants to view a meal recipe.
	 */
	public void loadMealRecipe() {
		this.listener.mealRecipe();
	}

	/**
	 * Listener interface for the MealHistoryViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the MealHistoryViewController.
	 */
	public interface Listener {

		/**
		 * Retrieves all consumed meals.
		 * This method should be implemented to return a list of ConsumedMealDTO objects representing all consumed meals.
		 *
		 * @return A list of ConsumedMealDTO objects representing all consumed meals.
		 */
		List<ConsumedMealDTO> getAllMeals();

		/**
		 * Triggers the return to the home view.
		 * This method should be called when the user wants to return to the home view.
		 */
		void returnHome();

		/**
		 * Triggers the loading of the Add Meal view.
		 * This method should be called when the user wants to add a meal.
		 */
		void addMeal();

		/**
		 * Triggers the loading of the Meal Recipe view.
		 * This method should be called when the user wants to view a meal recipe.
		 */
		void mealRecipe();

		/**
		 * Deletes a consumed food.
		 * This method should be implemented to handle the action of deleting a consumed food.
		 *
		 * @param consumedFood The ConsumedFoodDTO object representing the consumed food to be deleted.
		 * @param dateTime The LocalDateTime object representing the date of the consumed food.
		 */
		void deleteFood(ConsumedFoodDTO consumedFood, LocalDateTime dateTime);
	}
}
