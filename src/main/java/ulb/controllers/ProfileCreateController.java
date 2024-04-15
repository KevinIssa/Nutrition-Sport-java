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

import java.time.LocalDate;
import ulb.exceptions.ImageException;
import ulb.models.Height;
import ulb.models.Profile;
import ulb.models.Weight;
import ulb.models.enums.Sex;
import ulb.views.ProfileCreateViewController;

/**
 * This class is the controller for the profile creation screen of the application.
 * It is responsible for handling the logic of the profile creation screen, such as saving the user's profile information.
 * It also listens to events from the ProfileCreateViewController and notifies the listener when the user wants to return to the home screen.
 */
public class ProfileCreateController
		implements AppController, ProfileCreateViewController.Listener {

	private final ProfileCreateController.Listener listener;

	public ProfileCreateController(ProfileCreateController.Listener listener) {
		this.listener = listener;
	}

	public void saveProfile(
			String firstName,
			String lastName,
			String sex,
			LocalDate birthDate,
			float height,
			float weight)
			throws IllegalArgumentException {
		Profile profile =
				new Profile(
						firstName,
						lastName,
						Sex.fromString(sex),
						new Weight(weight),
						new Height(height),
						birthDate);
		profile.save();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void saveProfileImage(String imagePath) throws ImageException {
		Profile.saveImage(imagePath);
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
