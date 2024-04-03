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
import ulb.models.Food;
import ulb.widgets.FoodPopupController;

public class FoodViewController implements ViewController {

	@FXML private TextField searchField;
	@FXML private ListView<String> suggestionsList;
	@FXML private ListView<HBox> chosenFoodView;

	private ArrayList<ArrayList<String>> consumedFoodsList = new ArrayList<>();
	private FoodViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

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

	@FXML
	public void saveConsumedFoods() {
		if (consumedFoodsList.isEmpty()) {
			return;
		}
		this.listener.saveConsumedFoods(consumedFoodsList);
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
		Food selectedFood = listener.getCorrespondingFood(food);
		String value = getUserData(selectedFood);
		int quantity = extractInt(value, selectedFood);
		if (quantity == 0) {
			return;
		}
		int calories = listener.getCaloriesConsumedByGrams(food, quantity);
		HBox box = loadFoodItemBox();
		updateFoodItemBox(box, food, calories, quantity, selectedFood, value);
		chosenFoodView.getItems().add(box);
		consumedFoodsList.add(
				new ArrayList<>(
						List.of(
								selectedFood.getName(),
								Integer.toString(quantity),
								Integer.toString(calories))));
	}

	private String getUserData(Food food) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Custom Input Dialog");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_popup.fxml"));
		VBox box = loadPopupBox(loader);
		FoodPopupController controller = loader.getController();
		controller.setServinglabel(food.getServingQuantity());
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

	private int extractInt(String input, Food food) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			int quantity = Integer.parseInt(matcher.group());
			if (input.contains("portion")) {
				quantity *= food.getServingQuantity_int();
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
								calories, selectedFood.getServingType(), quantity);
		label2.setText(quantityText);
	}

	public void cleanFoodList() {
		chosenFoodView.getItems().clear();
		consumedFoodsList.clear();
	}

	public interface Listener {
		void sendUserSearch(String searchText);

		void returnHome();

		Food getCorrespondingFood(String food);

		int getCaloriesConsumedByGrams(String food, int quantity);

		void saveConsumedFoods(ArrayList<ArrayList<String>> consumedFoodsList);
	}
}
