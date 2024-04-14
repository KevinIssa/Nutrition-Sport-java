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
import ulb.models.Height;
import ulb.models.Profile;
import ulb.models.Weight;
import ulb.models.enums.Sex;
import ulb.views.ProfileCreateViewController;

public class ProfileCreateController
		implements AppController, ProfileCreateViewController.Listener {

	private final ProfileCreateController.Listener listener;

	public ProfileCreateController(ProfileCreateController.Listener listener) {
		this.listener = listener;
	}

	/**
	 * This method is used to load the create activity view.
	 * It creates a new Stage object for the popup stage.
	 * Then, it loads the activity create view with the popup stage and assigns the returned ViewController to a new ActivityCreateViewController object.
	 * It sets the listener of the ViewController with a new instance of the ActivityCreateController.
	 * The listener is set with a lambda function that loads the menu view and closes the popup stage when called.
	 * Then, it sets the modality of the popup stage to APPLICATION_MODAL, which means that while the popup stage is showing, it blocks user interaction with all other stages of the application.
	 * Finally, it shows the popup stage and waits for it to be hidden (closed) before returning to the caller.
	 */
	public void saveProfile(
			String firstName,
			String lastName,
			String sex,
			LocalDate birthDate,
			float height,
			float weight) {
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
	public void saveProfileImage(String imagePath) {
		Profile.saveImage(imagePath);
	}

	public interface Listener {

		void returnHome();
	}
}
