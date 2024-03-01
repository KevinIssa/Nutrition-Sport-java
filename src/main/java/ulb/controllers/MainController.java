package ulb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class MainController {

	@FXML
	public void consulterProfil() {
		showAlert("Consultation du profil");
	}

	@FXML
	public void encoderActivite() {
		showAlert("Encodage d'activité");
	}

	@FXML
	public void calculerCalories() {
		showAlert("Calcul du nombre de calories");
	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Action sélectionnée");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
