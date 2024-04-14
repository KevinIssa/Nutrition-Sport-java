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

/**
 * The ActivityCreateController class is responsible for handling the creation of new activities.
 * It implements the AppController interface and the ActivityCreateViewController.Listener interface.
 */
public class ActivityCreateController
		implements AppController, ActivityCreateViewController.Listener {

	private final ActivityCreateController.Listener listener;
	private final ActivityCreateViewController viewController;

	/**
	 * Constructor for the ActivityCreateController class.
	 * @param listener Listener for the ActivityCreateController
	 * @param viewController ViewController for the ActivityCreateController
	 */
	public ActivityCreateController(
			ActivityCreateController.Listener listener,
			ActivityCreateViewController viewController) {
		this.listener = listener;
		this.viewController = viewController;
	}

	/**
	 *Creates a new activity with the given parameters and saves it to the database.
	 * It also updates the view to show the calories consumed by the activity.
	 * @param sport The sport of the activity
	 * @param intensity The intensity of the activity
	 * @param duration The duration of the activity
	 * @param dateTime The date and time of the activity
	 */
	@Override
	public void saveActivity(Sport sport, int intensity, String duration, LocalDateTime dateTime) {
		Activity activity =
				new Activity(
						sport,
						Intensity.fromInt(intensity),
						Duration.ofMinutes(Long.parseLong(duration)),
						dateTime);
		activity.save();
		this.viewController.showCaloriesConsumed(
				activity.getCaloriesBurned(Profile.load().getWeight()));
	}

	/**
	 * This method is used to return to the home screen.
	 * It calls the returnHome method of the listener.
	 */
	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		void returnHome();
	}
}
