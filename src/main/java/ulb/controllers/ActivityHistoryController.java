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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ulb.controllers.dtos.ActivityDTO;
import ulb.models.Activity;
import ulb.models.enums.Sport;
import ulb.views.ActivityHistoryViewController;

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
		File folder = new File("activities");
		List<ActivityDTO> list = new ArrayList<>();
		File[] files = folder.listFiles();
		try {
			Objects.requireNonNull(files);
		} catch (NullPointerException e) {
			return list;
		}
		for (File file : files) {
			Activity activity = Activity.load(file.getPath());
			if (filter == null || activity.getSport() == filter) {
				list.add(new ActivityDTO(activity));
			}
		}
		return list;
	}

	public interface Listener {
		void returnHome();
	}
}
