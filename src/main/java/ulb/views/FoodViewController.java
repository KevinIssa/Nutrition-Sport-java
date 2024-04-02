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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ulb.models.ConsumedFoodSaver;
import ulb.models.Food;
import ulb.widgets.FoodPopupController;

public class FoodViewController implements ViewController {

	@FXML private TextField searchField;
	@FXML private ListView<String> suggestionsList;
	@FXML private ListView<HBox> chosenFoodView;

	private ArrayList<ArrayList<String>> consumedFoodsList = new ArrayList<>();
	private FoodViewController.Listener listener;

	@FXML
	private void suggestFoods() {
		String searchText = this.searchField.getText();
		this.listener.sendUserSearch(searchText);
	}

	public String getUserInput() {
		return this.searchField.getText();
	}

	/**
	 * Get the quantity of the food we want to add to our meal from the user
	 *
	 * @param food is the Food where we want to get values for
	 * @return the quantity of the food in format:    "quantity g/portion"     example : "50 g" or "2 portion"
	 */
	public String getUserData(Food food) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Custom Input Dialog");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_popup.fxml"));
		VBox box = loadPopupBox(loader);
		FoodPopupController controller = loader.getController();
		controller.setServinglabel(food.getServingQuantity());

		dialog.getDialogPane().setContent(box);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.setResultConverter(buttonType -> processDialogResult(buttonType, controller));

		return dialog.showAndWait().get();
	}

	private VBox loadPopupBox(FXMLLoader loader) {
		try {
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Food_popup file not existing");
		}
	}

	private String processDialogResult(ButtonType buttonType, FoodPopupController controller) {
		if (buttonType == ButtonType.OK) {
			if (controller.getGramme() != 0) {
				return String.valueOf(controller.getGramme()) + " g";
			} else if (controller.getServing() != 0) {
				return String.valueOf(controller.getServing()) + " portion";
			}
		}
		return "0";
	}

	/**
	 * Extract the first piece of int in a string
	 *
	 * @param input is the string were we want to extract the int    input are in format:   "int_value g/portion"
	 * @return a int value
	 */
	public static int extractInt(String input) {
		// Regular expression pattern to match digits
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(input);

		// Find the first match
		if (matcher.find()) {
			// Convert the matched string to integer
			return Integer.parseInt(matcher.group());
		} else {
			throw new IllegalArgumentException("No integer found in the input string");
		}
	}

	public void addChosenFood(String food) {
		Food selectedFood = this.listener.getCorrespondingFood(food);
		String value = getUserData(selectedFood); // ex : "50 g"
		int quantity = extractInt(value); // ex : 50

		if (quantity == 0) {
			return;
		}

		int calories =
			value.contains("g")
				? selectedFood.getCaloriesConsumedByGrams(quantity)
				: selectedFood.getCaloriesConsumedByServing(quantity);

		HBox box = loadFoodItemBox();
		updateFoodItemBox(box, food, calories, quantity, selectedFood, value);
		if (this.chosenFoodView != null) {
			this.chosenFoodView.getItems().add(box);
	}
		this.consumedFoodsList.add(
			new ArrayList<>(
				List.of(
					selectedFood.getName(),
					Integer.toString(quantity),
					Integer.toString(calories))));
	}

	private void removeChosenFood(HBox box) {
		int index = this.chosenFoodView.getItems().indexOf(box);
		this.chosenFoodView.getItems().remove(index);
		this.consumedFoodsList.remove(index);
	}

	private HBox loadFoodItemBox() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_item.fxml"));
		try {
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Food_item file not existing");
		}
	}

	private void updateFoodItemBox(
			HBox box, String food, int calories, int quantity, Food selectedFood, String value) {
		Label label1 = (Label) box.getChildren().get(0);
		label1.setText(food);

		Label label2 = (Label) box.getChildren().get(1);
		String quantityText =
				value.contains("g")
						? String.format(
								"Calories: %d          quantites(g): %d", calories, quantity)
						: String.format(
								"Calories: %d          quantites(%s): %d",
								calories,
								selectedFood.getServingType(),
								quantity * selectedFood.getServingQuantity_int());
		label2.setText(quantityText);
	}

	public void addChosenFoodMouse(MouseEvent event) {
		String chosenFood = this.suggestionsList.getSelectionModel().getSelectedItem();
		if (chosenFood != null) { // prevent the first element is still null
			this.addChosenFood(chosenFood);
		}
	}

	public void addChosenFoodKey(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String chosenFood = this.suggestionsList.getSelectionModel().getSelectedItem();
			if (chosenFood == null) {
				if (!this.suggestionsList.getItems().isEmpty()) {
					chosenFood = this.suggestionsList.getItems().get(0);
				}
			}
			if (chosenFood != null) { // prevent the first element is still null
				this.addChosenFood(chosenFood);
			}
		}
	}

	public void setSuggestions(List<String> foods) {
		this.suggestionsList.getItems().clear();
		for (String food : foods) {
			this.suggestionsList.getItems().add(food);
		}
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		void sendUserSearch(String searchText);

		void returnHome();

		Food getCorrespondingFood(String food);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	public void saveConsumedFoods() {
		ConsumedFoodSaver<ArrayList<String>> saver =
				new ConsumedFoodSaver<>(this.consumedFoodsList);
		saver.save();
		cleanFoodList();
	}

	public void cleanFoodList() {
		chosenFoodView.getItems().clear();
		consumedFoodsList.clear();
	}
}
