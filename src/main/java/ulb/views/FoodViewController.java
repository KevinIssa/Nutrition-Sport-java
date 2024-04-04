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
	private Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	@FXML
	private void suggestFoods() {
		listener.sendUserSearch(searchField.getText());
	}

	public void setSuggestions(List<String> foods) {
		suggestionsList.getItems().setAll(foods);
	}

	public void returnHome() {
		listener.returnHome();
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	public interface Listener {
		void sendUserSearch(String searchText);

		void returnHome();
	}
}
