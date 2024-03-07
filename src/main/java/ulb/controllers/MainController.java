package ulb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ulb.models.ProfileReader;
import ulb.models.ProfileReaderAndSaver;


// controller for the main menu
public class MainController extends AbstractController {

	@FXML
	public void consultProfile() {
		this.getModel().getController().switchFXML("/ulb/views/profile.fxml",this.getModel(), new ProfileReader(this.getModel().getProfile()));
	}

	@FXML
	public void encodeActivity() {
		this.getModel().getController().switchFXML("/ulb/views/add_activity.fxml", this.getModel());
	}

	@FXML
	public void computeCalories() {
		showAlert("Calcul du nombre de calories");
	}

	@FXML
	public void modifyProfile(){
		this.getModel().getController().switchFXML("/ulb/views/profile_modification.fxml", this.getModel(), new ProfileReaderAndSaver(this.getModel().getProfile()));
	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Action sélectionnée");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
