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
import ulb.dtos.ProfileDTO;
import ulb.services.ProfileService;
import ulb.views.ProfileViewController;

/**
 * This class is the controller for the profile screen of the application.
 * It is responsible for handling the logic of the profile screen, such as saving the user's profile information.
 * It also listens to events from the ProfileViewController and notifies the listener when the user wants to delete their profile or return to the home screen.
 */
public class ProfileController extends AppController implements ProfileViewController.Listener {
	private final ProfileService profileService;
	private final ProfileController.Listener listener;

	public ProfileController(ProfileService profileService, ProfileController.Listener listener) {
		this.profileService = profileService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		this.loadView("/ulb/views/Profile.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public void updateProfile(ProfileDTO profileDTO) {
		try {
			this.profileService.updateProfile(profileDTO);
		} catch (Exception e) {
			// TODO: Handle exception
			e.printStackTrace();
		}
	}

	@Override
	public ProfileDTO getProfile() {
		return this.profileService.loadProfile();
	}

	@Override
	public String getProfileImagePath() {
		return profileService.getProfileImagePath();
	}

	@Override
	public void deleteProfileView() {
		this.listener.deleteProfile();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * This is an interface for the listener in the ProfileController class.
	 * It defines two methods that must be implemented by any class that uses this interface.
	 * The methods are deleteProfile and returnHome.
	 * <p>
	 * The deleteProfile method is called when the user wants to delete their profile.
	 * The returnHome method is called when the user wants to return to the home view.
	 */
	public interface Listener {
		/**
		 * This method is called when the user wants to delete their profile.
		 * It should contain the logic for deleting the user's profile.
		 */
		void deleteProfile();

		/**
		 * This method is called when the user wants to return to the home view.
		 * It should contain the logic for returning to the home view.
		 */
		void returnHome();
	}
}
