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
import ulb.widgets.FoodBox;
import ulb.widgets.NumberField;
import ulb.widgets.Search;

public class AddFoodViewController implements ViewController, Search.Listener {
	@FXML private Search searchController;
	@FXML private ListView<FoodBox> chosenFoodList;
	@FXML private DatePicker date;
	@FXML private TextField hour;
	@FXML private TextField minute;
	@FXML private Label calorieLabel;
	private NumberField hourNumber;
	private NumberField minuteNumber;
	private static final Logger logger = LoggerFactory.getLogger(AddFoodViewController.class);
	private AddFoodViewController.Listener listener;
	private double totalCalories = 0;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.hourNumber = new NumberField(this.hour);
		this.minuteNumber = new NumberField(this.minute);
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
		if(searchText == null){
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

	private LocalTime getTime() {
		return LocalTime.of(this.hourNumber.getValue(), this.minuteNumber.getValue());
	}

	private void checkDate() throws IllegalArgumentException {
		LocalDate now = LocalDate.now();
		LocalDate foodDate = this.date.getValue();
		if (foodDate.isAfter(now)) {
			throw new IllegalArgumentException("La date ne peut pas être dans le futur");
		}
	}

	public LocalDateTime getDateTime() {
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
			this.listener.saveConsumedFoods(this.getConsumedFoods(), saveDate);
			this.cleanFoodList();
		} catch (NumberFormatException e) {
			showAlert("Erreur", "Veuillez entrer une heure valide.");
		} catch (IllegalArgumentException e) {
			showAlert("Erreur", e.getMessage());
		}
	}

	private List<List<String>> getConsumedFoods() {
		List<List<String>> consumedFoodsList = new ArrayList<>();
		for (FoodBox foodBox : chosenFoodList.getItems()) {
			consumedFoodsList.add(foodBox.getItems());
		}
		return consumedFoodsList;
	}

	@FXML
	public void makeMeal() {
		//TODO NOT IMPLEMENTED
	}

	private void addFoodBox(String food, double quantity, double calories, String foodUnit) {
		Button deleteFoodButton = new Button("X");
		deleteFoodButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		FoodBox foodBox = new FoodBox(deleteFoodButton, food, calories, quantity, foodUnit);
		deleteFoodButton.setOnAction(e -> this.deleteChosenFood(foodBox, calories));
		chosenFoodList.getItems().add(foodBox);
	}

	/**
	 * This method adds the chosen food to the list.
	 * It gets the serving type of the food, and updates the food item box with the food, calories, quantity, serving type, and value.
	 * @param food The chosen food.
	 * @param quantity The quantity (in grams) of the food.
	 */
	public void addChosenFood(String food, double quantity) {
		double calories = listener.getCaloriesConsumed(food, quantity);
		//Round to 2 decimals
		calories = Double.parseDouble(String.format("%.2f", calories));
		this.totalCalories += calories;
		this.calorieLabel.setText(String.format("%.2f", this.totalCalories));
		String foodUnit = listener.getFoodUnit(food);

		this.addFoodBox(food, quantity, calories, foodUnit);
	}

	private void deleteChosenFood(FoodBox foodBox, double calories) {
		this.chosenFoodList.getItems().remove(foodBox);
		this.totalCalories -= calories;
		this.calorieLabel.setText(Double.toString(this.totalCalories));
	}

	public interface Listener {
		List<String> getUserSearch(String searchText);

		void returnHome();

		double getCaloriesConsumed(String food, double quantity);

		void saveConsumedFoods(List<List<String>> consumedFoodsList, LocalDateTime mealDate);

		String getFoodServingQuantity(String food);

		String getFoodUnit(String food);

		void askUserFoodQuantity(String food);
	}
}
