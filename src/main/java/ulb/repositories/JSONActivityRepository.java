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
package ulb.repositories;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import ulb.models.Activity;
import ulb.models.CalorieCalculator;

/**
 * This class extends JSONRepository and implements ActivityRepository.
 * It provides methods for saving, loading, deleting activities, and calculating calories burned.
 * It uses JSON files for storing and retrieving activities.
 */
public class JSONActivityRepository extends JSONRepository<Activity> implements ActivityRepository {
	private static final String FOLDER_NAME = "activities";

	/**
	 * Saves the given Activity object to a JSON file.
	 * @param activity The Activity object to be saved.
	 */
	@Override
	public void save(Activity activity) {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			// logger.info("Creating activities folder");
			folder.mkdir();
		}
		String filename =
				FOLDER_NAME
						+ "/"
						+ LocalDateTime.now()
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
						+ ".json";
		try {
			super.save(activity, filename);
		} catch (IOException e) {
			// TODO: Handle exception
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Loads all Activity objects from the JSON files.
	 * @return A list of all loaded Activity objects.
	 */
	@Override
	public List<Activity> loadAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		List<Activity> activities = new ArrayList<>();
		if (files != null) {
			// logger.info("Loading all activities");
			for (File file : files) {
				if (!file.isDirectory()) {
					try {
						activities.add(super.load(file.getPath()));
					} catch (IOException e) {
						// If the file is corrupted, we just skip it
						// logger.error("Failed to load activity", e);
					}
				}
			}
		}
		return activities;
	}

	/**
	 * Deletes the given Activity object from the JSON files.
	 * @param activity The Activity object to be deleted.
	 */
	@Override
	public void delete(Activity activity) {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			// logger.info("Deleting all activities");
			for (File file : files) {
				Activity loadedActivity = null;
				try {
					loadedActivity = load(file.getPath());
				} catch (IOException e) {
					// If the file is corrupted, we just skip it
					// logger.error("Failed to load activity", e);
					continue;
				}
				if (loadedActivity.equals(activity)) {
					file.delete();
				}
			}
		}
	}

	/**
	 * Deletes all Activity objects from the JSON files.
	 */
	@Override
	public void deleteAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			// logger.info("Deleting all activities");
			for (File file : files) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * Calculates the calories burned for the given Activity object and weight.
	 * @param activity The Activity object for which the calories burned is to be calculated.
	 * @param weight The weight of the user.
	 * @return The number of calories burned.
	 */
	@Override
	public int calculateCaloriesBurned(Activity activity, float weight) {
		return (int) CalorieCalculator.compute(activity, weight);
	}

	/**
	 * Returns the Class object for type Activity.
	 * It is used by the Jackson library to know the type of the objects to deserialize.
	 * @return the Class object for type Activity
	 */
	@Override
	protected Class<Activity> getObjectType() {
		return Activity.class;
	}
}
