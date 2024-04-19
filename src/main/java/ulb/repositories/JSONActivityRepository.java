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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import ulb.models.Activity;
import ulb.models.CalorieCalculator;

/**
 * This class implements the ActivityRepository interface and provides methods for saving, loading, deleting activities,
 * and calculating calories burned. It uses JSON files for storing and retrieving activities.
 */
public class JSONActivityRepository implements ActivityRepository {
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
				STR."\{
						FOLDER_NAME}/\{
						LocalDateTime.now()
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))}.json";
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			// logger.info("Saving activity:{} to {} ", this, filename);
			mapper.writeValue(new File(filename), activity);
		} catch (IOException e) {
			// logger.error("Error saving activity to file", e);
		}
	}

	/**
	 * Loads an Activity object from a given JSON file.
	 * @param file The JSON file from which the Activity object is to be loaded.
	 * @return The loaded Activity object.
	 */
	private Activity load(File file) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			// logger.info("Loading activity from file: {}", file);
			return mapper.readValue(file, Activity.class);
		} catch (IOException e) {
			// logger.error("Error loading activity from file", e);
			return null;
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
					activities.add(load(file));
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
				Activity loadedActivity = load(file);
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
}
