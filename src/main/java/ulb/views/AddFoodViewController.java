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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.Food;
import ulb.widgets.FoodPopupController;


public class AddFoodViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(AddFoodViewController.class);
	@FXML private TextField searchField;
	@FXML private ListView<String> suggestionsList;
	@FXML private ListView<HBox> chosenFoodView;
	@FXML private Slider slider;
	@FXML private Label title;
	@FXML private Label name;
	@FXML private TextField textField;
	@FXML private DatePicker mealDate;
	@FXML private TextField hour;
	@FXML private TextField minutes;
	@FXML private Group date;
	@FXML private Label calorieNumber;
	private boolean mode = false;
	private final ArrayList<ArrayList<String>> consumedFoodsList = new ArrayList<>();
	private AddFoodViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.mealDate.getEditor().setStyle("-fx-background-color: #c9d1f0;");
		this.hour.setText(String.valueOf(LocalTime.now().getHour()));
		this.minutes.setText(String.valueOf(LocalTime.now().getMinute()));
		this.mealDate.setValue(LocalDate.now());
		this.name.setVisible(false);
		this.textField.setVisible(false);
		this.slider
				.valueProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							this.mode = newValue.intValue() == 1;
							changeMode();
						});
		searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (! newValue) {
				if (!suggestionsList.isFocused()){
					cleanSuggestedFoodList();
				}
			}
		});
	}

	/**
	 * This method changes the mode of the view.
	 * If the mode is true, it switches to meal mode.
	 * If the mode is false, it switches to food mode.
	 */
	public void changeMode() {
		if (mode) {
			title.setText("Ajoutez un plat");
			name.setVisible(true);
			textField.setVisible(true);
			date.setVisible(false);
			textField.setText("");
		} else {
			title.setText("Ajoutez les aliments consommés");
			name.setVisible(false);
			textField.setVisible(false);
			date.setVisible(true);
		}
		consumedFoodsList.clear();
		chosenFoodView.getItems().clear();
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
		String searchText = searchField.getText();
		listener.sendUserSearch(searchText);
		suggestionsList.getSelectionModel().selectFirst();
	}

	/**
	 * This method adds the chosen food to the list when the user clicks on it.
	 */
	@FXML
	public void addChosenFoodMouse() {
		String chosenFood = suggestionsList.getSelectionModel().getSelectedItem();
		if (chosenFood != null) {
			addChosenFood(chosenFood);
		}
		cleanSuggestedFoodList();
	}



	/**
	 * This method adds the chosen food to the list when the user presses the enter key.
	 * @param event The key event.
	 */
	@FXML
	public void keyPress(KeyEvent event) {
		switch (event.getCode()) {
			case ENTER:
				this.onEnterPress();
				break;
			case DOWN:
				this.onDownPress();
				break;
			case UP:
				this.onUpPress();
		}
	}

	private void onUpPress() {
		if (this.suggestionsList.getSelectionModel().getSelectedIndex() != 0) {
			this.suggestionsList.getSelectionModel().selectPrevious();
		}
		this.searchField.setText(this.suggestionsList.getSelectionModel().getSelectedItem());
		int index = this.suggestionsList.getSelectionModel().getSelectedIndex();
		if (index - 3 <= this.suggestionsList.getItems().size() - 1) {
			this.suggestionsList.scrollTo(index - 3);
		}
	}

	private void onDownPress() {
		if (this.suggestionsList.getSelectionModel().getSelectedIndex()
				!= this.suggestionsList.getItems().size() - 1) {
			this.suggestionsList.getSelectionModel().selectNext();
		}
		this.searchField.setText(this.suggestionsList.getSelectionModel().getSelectedItem());
		int index = this.suggestionsList.getSelectionModel().getSelectedIndex();
		if (index - 4 >= 0) {
			this.suggestionsList.scrollTo(index - 4);
		}
	}

	private void onEnterPress() {
		String chosenFood = suggestionsList.getSelectionModel().getSelectedItem();
		if (chosenFood == null && !suggestionsList.getItems().isEmpty()) {
			chosenFood = suggestionsList.getItems().get(0);
		}
		if (chosenFood != null) {
			addChosenFood(chosenFood);
		}
	}

	/**
	 * This method returns the time of the meal entered by the user and handles the exceptions in case of an invalid input.
	 * @return The time of the meal.
	 */
	public LocalTime getMealTime() {
		try {
			int intHour = Integer.parseInt(hour.getText());
			int intMinutes = Integer.parseInt(minutes.getText());

			if (intHour < 0 || intHour > 23 || intMinutes < 0 || intMinutes > 59) {
				showAlert(
						"Heure invalide",
						"L'heure doit être comprise entre 0 et 23 et les minutes entre 0 et 59");
				return null;
			}

			return LocalTime.of(intHour, intMinutes);
		} catch (NumberFormatException e) {
			showAlert("Heure invalide", "L'heure doit être un nombre");
			return null;
		}
	}

	public LocalDateTime getMealDateTime() {
		LocalDate currentDate = LocalDate.now();
		if (mealDate.getValue().isAfter(currentDate)) {
			showAlert("Date invalide", "La date ne peut pas être dans le futur");
			return null;
		}

		return LocalDateTime.of(mealDate.getValue(), getMealTime());
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
		if (consumedFoodsList.isEmpty()) {
			return;
		}
		if (mode) {
			if (!textField.getText().isEmpty()) {
				this.listener.saveMeal(textField.getText(), consumedFoodsList);
			}
			this.listener.reload();
		} else {
			try {
				LocalDateTime mealDate = getMealDateTime();
				if (mealDate == null) {
					throw new NullPointerException();
				}
				this.listener.saveConsumedFoods(consumedFoodsList, mealDate);

			} catch (NullPointerException e) {
				return;
			}
		}
		cleanFoodList();
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
		HBox selectedItem = chosenFoodView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			chosenFoodView.getItems().remove(selectedItem);
			if (selectedItem.getChildren().get(0) instanceof Label) {
				Label label = (Label) selectedItem.getChildren().get(0);
				String selectedFoodName = label.getText();
				consumedFoodsList.removeIf(foodList -> foodList.contains(selectedFoodName));

			}
			if (selectedItem.getChildren().get(1) instanceof Label){
				Label label = (Label) selectedItem.getChildren().get(1);
				String selectedFoodcalorie = label.getText();
				String[] splittedFood = selectedFoodcalorie.split("\\s+");
				String calorieString = splittedFood[1];
				removeCalorie(Integer.parseInt(calorieString));
			}
		}
	}

	// Helper methods

	public void setSuggestions(List<String> foods) {
		suggestionsList.getItems().setAll(foods);
	}

	public void returnHome() {
		listener.returnHome();
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
		String value = getUserData(food);
		int quantity = extractQuantity(value, food);
		if (quantity == 0) {
			return;
		}

		int calories = listener.getCaloriesConsumedByGrams(food, quantity);
		HBox box = loadFoodItemBox();
		String servingType = listener.getFoodServingType(food);
		updateFoodItemBox(box, food, calories, quantity, servingType, value);
		chosenFoodView.getItems().add(box);

		consumedFoodsList.add(
				new ArrayList<>(
						List.of(
								food,
								Integer.toString(quantity),
								Integer.toString(calories),
								value.contains("g") ? "g" : servingType)));
		addCalorie(calories);
	}
	public void removeCalorie(int calorie){
		String calorieString = this.calorieNumber.getText();
		String numericString = calorieString.replaceAll("[^0-9]", ""); // Remove non-numeric characters
		setCalorieNumber(Integer.parseInt(numericString) - calorie);
	}
	public void addCalorie(int calorie){
		String calorieString = this.calorieNumber.getText();
		String numericString = calorieString.replaceAll("[^0-9]", ""); // Remove non-numeric characters
		setCalorieNumber(Integer.parseInt(numericString) + calorie);
	}
	/**
	 * This method use the parameter given to show on the view the correct calorie number on calorieNumber
	 * @param calorie number of calorie that need to be showed
	 */
	public void setCalorieNumber(int calorie){
		String toString = String.valueOf(calorie);
		String calorieString = "Kcal: " + " ".repeat(10 - toString.length()) + toString;
		this.calorieNumber.setText(calorieString);
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
	private String getUserData(String food) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Custom Input Dialog");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_popup.fxml"));
		VBox box = loadPopupBox(loader);

		FoodPopupController controller = loader.getController();
		controller.setServinglabel(listener.getFoodServingQuantity(food));

		dialog.getDialogPane().setContent(box);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.setResultConverter(buttonType -> processDialogResult(buttonType, controller));

		return dialog.showAndWait().orElse("0");
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
	 * Extracts the quantity from the the food selected by the user.
	 */
	private int extractQuantity(String input, String food) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {

			int quantity = Integer.parseInt(matcher.group());
			if (input.contains("portion")) {
				quantity *= listener.extractServingQuantityValue(food);
			}
			return quantity;

		} else {
			logger.error("No match found in serving quantity {} for food {}", input, food);
			return 0;
		}
	}

	private HBox loadFoodItemBox() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_item.fxml"));
		try {
			return loader.load();
		} catch (IOException e) {
			logger.error("Food_item file not existing", e);
			System.exit(1);
			return null; // Unreachable
		}
	}

	private void updateFoodItemBox(
			HBox box, String food, int calories, int quantity, String servingType, String value) {

		Label label1 = (Label) box.getChildren().get(0);
		label1.setText(food);

		Label label2 = (Label) box.getChildren().get(1);
		String quantityText =
				value.contains("g")
						? String.format(
								"Calories: %d          quantites(g): %d", calories, quantity)
						: String.format(
								"Calories: %d          quantites(%s): %d",
								calories, servingType, quantity);
		label2.setText(quantityText);
	}

	public void cleanFoodList() {
		chosenFoodView.getItems().clear();
		consumedFoodsList.clear();
		setCalorieNumber(0);
	}

	public void cleanSuggestedFoodList(){
		searchField.clear();
		suggestionsList.getItems().clear();
	}

	public interface Listener {
		void sendUserSearch(String searchText);

		void returnHome();

		int getCaloriesConsumedByGrams(String food, int quantity);

		void saveMeal(String mealName, ArrayList<ArrayList<String>> consumedFoodsList);

		void saveConsumedFoods(
				ArrayList<ArrayList<String>> consumedFoodsList, LocalDateTime mealDate);

		String getFoodServingQuantity(String food);

		int extractServingQuantityValue(String food);

		String getFoodServingType(String food);

		Food getCorrespondingFood(String food);

		void reload();
	}
}
