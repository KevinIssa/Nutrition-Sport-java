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
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ActivityDTO;
import ulb.enums.Sport;
import ulb.widgets.HistoryBox;

/**
 * This class is a controller for the ActivityHistory view. It implements the ViewController interface.
 * It is responsible for handling the user interactions with the activity history view in the application.
 */
public class ActivityHistoryViewController implements ViewController {
	private static final Logger logger =
			LoggerFactory.getLogger(ActivityHistoryViewController.class);
	private ActivityHistoryViewController.Listener
			listener; // Listener interface for communication with the controller
	@FXML private ListView<HistoryBox> historyList; // ListView to display activity history
	@FXML private HBox filters;
	private Sport filteredSport = null;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void addActivity(ActivityDTO activity) {
		Button deleteActivityButton = new Button();
		Button editActivityButton = new Button();

		HistoryBox activityHistoryBox =
				new HistoryBox(activity, deleteActivityButton, editActivityButton);

		deleteActivityButton.setOnAction(e -> deleteActivityInHistory(activityHistoryBox));
		editActivityButton.setOnAction(e -> editActivityInHistory(activityHistoryBox));

		historyList.getItems().add(activityHistoryBox);
	}

	/**
	 * This method sets the activities to be displayed in the activity history list.
	 * It first clears the current items in the list, then retrieves the activities from the listener,
	 * filtering them by the currently selected sport if any. Each activity is then added to the list.
	 */
	public void setActivities() {
		historyList.getItems().clear();
		listener.getActivities(filteredSport).forEach(this::addActivity);
	}

	private void deleteActivityInHistory(HistoryBox activityBox) {
		this.listener.deleteActivity(activityBox.getActivity());
		historyList.getItems().remove(activityBox);
	}

	private void editActivityInHistory(HistoryBox activityBox) {
		deleteActivityInHistory(activityBox);
		this.listener.editActivity(activityBox.getActivity());
		this.setActivities();
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
		this.setActivities(); // Add activities when listener is set
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void toggleShowFilter() {
		filters.setVisible(!filters.isVisible());
		if (!filters.isVisible()) {
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

	public void loadAddActivity() {
		this.listener.addActivity();
	}

	// Listener interface for communication with the controller
	public interface Listener {

		List<ActivityDTO> getActivities(Sport filter);

		void deleteActivity(ActivityDTO activityDTO);

		void editActivity(ActivityDTO activityDTO);

		void returnHome();

		void addActivity();
	}
}
