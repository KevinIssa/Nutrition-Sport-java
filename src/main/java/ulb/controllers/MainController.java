package ulb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

// controller for the main menu
public class MainController extends Abstract_Controller{

	@FXML
	public void consulterProfil() {
		this.modele.getController().switchFXML("/ulb/views/profil_creation.fxml",this.modele);
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
