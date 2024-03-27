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
import java.util.List;
import java.util.Objects;
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

	@FXML private ListView<HBox> chosenFood;

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
    public String getUserData(Food food){
		// Create a TextInputDialog
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Custom Input Dialog");
        // load the window we want to use as popup
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_popup.fxml"));
		VBox box = null;
		try {
			box = loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Food_popup file not existing");
		}
		// set the Textinputdialog content as our Food_popup
		FoodPopupController controller = loader.getController();
		controller.setServinglabel(food.getServingQuantity());
		dialog.getDialogPane().setContent(box);
		// Add OK and Cancel buttons to the dialog
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		// Convert result to string when OK is clicked if return 0 then it mean not reply
		dialog.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				if (controller.getGramme() != 0){
					// return the calories by grams
					return String.valueOf(controller.getGramme()) + " g";
				}else if (controller.getServing() != 0){
					// return the calories by litres
					return String.valueOf(controller.getServing()) + " portion";
				}
			}else if (buttonType == ButtonType.CANCEL){
				return "0";
			}
            return "0";
        });
		// return the answear generated based on player action in the popup
		return dialog.showAndWait().get();
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
	public void addChosenFood(String food){
		// get the correspond Food with name food
		Food selectedfood = this.listener.getCorrespondingFood(food);
		// get player input in format: "int_value g/portion"
		String value = getUserData(selectedfood);
		// extract int_value from "int_value g/portion"
		int quantity = extractInt(value);
		// if the value is none which mean the player didnt answear so we cancel this attempt to add a food to the meal
		if (quantity == 0){
			return;
		}
		// we calculate the calories depending of the way we input value, directly by gramme or by serving
		int calories;
		if (value.contains("g")){
			calories = selectedfood.getCaloriesConsumedByGrams(quantity);
		}else{
			calories = selectedfood.getCaloriesConsumedByServing(quantity);
		}
		// load the HBox used to contains the data needed to be display to the user
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/widgets/Food_item.fxml"));
		HBox box = null;
		try {
			box = loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Food_item file not existing");
		}
		// add the name of the food added
		Label label1 = (Label) box.getChildren().get(0);
		label1.setText(food);
		// add the number of calories the food will add to the meal and add information about how much of this food did we add
		Label label2 = (Label) box.getChildren().get(1);
		if (value.contains("g")){
			label2.setText(String.format("Calories: %d          quantites(g): %d", calories, quantity));
		}else{
			if (selectedfood.getServingType() == "g"){
				label2.setText(String.format("Calories: %d          quantites(g): %d", calories, quantity*selectedfood.getServingQuantity_int()));
			}else{
				label2.setText(String.format("Calories: %d          quantites(ml): %d", calories, quantity*selectedfood.getServingQuantity_int()));
			}
		}
		// add the Hbox with the information about the food added to the list of food added
		this.chosenFood.getItems().add(box);
	}

	public void addChosenFoodMouse(MouseEvent event) {
		String chosenFood = this.suggestionsList.getSelectionModel().getSelectedItem();
		if (chosenFood != null) {// prevent the first element is still null
			this.addChosenFood(chosenFood);
		}
	}

	public void addChosenFoodKey(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String chosenFood = this.suggestionsList.getSelectionModel().getSelectedItem();
			if (chosenFood == null) {
				if (!this.suggestionsList.getItems().isEmpty()){
					chosenFood = this.suggestionsList.getItems().get(0);
				}
			}
			if (chosenFood != null) {// prevent the first element is still null
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
}
