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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ulb.models.Activity;
import ulb.models.ConsumedMeal;
import ulb.models.Profile;
import ulb.views.ProfileDeleteConfirmViewController;

public class ProfileDeleteController
		implements AppController, ProfileDeleteConfirmViewController.Listener {

	private final ProfileDeleteController.Listener listener;

	public ProfileDeleteController(ProfileDeleteController.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void deleteProfile() {
		Profile profile = Profile.load();
		profile.delete();
		Activity.clearAllActivities();
		ConsumedMeal.clearAllConsumedMeals();
		deleteProfileImage();
		this.listener.createProfile();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	private void deleteProfileImage() {
		Path fileToDelete = Paths.get(".").resolve("profile.png");
		try {
			if (Files.exists(fileToDelete)) {
				Files.delete(fileToDelete);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public interface Listener {
		void returnHome();

		void createProfile();
	}
}
