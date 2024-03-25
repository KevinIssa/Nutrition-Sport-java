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
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FoodViewController implements ViewController {

	@FXML private TextField searchField;

	@FXML private ListView<String> suggestionsList;

	@FXML private ListView<String> UiChosenFood;

	private FoodViewController.Listener listener;

	@FXML
	private void suggestFoods() {
		String searchText = this.searchField.getText();
		this.listener.sendUserSearch(searchText);
	}
	
	public String getUserInput() {
		return this.searchField.getText();
	}

	
	public void addChosenFood(String chosenFood) {
		this.UiChosenFood.getItems().add(chosenFood);
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
		void addChosenFood(String userInput);
		void returnHome();
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
