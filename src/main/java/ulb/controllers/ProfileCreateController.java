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
import ulb.dtos.ProfileDTO;
import ulb.exceptions.IllegalImageFormatException;
import ulb.exceptions.InvalidImageException;
import ulb.exceptions.ProfileParameterException;
import ulb.exceptions.ValueObjectException;
import ulb.services.ProfileService;
import ulb.views.ProfileCreateViewController;

/**
 * This class is the controller for the profile creation screen of the application.
 * It is responsible for handling the logic of the profile creation screen, such as saving the user's profile information.
 * It also listens to events from the ProfileCreateViewController and notifies the listener when the user wants to return to the home screen.
 */
public class ProfileCreateController extends AppController
		implements ProfileCreateViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(ProfileCreateController.class);
	private final ProfileService profileService;
	private final ProfileCreateController.Listener listener;

	public ProfileCreateController(
			ProfileService profileService, ProfileCreateController.Listener listener) {
		logger.info("Initializing ProfileCreateController");
		this.profileService = profileService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing ProfileCreateView");
		this.loadView("/ulb/views/ProfileCreate.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public void saveProfile(ProfileDTO profileDTO) throws ProfileParameterException {
		try {
			logger.info("Saving profile {}", profileDTO);
			this.profileService.saveProfile(profileDTO);
		} catch (ValueObjectException | IllegalImageFormatException | InvalidImageException e) {
			throw new ProfileParameterException(e.getMessage(), e);
		}
	}

	@Override
	public void returnHome() {
		logger.info("Returning to home screen");
		this.listener.returnHome();
	}

	/**
	 * This is an interface for the Listener within the ProfileCreateController class.
	 * It is used to define the contract for the Listener, which is expected to be implemented by any class that wants to listen to events from the ProfileCreateController.
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
