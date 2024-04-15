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

import java.util.List;
import java.util.stream.Collectors;
import ulb.controllers.dtos.ActivityDTO;
import ulb.models.Activity;
import ulb.models.enums.Sport;
import ulb.views.ActivityHistoryViewController;

/**
 * The ActivityHistoryController class is responsible for handling the history of activities.
 * It implements the AppController interface and the ActivityHistoryViewController.Listener interface.
 */
public class ActivityHistoryController
		implements AppController, ActivityHistoryViewController.Listener {

	private final ActivityHistoryController.Listener listener;

	public ActivityHistoryController(ActivityHistoryController.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public List<ActivityDTO> getActivities(Sport filter) {
		return Activity.loadAll().stream()
				.filter(activity -> filter == null || activity.getSport() == filter)
				.map(ActivityDTO::new)
				.collect(Collectors.toList());
	}

	/**
	 * This is an interface for the Listener within the ActivityHistoryController class.
	 * It is used to define the contract for the Listener, which is expected to be implemented by any class that wants to listen to events from the ActivityHistoryController.
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
