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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.FoodDTO;
import ulb.models.Food;
import ulb.models.Meal;
import ulb.widgets.FoodBox;
import ulb.widgets.NumberField;
import ulb.widgets.Search;

public class MakeMealViewController implements ViewController, Search.Listener {
	@FXML private TextField mealName;
	@FXML private Search searchController;
	@FXML private ListView<FoodBox> chosenFoodList;
	@FXML private Label calorieLabel;
	@FXML private NumberField personAmountNumber;
	@FXML private ToggleButton switchButton;
	private double totalCalories = 0;
	private MakeMealViewController.Listener listener;
	private static final Logger logger = LoggerFactory.getLogger(MakeMealViewController.class);

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.searchController.setListener(this);
	}

	/**
	 * This method adds the chosen food to the list when the user clicks on it.
	 */
	@Override
	public void onClick(String searchText) {
		if (searchText == null) {
			return;
		}
		this.listener.askUserFoodQuantity(searchText);
	}

	@Override
	public ObservableList<String> getContent(String searchText) {
		return FXCollections.observableArrayList(this.listener.getUserSearch(searchText));
	}

	@FXML
	public void consumeFoods() {
		this.listener.changeMode();
	}

	@FXML
	public void cleanFoodList() {
		this.chosenFoodList.getItems().clear();
		this.totalCalories = 0;
		this.calorieLabel.setText("0");
	}

	private void addFoodBox(String food, double quantity, double calories, String foodUnit) {
		Button deleteFoodButton = new Button("X");
		deleteFoodButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		FoodBox foodBox = new FoodBox(deleteFoodButton, food, calories, quantity, foodUnit);
		deleteFoodButton.setOnAction(e -> this.deleteChosenFood(foodBox, calories, quantity));
		chosenFoodList.getItems().add(foodBox);
	}

	public void setDefaultRecipe(Meal meal) {
		this.switchButton.setVisible(false);
		this.mealName.setText(meal.getName());
		this.personAmountNumber.setValue(1);
		for (Map.Entry<Food, Double> ingredient : meal.getIngredients()) {
			addChosenFood(ingredient.getKey().getName(), ingredient.getValue());
		}
	}

	/**
	 * This method adds the chosen food to the list.
	 * It gets the serving type of the food, and updates the food item box with the food, calories, quantity, serving type, and value.
	 * @param food The chosen food.
	 * @param quantity The quantity (in grams) of the food.
	 */
	public void addChosenFood(String food, double quantity) {
		double calories = listener.getCaloriesConsumed(food, quantity);
		// Round to 2 decimals
		calories = BigDecimal.valueOf(calories).setScale(2, RoundingMode.DOWN).doubleValue();
		quantity = BigDecimal.valueOf(quantity).setScale(2, RoundingMode.DOWN).doubleValue();
		this.totalCalories += calories;
		this.calorieLabel.setText(String.format("%.2f", this.totalCalories));
		String foodUnit = listener.getFoodUnit(food);

		this.addFoodBox(food, quantity, calories, foodUnit);
	}

	private void deleteChosenFood(FoodBox foodBox, double calories, double quantity) {
		this.chosenFoodList.getItems().remove(foodBox);
		this.totalCalories -= calories;
		this.calorieLabel.setText(Double.toString(this.totalCalories));
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void saveMeal() {
		try {
			List<FoodDTO> foodlist = new java.util.ArrayList<>();
			for (FoodBox foodBox : chosenFoodList.getItems()) {
				foodlist.add(new FoodDTO(foodBox.getFood(), foodBox.getQuantityValue()));
			}
			this.listener.saveMeal(
					this.mealName.getText(), foodlist, this.personAmountNumber.getValue());
			this.cleanFoodList();
		} catch (NumberFormatException e) {
			logger.error("Error while parsing the number of persons");
			// juste ignore it normally nothing to do
		}
	}

	public interface Listener {
		void askUserFoodQuantity(String searchText);

		List<String> getUserSearch(String searchText);

		double getCaloriesConsumed(String food, double quantity);

		String getFoodUnit(String food);

		void returnHome();

		void saveMeal(String mealName, List<FoodDTO> foodsList, int personAmount);

		void changeMode();
	}
}
