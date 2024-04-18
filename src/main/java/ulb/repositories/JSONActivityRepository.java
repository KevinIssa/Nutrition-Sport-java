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

public class JSONActivityRepository implements ActivityRepository {
	private static final String FOLDER_NAME = "activities";

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

	@Override
	public Activity load() {
		File file = new File(filename);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			// logger.info("Loading activity from file: {}", filename);
			return mapper.readValue(file, Activity.class);
		} catch (IOException e) {
			// logger.warn("No activity found");
			return null;
		}
	}

	@Override
	public List<Activity> loadAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		List<Activity> activities = new ArrayList<>();
		if (files != null) {
			// logger.info("Loading all activities");
			for (File file : files) {
				if (!file.isDirectory()) {
					activities.add(load());
				}
			}
		}
		return activities;
	}

	@Override
	public void delete(Activity activity) {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			// logger.info("Deleting all activities");
			for (File file : files) {
				Activity loadedActivity = load();
				if (loadedActivity.equals(activity)) {
					file.delete();
				}
			}
		}
	}

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
}
