package ulb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {
	@FXML
	private Button profileButton;

	@FXML
	private Button activityButton;

	@FXML
	private Button addActivityButton;

	@FXML
	private void initialize() {
		// Gérer les événements des boutons ici
		profileButton.setOnAction(event -> handleProfileButtonClick());
		activityButton.setOnAction(event -> handleActivityButtonClick());
		addActivityButton.setOnAction(event -> handleAddActivityButtonClick());
	}

	private void handleProfileButtonClick() {
		// Action à effectuer lorsque le bouton de profil est cliqué
		System.out.println("Profil cliqué");
	}

	private void handleActivityButtonClick() {
		// Action à effectuer lorsque le bouton d'activité est cliqué
		System.out.println("Activité cliquée");
	}

	private void handleAddActivityButtonClick() {
		// Action à effectuer lorsque le bouton d'ajout d'activité est cliqué
		System.out.println("Ajouter une activité cliquée");
	}
}
