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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.FoodDTO;
import ulb.enums.Unit;
import ulb.exceptions.SavingException;
import ulb.models.ConsumedMeal;
import ulb.widgets.FoodBox;
import ulb.widgets.NumberField;
import ulb.widgets.Search;

public class AddFoodViewController implements ViewController, Search.Listener {
	@FXML private Search searchController;
	@FXML private ListView<FoodBox> chosenFoodList;
	@FXML private DatePicker date;
	@FXML private Label calorieLabel;
	@FXML private NumberField hourNumber;
	@FXML private NumberField minuteNumber;
	private static final Logger logger = LoggerFactory.getLogger(AddFoodViewController.class);
	private AddFoodViewController.Listener listener;
	private double totalCalories = 0;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		LocalTime now = LocalTime.now();
		this.hourNumber.setValue(now.getHour());
		this.minuteNumber.setValue(now.getMinute());
		this.date.setValue(LocalDate.now());
		this.date.getEditor().setStyle("-fx-background-color: #c9d1f0;");
		this.searchController.setListener(this);
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
	}

	@Override
	public ObservableList<String> getContent(String searchText) {
		return FXCollections.observableArrayList(this.listener.getUserSearch(searchText));
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

	private void checkTime() throws NumberFormatException {
		int hourValue = this.hourNumber.getValue();
		int minuteValue = this.minuteNumber.getValue();
		if (hourValue < 0 || hourValue > 23 || minuteValue < 0 || minuteValue > 59) {
			throw new NumberFormatException();
		}
	}

	private LocalTime getTime() throws NumberFormatException {
		return LocalTime.of(this.hourNumber.getValue(), this.minuteNumber.getValue());
	}

	private void checkDate() throws IllegalArgumentException {
		LocalDate now = LocalDate.now();
		LocalDate foodDate = this.date.getValue();
		if (foodDate.isAfter(now)) {
			throw new IllegalArgumentException("La date ne peut pas être dans le futur");
		}
	}

	public LocalDateTime getDateTime() throws IllegalArgumentException {
		return LocalDateTime.of(date.getValue(), getTime());
	}

	@FXML
	public void returnHome() {
		listener.returnHome();
	}

	@FXML
	public void cleanFoodList() {
		this.chosenFoodList.getItems().clear();
		this.totalCalories = 0;
		this.calorieLabel.setText("0");
	}

	/**
	 * This method saves the consumed foods selected.
	 * If the consumedFoodsList is empty, it returns without doing anything.
	 * If the mode is true (meal mode), it saves the meal if the name field is not empty and then reloads the view.
	 * If the mode is false (food mode), it tries to get the meal date and time. If it is null, it returns without doing anything.
	 * Otherwise, it saves the consumed foods with the meal date and time.
	 * Finally, it cleans the food list.
	 */
	@FXML
	public void saveConsumedFoods() {
		if (this.chosenFoodList.getItems().isEmpty()) {
			return;
		}
		try {
			checkTime();
			checkDate();
			LocalDateTime saveDate = getDateTime();
			ConsumedMeal consumedMeal = this.createMeal();
			consumedMeal.setDate(saveDate);
			this.listener.saveConsumedFoods(consumedMeal);
			this.cleanFoodList();
			this.goToHistoryMeal();
		} catch (NumberFormatException e) {
			showAlert("Erreur de timestamp", "Veuillez entrer une heure valide.");
		} catch (IllegalArgumentException e) {
			showAlert("Erreur de validité", e.getMessage());
		} catch (SavingException e) {
			showAlert(
					"Erreur de sauvegarde", "Erreur lors de la sauvegarde des aliments consommés.");
		}
	}

	private void goToHistoryMeal() {
		this.listener.goToHistoryMeal();
	}

	private ConsumedMeal createMeal() {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		for (ConsumedFoodDTO consumedFood : this.getConsumedFoods()) {
			consumedMeal.addConsumedFood(
					consumedFood.name(),
					Double.parseDouble(String.valueOf(consumedFood.quantity())),
					Double.parseDouble(String.valueOf(consumedFood.calories())),
					consumedFood.unit());
		}
		return consumedMeal;
	}

	private List<ConsumedFoodDTO> createConsumedFoodDTOList() {
		List<ConsumedFoodDTO> consumedFoodsList = new ArrayList<>();
		for (FoodBox foodBox : chosenFoodList.getItems()) {
			List<String> items = foodBox.getItems();
			String foodName = items.get(0);
			double quantity = Double.parseDouble(items.get(1));
			double calories = Double.parseDouble(items.get(2));
			String unit = items.get(3);
			consumedFoodsList.add(new ConsumedFoodDTO(foodName, quantity, calories, unit));
		}
		return consumedFoodsList;
	}

	private List<ConsumedFoodDTO> getConsumedFoods() {
		return createConsumedFoodDTOList();
	}

	@FXML
	public void makeMeal() {
		this.listener.changeMode();
	}

	private void addFoodBox(FoodDTO food, double calories) {
		Button deleteFoodButton = new Button("X");
		deleteFoodButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		FoodBox foodBox = new FoodBox(deleteFoodButton, food, calories);
		deleteFoodButton.setOnAction(e -> this.deleteChosenFood(foodBox, calories));
		chosenFoodList.getItems().add(foodBox);
	}

	/**
	 * This method adds the chosen food to the list.
	 * It gets the serving type of the food, and updates the food item box with the food, calories, quantity, serving type, and value.
	 * @param food The chosen food DTO.
	 */
	public void addChosenFood(FoodDTO food) {
		double calories = listener.getCaloriesConsumed(food);
		// Round to 2 decimals
		calories = BigDecimal.valueOf(calories).setScale(2, RoundingMode.DOWN).doubleValue();
		this.totalCalories += calories;
		this.calorieLabel.setText(String.format("%.2f", this.totalCalories));

		this.addFoodBox(food, calories);
	}

	private void deleteChosenFood(FoodBox foodBox, double calories) {
		this.chosenFoodList.getItems().remove(foodBox);
		this.totalCalories -= calories;
		this.calorieLabel.setText(Double.toString(this.totalCalories));
	}

	/**
	 * Listener interface for the AddFoodViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the AddFoodViewController.
	 */
	public interface Listener {

		/**
		 * Asks the user for the quantity of the selected food.
		 * This method should be implemented to handle the action of asking the user for the quantity of the selected food.
		 *
		 * @param food The name of the food for which the quantity is to be asked.
		 */
		void askUserFoodQuantity(String food);

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
		 * Retrieves the serving quantity for a given food.
		 * This method should be implemented to return the serving quantity for a given food.
		 *
		 * @param food The name of the food for which the serving quantity is to be retrieved.
		 * @return The serving quantity for the given food.
		 */
		String getFoodServingQuantity(String food);

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
		 * Saves the consumed foods.
		 * This method should be implemented to handle the action of saving the consumed foods.
		 *
		 * @param consumedMeal The ConsumedMeal object representing the consumed foods to be saved.
		 * @throws SavingException If an error occurs while saving the consumed foods.
		 */
		void saveConsumedFoods(ConsumedMeal consumedMeal) throws SavingException;

		void goToHistoryMeal();
	}
}
