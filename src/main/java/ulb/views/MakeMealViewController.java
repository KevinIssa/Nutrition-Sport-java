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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;
import ulb.enums.Unit;
import ulb.exceptions.SavingException;
import ulb.widgets.FoodBox;
import ulb.widgets.NumberField;
import ulb.widgets.Search;

public class MakeMealViewController implements ViewController, Search.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MakeMealViewController.class);
	@FXML private TextField mealName;
	@FXML private Search searchController;
	@FXML private ListView<FoodBox> chosenFoodList;
	@FXML private Label calorieLabel;
	@FXML private NumberField personAmountNumber;
	@FXML private ToggleButton switchButton;
	private double totalCalories = 0;
	private boolean isEditMode;
	private RecipeDTO recipeDefaultDTO;
	private MakeMealViewController.Listener listener;

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

	private void addFoodBox(FoodDTO foodDTO, double calories) {
		Button deleteFoodButton = new Button("X");
		deleteFoodButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		FoodBox foodBox = new FoodBox(deleteFoodButton, foodDTO, calories);
		deleteFoodButton.setOnAction(e -> this.deleteChosenFood(foodBox, calories));
		chosenFoodList.getItems().add(foodBox);
	}

	public void setMeal(RecipeDTO meal) {
		this.switchButton.setVisible(false);
		this.mealName.setText(meal.name());
		this.personAmountNumber.setValue(1);
		for (FoodDTO ingredient : meal.foods()) {
			addChosenFood(ingredient);
		}
	}

	/**
	 * This method adds the chosen food to the list.
	 * It gets the serving type of the food, and updates the food item box with the food, calories, quantity, serving type, and value.
	 * @param foodDTO The chosen food DTO
	 */
	public void addChosenFood(
			FoodDTO foodDTO) { // duplicate code with AddFoodViewController but time is running out
		double calories = listener.getCaloriesConsumed(foodDTO);
		// Round to 2 decimals
		calories = BigDecimal.valueOf(calories).setScale(2, RoundingMode.DOWN).doubleValue();
		this.totalCalories += calories;
		this.calorieLabel.setText(String.format("%.2f", this.totalCalories));

		this.addFoodBox(foodDTO, calories);
	}

	private void deleteChosenFood(FoodBox foodBox, double calories) {
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

	/**
	 * Returns to the home view.
	 * If the view is in edit mode, it attempts to save the default recipe.
	 * If an error occurs while saving, it logs an error message.
	 * Regardless of whether the view is in edit mode or not, it triggers the return to the home view.
	 */
	public void returnHome() {
		if (this.isEditMode) {
			try {
				this.listener.saveMeal(recipeDefaultDTO);
			} catch (SavingException e) {
				logger.error("Error while saving the meal");
				// should not happen because the meal already exists and will save
			}
		}
		this.listener.returnHome();
	}

	/**
	 * Saves a meal.
	 * It creates a list of FoodDTO objects from the chosen foods, and attempts to save a new RecipeDTO object with the meal name, the list of foods, and the number of persons.
	 * If an error occurs while parsing the number of persons, it logs an error message.
	 * If the meal already exists, it logs an info message, sets the view to edit mode, and shows an alert.
	 * After saving the meal, it cleans the food list.
	 */
	public void saveMeal() {
		try {
			this.isEditMode = false;
			List<FoodDTO> foodlist = new java.util.ArrayList<>();
			for (FoodBox foodBox : chosenFoodList.getItems()) {
				foodlist.add(
						new FoodDTO(
								foodBox.getFood(),
								foodBox.getQuantityValue(),
								listener.getFoodUnit(foodBox.getFood())));
			}
			this.listener.saveMeal(
					new RecipeDTO(
							this.mealName.getText(), foodlist, this.personAmountNumber.getValue()));
			this.cleanFoodList();
			this.goToHistoryRecipe();
		} catch (NumberFormatException e) {
			logger.error("Error while parsing the number of persons");
			// juste ignore it normally nothing to do
		} catch (SavingException e) {
			logger.info("the meal could not be saved because it exists already");
			this.isEditMode = true;
			showAlert("This meal already exists", "Please choose another name for your meal");
		}
	}

	private void goToHistoryRecipe() {
		this.listener.goToHistoryRecipe();
	}

	/**
	 * Sets the default recipe.
	 * It sets the default recipe, sets the view to edit mode, hides the switch button, sets the meal name and the number of persons, and adds the foods of the recipe to the chosen foods.
	 *
	 * @param recipeDTO The RecipeDTO object representing the default recipe.
	 */
	public void setDefaultRecipe(RecipeDTO recipeDTO) {
		logger.debug("Setting default recipe: {}", recipeDTO);
		this.recipeDefaultDTO = recipeDTO;
		this.isEditMode = true;
		this.switchButton.setVisible(false);
		this.mealName.setText(recipeDTO.name());
		this.personAmountNumber.setValue(1);
		for (FoodDTO foodDTO : recipeDTO.foods()) {
			logger.info("Adding foodDTO: {}", foodDTO);
			addChosenFood(foodDTO);
		}
	}

	/**
	 * Listener interface for the MakeMealViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the MakeMealViewController.
	 */
	public interface Listener {

		/**
		 * Asks the user for the quantity of the selected food.
		 * This method should be implemented to handle the action of asking the user for the quantity of the selected food.
		 *
		 * @param searchText The text that the user has searched for.
		 */
		void askUserFoodQuantity(String searchText);

		/**
		 * Changes the mode of the view.
		 * This method should be implemented to handle the action of changing the mode of the view.
		 */
		void changeMode();

		/**
		 * Retrieves the calories consumed for a given food.
		 * This method should be implemented to return the calories consumed for a given food.
		 *
		 * @param food The FoodDTO object representing the food for which the calories consumed are to be retrieved.
		 * @return The calories consumed for the given food.
		 */
		double getCaloriesConsumed(FoodDTO food);

		/**
		 * Retrieves the unit of a given food.
		 * This method should be implemented to return the unit of a given food.
		 *
		 * @param food The name of the food for which the unit is to be retrieved.
		 * @return The unit of the given food.
		 */
		Unit getFoodUnit(String food);

		/**
		 * Retrieves the user's search results.
		 * This method should be implemented to return a list of strings representing the user's search results.
		 *
		 * @param searchText The text that the user has searched for.
		 * @return A list of strings representing the user's search results.
		 */
		List<String> getUserSearch(String searchText);

		/**
		 * Triggers the return to the home view.
		 * This method should be implemented to handle the action of returning to the home view.
		 */
		void returnHome();

		/**
		 * Saves a meal.
		 * This method should be implemented to handle the action of saving a meal.
		 *
		 * @param recipeDTO The RecipeDTO object representing the meal to be saved.
		 * @throws SavingException If an error occurs while saving the meal.
		 */
		void saveMeal(RecipeDTO recipeDTO) throws SavingException;

		void goToHistoryRecipe();
	}
}
