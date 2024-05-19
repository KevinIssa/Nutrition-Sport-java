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
		} catch (NumberFormatException e) {
			showAlert("Erreur", "Veuillez entrer une heure valide.");
		} catch (IllegalArgumentException e) {
			showAlert("Erreur", e.getMessage());
		}
	}

	private ConsumedMeal createMeal() {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		for (ConsumedFoodDTO consumedFood : this.getConsumedFoods()) {
			consumedMeal.addConsumedFood(
				consumedFood.name(),
				Double.parseDouble(String.valueOf(consumedFood.quantity())),
				Double.parseDouble(String.valueOf(consumedFood.calories())),
				consumedFood.unit()
			);
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

	public interface Listener {
		void askUserFoodQuantity(String food);

		void changeMode();

		double getCaloriesConsumed(FoodDTO food);

		String getFoodServingQuantity(String food);

		Unit getFoodUnit(String food);

		List<String> getUserSearch(String searchText);

		void returnHome();

		void saveConsumedFoods(ConsumedMeal consumedMeal);
	}
}
