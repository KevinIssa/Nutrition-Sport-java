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

	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealHistoryViewController.Listener) listener;
		this.loadConsumedMeals();
	}

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

	private void handleException(Exception e) {
		switch (e) {
				// variables are intentionally unused
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

	public void returnHome() {
		this.listener.returnHome();
	}

	public void loadAddMeal() {
		this.listener.addMeal();
	}

	public void loadMealRecipe() {
		this.listener.mealRecipe();
	}

	public interface Listener {

		List<ConsumedMealDTO> getAllMeals(); // Load all meals

		void returnHome(); // Return to the home view

		void addMeal();

		void mealRecipe();

		void deleteFood(ConsumedFoodDTO consumedFood, LocalDateTime dateTime);
	}
}
