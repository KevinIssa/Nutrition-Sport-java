package ulb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ulb.models.Model;
import ulb.models.ProfileReader;

// controller for the main menu
public class MainController extends AbstractController {

	@FXML
	public void consulterProfil() {
		this.getModel().getController().switchFXML("/ulb/views/profile.fxml",this.getModel(), new ProfileReader(this.getModel().getProfil()));
	}

	@FXML
	public void encoderActivite() {
		this.getModele().getController().switchFXML("/ulb/views/add_activity.fxml", this.getModele());
	}

	@FXML
	public void calculerCalories() {
		showAlert("Calcul du nombre de calories");
	}

	@FXML
	public void modifierProfil(){

	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Action sélectionnée");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
