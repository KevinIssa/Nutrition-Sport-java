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

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ActivityDTO;
import ulb.services.ActivityService;
import ulb.services.ProfileService;
import ulb.views.ActivityCreateViewController;

/**
 * The ActivityCreateController class is responsible for managing the interactions between the ActivityCreateViewController and the model classes related to activities.
 * It implements the AppController interface and the Listener interface from the ActivityCreateViewController.
 * This class handles the saving of activities, the calculation of calories burned by an activity, and the return to the home screen of the application.
 */
public class ActivityCreateController extends AppController
		implements ActivityCreateViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(ActivityCreateController.class);
	private final ActivityService activityService;
	private final ProfileService profileService;
	private final ActivityCreateController.Listener listener;

	/**
	 * Constructor for the ActivityCreateController class.
	 *
	 * @param listener Listener for the ActivityCreateController
	 */
	public ActivityCreateController(
			ActivityService activityService, ProfileService profileService, Listener listener) {
		logger.info("Initializing ActivityCreateController");
		this.activityService = activityService;
		this.profileService = profileService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing ActivityCreateView");
		this.loadView("/ulb/views/ActivityCreate.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public int calculateCalorie(ActivityDTO activityDTO) {
		return this.activityService.calculateCaloriesBurnedDuringActivity(
				activityDTO, this.profileService.getProfileWeight());
	}

	@Override
	public void saveActivity(ActivityDTO activityDTO) {
		int burnedCalories = calculateCalorie(activityDTO);
		activityDTO = new ActivityDTO(activityDTO, burnedCalories);
		logger.info("Saving activity {}", activityDTO);
		this.activityService.saveActivity(activityDTO);
		((ActivityCreateViewController) this.viewController)
				.showCaloriesConsumed(activityDTO.burnedCalories());
	}

	@Override
	public void returnHome() {
		logger.info("Closing the popup");
		this.listener.returnHome();
	}

	@Override
	public void goToActivityHistory() {
		logger.info("Going to activity history");
		this.listener.goToActivityHistory();
	}

	/**
	 * This is an interface for the Listener within the ActivityCreateController class.
	 * It is used to define the contract for the Listener, which is expected to be implemented by any class that wants to listen to events from the ActivityCreateController.
	 * <p>
	 * Currently, it has a single method, returnHome, which is expected to be called when the user wants to return to the home screen of the application.
	 */
	public interface Listener {

		/**
		 * This method is called when the user wants to return to the home screen of the application.
		 * The implementing class should define the behavior that occurs when this event happens.
		 */
		void returnHome();

		/**
		 * This method is called when the user wants to navigate to the activity history screen of the application.
		 * The implementing class should define the behavior that occurs when this event happens.
		 */
		void goToActivityHistory();
	}
}
