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

import java.time.Duration;
import java.time.LocalDateTime;
import ulb.models.Activity;
import ulb.models.Profile;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;
import ulb.views.ActivityCreateViewController;

public class ActivityCreateController
		implements AppController, ActivityCreateViewController.Listener {

	private final ActivityCreateController.Listener listener;

	public ActivityCreateController(ActivityCreateController.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void saveActivity(Sport sport, int intensity, String duration, LocalDateTime dateTime) {
		Activity activity =
				new Activity(
						sport,
						Intensity.fromInt(intensity),
						Duration.ofMinutes(Long.parseLong(duration)),
						dateTime);
		activity.save();
		ActivityCreateViewController.showAlert(
				activity.getCaloriesBurned(Profile.load().getWeight()));
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		void returnHome();
	}
}
