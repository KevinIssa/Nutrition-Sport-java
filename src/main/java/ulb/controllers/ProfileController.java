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
import ulb.views.ProfileViewController;

public class ProfileController implements AppController, ProfileViewController.Listener {

	private final Profile profile = Profile.load();
	private final ProfileController.Listener listener;

	public ProfileController(ProfileController.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void saveProfile(
			String firstName,
			String lastName,
			String sex,
			java.time.LocalDate birthDate,
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
	public void deleteProfileView() {
		this.listener.deleteProfile();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public String getFirstName() {
		return profile.getFirstName();
	}

	@Override
	public String getLastName() {
		return profile.getLastName();
	}

	@Override
	public String getSex() {
		return profile.getSex().toString();
	}

	@Override
	public LocalDate getBirthDate() {
		return profile.getBirthDate();
	}

	@Override
	public float getHeight() {
		return profile.getHeight();
	}

	@Override
	public float getWeight() {
		return profile.getWeight();
	}

	@Override
	public void saveProfileImage(String imagePath) throws ImageException {
		Profile.saveImage(imagePath);
	}

	@Override
	public String getProfileImagePath() {
		return Profile.IMAGE_PATH;
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
