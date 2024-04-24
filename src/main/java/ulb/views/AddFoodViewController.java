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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.Food;
import ulb.widgets.FoodBox;
import ulb.widgets.FoodPopupController;
import ulb.widgets.NumberField;
import ulb.widgets.Search;

public class AddFoodViewController implements ViewController {
	@FXML private Search searchController;
	@FXML private ListView<FoodBox> chosenFoodList;
	@FXML private DatePicker date;
	@FXML private TextField hour;
	@FXML private TextField minute;

	private NumberField hourNumber;
	private NumberField minuteNumber;
	private static final Logger logger = LoggerFactory.getLogger(AddFoodViewController.class);
	private AddFoodViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.hourNumber = new NumberField(this.hour);
		this.minuteNumber = new NumberField(this.minute);
		LocalTime now = LocalTime.now();
		this.hourNumber.setValue(now.getHour());
		this.minuteNumber.setValue(now.getMinute());
		this.date.setValue(LocalDate.now());
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
	}

	@FXML
	private void suggestFoods() {
		String searchText = this.searchController.getText();
		listener.sendUserSearch(searchText);
	}

	/**
	 * This method adds the chosen food to the list when the user clicks on it.
	 */
	@FXML
	public void addClickedFood() {
		String chosenFood = this.searchController.getSelectedItem();
		if (chosenFood != null) {
			this.addChosenFood(chosenFood);
		}
	}

	/**
	 * This method adds the chosen food to the list when the user presses the enter key.
	 * @param event The key event.
	 */
	@FXML
	public void keyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			this.onEnterPress();
		}
	}

	private void onEnterPress() {
		String chosenFood = this.searchController.getSelectedItem();
		if (chosenFood == null && !this.searchController.isEmpty()) {
			chosenFood = this.searchController.getItems().getFirst();
		}
		if (chosenFood != null) {
			addChosenFood(chosenFood);
		}
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


	private void checkDate() throws IllegalArgumentException{
		LocalDate now = LocalDate.now();
		LocalDate foodDate = this.date.getValue();
		if (foodDate.isAfter(now)) {
			throw new IllegalArgumentException("La date ne peut pas être dans le futur");
		}
	}

	public LocalDateTime getDateTime() {
		return LocalDateTime.of(date.getValue(), getTime());
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
		if (this.chosenFoodList.getSelectionModel().isEmpty()) {
			return;
		}
		try {
			checkTime();
			checkDate();
			LocalDateTime mealDate = getDateTime();
			this.saveConsumedFoods(mealDate);
			chosenFoodList.getItems().clear();
		} catch (NumberFormatException e) {
			showAlert("Erreur", "Veuillez entrer une heure valide.");
		} catch (IllegalArgumentException e) {
			showAlert("Erreur", e.getMessage());
		}
	}

	private void saveConsumedFoods(LocalDateTime mealDate) {
		List<List<String>> consumedFoodsList = new ArrayList<>();
		for (FoodBox foodBox : chosenFoodList.getItems()) {
			consumedFoodsList.add(foodBox.getItems());
		}
		this.listener.saveConsumedFoods(consumedFoodsList, mealDate);
	}

	/**
	 * This method removes the selected food from the list when the user select an item and click on the cross.
	 * It first gets the selected item from the chosenFoodView.
	 * If the selected item is not null, it removes the item from the chosenFoodView.
	 * It then checks if the first child of the selected item is a Label.
	 * If it is, it gets the text of the label (which is the name of the selected food),
	 * and removes all food lists from consumedFoodsList that contain the selected food name.
	 */
	@FXML
	public void removeSelectedFood() {
		FoodBox selectedItem = chosenFoodList.getSelectionModel().getSelectedItem();
		if (selectedItem == null) {
			return;
		}
		chosenFoodList.getItems().remove(selectedItem);
	}

	// Helper methods
	public void setSuggestions(ObservableList<String> foods) {
		searchController.setResults(foods);
	}

	public void returnHome() {
		listener.returnHome();
	}

	private VBox loadPopupBox(FXMLLoader loader) {
		try {
			return loader.load();
		} catch (IOException e) {
			logger.error("Food_popup file not existing", e);
			System.exit(1);
			return null; // Unreachable
		}
	}

	private String processDialogResult(ButtonType buttonType, FoodPopupController controller) {
		if (buttonType == ButtonType.OK) {

			if (controller.getGramme() != 0) {
				return controller.getGramme() + " g";

			} else if (controller.getServing() != 0) {
				return controller.getServing() + " portion";
			}
		}
		return "0";
	}

	/**
	 * This method gets the user data for a food.
	 * It creates a dialog with a custom input dialog title, and loads the popup box for the food.
	 * It sets the serving label of the controller with the food serving quantity, and sets the content of the dialog pane with the box.
	 * It adds OK and Cancel buttons to the dialog pane, and sets the result converter of the dialog with the processDialogResult method.
	 * It then shows the dialog and waits for the user to input a value or cancel the dialog.
	 * If the user inputs a value, it returns the value. Otherwise, it returns "0".
	 * @param food The food.
	 * @return The user data.
	 */
	private String getUserFoodQuantity(String food) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_popup.fxml"));

		FoodPopupController controller = loader.getController();
		controller.setServinglabel(listener.getFoodServingQuantity(food));

		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Quantité de " + food);
		VBox box = loadPopupBox(loader);
		dialog.getDialogPane().setContent(box);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.setResultConverter(buttonType -> processDialogResult(buttonType, controller));

		return dialog.showAndWait().orElse("0");
	}

	private int getGramQuantity(String input, String food, Matcher matcher) {
		int quantity = Integer.parseInt(matcher.group());
		if (input.contains("portion")) {
			quantity *= this.listener.extractServingQuantityValue(food);
		}
		return quantity;
	}

	/**
	 * Extracts the quantity from the food selected by the user.
	 */
	private int extractQuantity(String input, String food) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return getGramQuantity(input, food, matcher);
		} else {
			logger.error("No match found in serving quantity {} for food {}", input, food);
			return 0;
		}
	}

	/**
	 * This method adds the chosen food to the list.
	 * It first gets the user data for the food, and extracts the quantity from it.
	 * If the quantity is 0, it returns without doing anything.
	 * Otherwise, it gets the calories consumed by the quantity of the food, and loads the food item box.
	 * It then gets the serving type of the food, and updates the food item box with the food, calories, quantity, serving type, and value.
	 * It adds the box to the chosenFoodView, and adds a list of the food, quantity, calories, and serving type (or "g" if the value contains "g") to the consumedFoodsList.
	 * @param food The chosen food.
	 */
	public void addChosenFood(String food) {
		String value = this.getUserFoodQuantity(food);
		int quantity = this.extractQuantity(value, food);
		if (quantity == 0) {
			return;
		}

		int calories = listener.getCaloriesConsumedByGrams(food, quantity);
		String foodUnit = listener.getFoodQuantityUnit(food);
		FoodBox foodBox = new FoodBox(food, calories, quantity, foodUnit);
		chosenFoodList.getItems().add(foodBox);
	}

	public interface Listener {
		void sendUserSearch(String searchText);

		void returnHome();

		int getCaloriesConsumedByGrams(String food, int quantity);


		void saveConsumedFoods(
				List<List<String>> consumedFoodsList, LocalDateTime mealDate);

		String getFoodServingQuantity(String food);

		int extractServingQuantityValue(String food);

		String getFoodQuantityUnit(String food);

		Food getCorrespondingFood(String food);
	}
}
