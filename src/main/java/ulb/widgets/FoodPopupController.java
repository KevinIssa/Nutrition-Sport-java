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

import java.util.Objects;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FoodPopupController {
	@FXML TextField gramme;
	@FXML TextField serving;
	@FXML Label servinglabel;

	public int getGramme() {
		if (!gramme.isDisable()) {
			return Integer.parseInt(gramme.getText());
		}
		return 0;
	}

	public int getServing() {
		if (!serving.isDisable()) {
			return Integer.parseInt(serving.getText());
		}
		return 0;
	}

	public void setServinglabel(String label) {
		this.servinglabel.setText(label);
	}

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
}
