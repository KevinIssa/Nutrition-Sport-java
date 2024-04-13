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

		hour.setText(String.valueOf(LocalTime.now().getHour()));
		minutes.setText(String.valueOf(LocalTime.now().getMinute()));
		mealdate.setValue(LocalDate.now());
		name.setVisible(false);
		namefield.setVisible(false);
		configSlider();
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

	// Action event handlers
	@FXML
	private void suggestFoods() {
		String searchText = searchField.getText();
		listener.sendUserSearch(searchText);
	}

	@FXML
	public void addChosenFoodMouse(MouseEvent event) {
		String chosenFood = suggestionsList.getSelectionModel().getSelectedItem();
		if (chosenFood != null) {
			addChosenFood(chosenFood);
		}
	}

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

	public LocalDateTime getMealDateTime() {

		LocalDate currentDate = LocalDate.now();
		if (isDateInFuture(mealdate.getValue(), currentDate)) {
			showAlert("Date invalide", "La date ne peut pas être dans le futur");
			return null;
		}

		LocalDateTime mealdatetime = LocalDateTime.of(mealdate.getValue(), getMealTime());
		return mealdatetime;
	}

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
