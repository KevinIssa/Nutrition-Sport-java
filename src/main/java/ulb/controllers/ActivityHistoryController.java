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

	/**
	 * This method is used to get a list of activities.
	 * It filters the activities based on the provided sport filter.
	 * If the filter is null, it returns all activities.
	 * @param filter The sport filter. If null, all activities are returned.
	 * @return A list of ActivityDTO objects representing the filtered activities.
	 */
	@Override
	public List<ActivityDTO> getActivities(Sport filter) {
		return Activity.loadAll().stream()
				.filter(activity -> filter == null || activity.getSport() == filter)
				.map(ActivityDTO::new)
				.collect(Collectors.toList());
	}

	public interface Listener {
		void returnHome();
	}
}
