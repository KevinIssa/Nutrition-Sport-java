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
package ulb.widgets;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ulb.views.ViewController;

/**
 * The FoodPopupController class is a controller for the food popup view.
 * It implements the ViewController interface and overrides its methods.
 * It uses JavaFX annotations to link the controller to the view's components.
 */
public class FoodPopupController implements ViewController {
	@FXML TextField gramme;
	@FXML TextField serving;
	@FXML Label servinglabel;

	public int getGramme() {
		return handleInput(gramme, "grammes", 4000);
	}

	public int getServing() {
		return handleInput(serving, "portions", 100);
	}

	/**
	 * This method is used to handle an input.
	 * It checks if the input field is not disabled, validates the input, and returns the value if it is valid.
	 * If the input is invalid or excessive, it shows an alert and returns 0.
	 *
	 * @param inputField The TextField for the input.
	 * @param unit The unit of the input.
	 * @param max The maximum allowed value of the input.
	 * @return The input value, or 0 if the input is invalid or excessive.
	 */
	private int handleInput(TextField inputField, String unit, int max) {
		if (!inputField.isDisable()) {
			try {
				int value = Integer.parseInt(inputField.getText());
				if (value > max) {
					showAlert(
							"Quantité excessive",
							"Le maximum autorisé est de " + max + " " + unit + ".");
					return 0;
				}
				return value;
			} catch (NumberFormatException e) {
				showAlert(
						"Valeur invalide",
						"Veuillez entrer une valeur valide pour les " + unit + ".");
				return 0;
			}
		}
		return 0;
	}

	public void setServinglabel(String label) {
		this.servinglabel.setText(label);
	}

	/**
	 * Used to handle the textbox event.
	 * It disables the gramme input if the serving input is not empty, and vice versa.
	 *
	 * @param event The Event object.
	 */
	@FXML
	void textboxHandler(Event event) {
		if (!Objects.equals(serving.getText(), "")) {
			gramme.setDisable(true);
		} else {
			gramme.setDisable(false);
		}
		if (!Objects.equals(gramme.getText(), "")) {
			serving.setDisable(true);
		} else {
			serving.setDisable(false);
		}
	}

	@Override
	public void setListener(Object listener) {}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}
}
