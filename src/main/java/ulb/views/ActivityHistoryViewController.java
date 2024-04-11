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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import ulb.controllers.dtos.ActivityDTO;
import ulb.models.enums.Sport;

public class ActivityHistoryViewController implements ViewController {
	private ActivityHistoryViewController.Listener
			listener; // Listener interface for communication with the controller
	@FXML private ListView<HistoryBox> historyList; // ListView to display activity history
	@FXML private AnchorPane filterPane;
	private boolean isFilterVisible = false;
	private Sport filteredSport = null;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void addActivity(ActivityDTO activity) {
		Button deleteActivityButton = new Button();
		deleteActivityButton.setOnAction(e -> deleteActivityInHistory(activity));
		HistoryBox activityHistoryBox = new HistoryBox(activity, deleteActivityButton);

		historyList.getItems().add(activityHistoryBox);
	}

	// Add all activities to the activity history list
	public void setActivities() {
		historyList.getItems().clear();
		for (ActivityDTO activity : listener.getActivities(filteredSport)) {
			this.addActivity(activity);
		}
	}

	private void deleteActivityInHistory(ActivityDTO activity) {
		// à connecter avec le controller et la modèle
		System.out.println("C pas fait ");
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
		this.setActivities(); // Add activities when listener is set
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void toggleShowFilter() {
		if (isFilterVisible) {
			filterPane.setVisible(false);
			isFilterVisible = false;
		} else {
			filterPane.setVisible(true);
			isFilterVisible = true;
			filteredSport = null;
			setActivities();
		}
	}

	public void filterRunning() {
		filteredSport = Sport.RUNNING;
		this.setActivities();
	}

	public void filterWalking() {
		filteredSport = Sport.WALKING;
		this.setActivities();
	}

	public void filterBiking() {
		filteredSport = Sport.BIKING;
		this.setActivities();
	}

	public void filterSwimming() {
		filteredSport = Sport.SWIMMING;
		this.setActivities();
	}

	public void filterVolleyball() {
		filteredSport = Sport.VOLLEYBALL;
		this.setActivities();
	}

	// Listener interface for communication with the controller
	public interface Listener {

		List<ActivityDTO> getActivities(Sport filter); // Load activity from file

		void returnHome(); // Return to the home view
	}
}
