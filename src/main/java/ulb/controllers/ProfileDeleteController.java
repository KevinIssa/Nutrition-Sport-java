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

	/**
	 * Delete the profile.
	 * It deletes the profile, clears all activities and consumed meals.
	 * Then, it calls the createProfile method of the listener.
	 */
	@Override
	public void deleteProfile() {
		Profile.delete();
		Activity.clearAll();
		ConsumedMeal.clearAllConsumedMeals();
		this.listener.createProfile();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		void returnHome();

		void createProfile();
	}
}
