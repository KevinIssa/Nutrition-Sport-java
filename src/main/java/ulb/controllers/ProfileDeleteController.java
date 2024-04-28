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
import ulb.services.ProfileService;
import ulb.views.ProfileDeleteConfirmViewController;

/**
 * This class is a controller for the profile delete view.
 * It is responsible for deleting the profile and all the data associated with it.
 * It implements the AppController interface and the ProfileDeleteConfirmViewController.Listener interface.
 * It has a listener that must implement the ProfileDeleteController.Listener interface.
 */
public class ProfileDeleteController extends AppController
		implements ProfileDeleteConfirmViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(ProfileDeleteController.class);
	private final ProfileService profileService;
	private final ProfileDeleteController.Listener listener;

	public ProfileDeleteController(
			ProfileService profileService, ProfileDeleteController.Listener listener) {
		logger.info("Initializing ProfileDeleteController");
		this.profileService = profileService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing ProfileDeleteController");
		this.loadView("/ulb/views/ProfileDeleteConfirm.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public void deleteProfile() {
		logger.info("Deleting profile");
		this.profileService.deleteProfile();
		// Activity.clearAll();
		// ConsumedMeal.clearAll();
		this.listener.createProfile();
	}

	@Override
	public void returnHome() {
		logger.info("Returning to home view");
		this.listener.returnHome();
	}

	/**
	 * This is an interface for the listener in the ProfileDeleteController class.
	 * It defines two methods that must be implemented by any class that uses this interface.
	 * The methods are returnHome and createProfile.
	 * <p>
	 * The returnHome method is called when the user wants to return to the home view.
	 * It should contain the logic for returning to the home view.
	 * <p>
	 * The createProfile method is called when the user wants to create a new profile.
	 * It should contain the logic for creating a new profile.
	 */
	public interface Listener {

		/**
		 * This method is called when the user wants to return to the home view.
		 * It should contain the logic for returning to the home view.
		 */
		void returnHome();

		/**
		 * This method is called when the user wants to create a new profile.
		 * It should contain the logic for creating a new profile.
		 */
		void createProfile();
	}
}
