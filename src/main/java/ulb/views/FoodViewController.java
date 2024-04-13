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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ulb.models.Food;
import ulb.widgets.FoodPopupController;

public class FoodViewController implements ViewController {

	@FXML private TextField searchField;
	@FXML private ListView<String> suggestionsList;
	@FXML private ListView<HBox> chosenFoodView;
	@FXML private Slider slider;
	@FXML private Label title;
	@FXML private Label name;
	@FXML private TextField namefield;
	@FXML private Label statuslabel;
	private Boolean mode;
	@FXML private DatePicker mealdate;
	@FXML private TextField hour;
	@FXML private TextField minutes;
	@FXML private Group date;

	private ArrayList<ArrayList<String>> consumedFoodsList = new ArrayList<>();
	private FoodViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Set the current time
		hour.setText(String.valueOf(LocalTime.now().getHour()));
		minutes.setText(String.valueOf(LocalTime.now().getMinute()));
		// Set the current date
		mealdate.setValue(LocalDate.now());
		// Hide the name label and text field
		name.setVisible(false);
		namefield.setVisible(false);
		// Configure the slider
		configSlider();
		// Add a listener to the slider value property
		slider.valueProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							// Update the boolean variable based on slider value
							mode = newValue.intValue() == 1;
							// Update the status label
							statuslabel.setText(mode ? "plats" : "aliments");
							// Call function whenever the slider is modified
							changeMode();
						});
	}

	private void configSlider() {
		slider.setMin(0);
		slider.setMax(1);
		slider.setValue(0);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		mode = false; // * Default value
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
			namefield.setVisible(true);
			date.setVisible(false);
			namefield.setText("");
			consumedFoodsList.clear();
			chosenFoodView.getItems().clear();
		} else {
			title.setText("Ajoutez les aliments consommés");
			name.setVisible(false);
			namefield.setVisible(false);
			date.setVisible(true);
			consumedFoodsList.clear();
			chosenFoodView.getItems().clear();
		}
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	@FXML
	private void suggestFoods() {
		String searchText = searchField.getText();
		listener.sendUserSearch(searchText);
	}

	/**
	 * This method adds the chosen food to the list when the user clicks on it.
	 * @param event The mouse event.
	 */
	@FXML
	public void addChosenFoodMouse(MouseEvent event) {
		String chosenFood = suggestionsList.getSelectionModel().getSelectedItem();
		if (chosenFood != null) {
			addChosenFood(chosenFood);
		}
	}

	/**
	 * This method adds the chosen food to the list when the user presses the enter key.
	 * @param event The key event.
	 */
	@FXML
	public void addChosenFoodKey(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String chosenFood = suggestionsList.getSelectionModel().getSelectedItem();
			if (chosenFood == null && !suggestionsList.getItems().isEmpty()) {
				chosenFood = suggestionsList.getItems().get(0);
			}
			if (chosenFood != null) {
				addChosenFood(chosenFood);
			}
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

			LocalTime time =
					LocalTime.of(
							Integer.parseInt(hour.getText()), Integer.parseInt(minutes.getText()));
			return time;
		} catch (NumberFormatException e) {
			showAlert("Heure invalide", "L'heure doit être un nombre");
			return null;
		}
	}

	public boolean isDateInFuture(LocalDate date1, LocalDate date2) {
		return date1.compareTo(date2) > 0;
	}

	/**
	 * This method returns the date and time of the meal entered by the user.
	 * @return The date and time of the meal.
	 */
	public LocalDateTime getMealDateTime() {

		LocalDate currentDate = LocalDate.now();
		if (isDateInFuture(mealdate.getValue(), currentDate)) {
			showAlert("Date invalide", "La date ne peut pas être dans le futur");
			return null;
		}

		LocalDateTime mealdatetime = LocalDateTime.of(mealdate.getValue(), getMealTime());
		return mealdatetime;
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
			if (!Objects.equals(namefield.getText(), "")) {
				this.listener.saveMeal(namefield.getText(), consumedFoodsList);
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
			throw new RuntimeException("Food_popup file not existing", e);
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
			throw new IllegalArgumentException("No integer found in the input string");
		}
	}

	private HBox loadFoodItemBox() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_item.fxml"));
		try {
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Food_item file not existing", e);
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
	}

	public interface Listener {
		void sendUserSearch(String searchText);

		void returnHome();

		int getCaloriesConsumedByGrams(String food, int quantity);

		void saveMeal(String mealname, ArrayList<ArrayList<String>> consumedFoodsList);

		void saveConsumedFoods(
				ArrayList<ArrayList<String>> consumedFoodsList, LocalDateTime mealdate);

		String getFoodServingQuantity(String food);

		int extractServingQuantityValue(String food);

		String getFoodServingType(String food);

		Food getCorrespondingFood(String food);

		void reload();
	}
}
