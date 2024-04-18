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
package ulb.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javafx.stage.Stage;
import ulb.dtos.ActivityDTO;
import ulb.enums.Sport;
import ulb.services.ActivityService;
import ulb.views.ActivityHistoryViewController;

/**
 * The ActivityHistoryController class is responsible for managing the interactions between the ActivityHistoryViewController and the model classes related to activities.
 * It implements the AppController interface and the Listener interface from the ActivityHistoryViewController.
 * This class handles the loading of activities from the database and the return to the home screen of the application.
 */
public class ActivityHistoryController extends AppController
		implements ActivityHistoryViewController.Listener {
	private final ActivityService activityService;
	private final ActivityHistoryController.Listener listener;
	public static final String FOLDER_NAME = "activities";

	public ActivityHistoryController(
			ActivityService activityService, ActivityHistoryController.Listener listener) {
		this.activityService = activityService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		this.loadView("/ulb/views/ActivityHistory.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public List<ActivityDTO> getActivities(Sport filter) {
		return this.activityService.loadActivities().stream()
				.filter(activity -> filter == null || activity.sport() == filter)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteActivity(ActivityDTO activityDTO) {
		this.activityService.deleteActivity(activityDTO);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * This is an interface for the Listener within the ActivityHistoryController class.
	 * It is used to define the contract for the Listener, which is expected to be implemented by any class that wants to listen to events from the ActivityHistoryController.
	 * <p>
	 * Currently, it has a single method, returnHome, which is expected to be called when the user wants to return to the home screen of the application.
	 */
	public interface Listener {

		/**
		 * This method is called when the user wants to return to the home screen of the application.
		 * The implementing class should define the behavior that occurs when this event happens.
		 */
		void returnHome();
	}
}
