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
package ulb.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

/**
 * Represents an activity performed by a user.
 */
public class Activity implements JsonSerializable {

	public static final String FOLDER_NAME = "activities";

	private Sport sport;
	private Intensity intensity = Intensity.MODERATE;
	private Duration duration;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss")
	private LocalDateTime date;

	/**
	 * Default constructor for Activity class.
	 */
	public Activity() {}

	/**
	 * Parameterized constructor for Activity class.
	 *
	 * @param sport     The sport of the activity.
	 * @param intensity The intensity of the activity.
	 * @param duration  The duration of the activity.
	 * @param date      The date and time when the activity was performed.
	 */
	public Activity(Sport sport, Intensity intensity, Duration duration, LocalDateTime date) {
		this.sport = sport;
		this.intensity = intensity;
		this.duration = duration;
		this.date = date;
	}

	/**
	 * Checks if this activity is equal to another object.
	 *
	 * @param obj The object to compare with this activity.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Activity activity = (Activity) obj;
		return sport == activity.sport
				&& intensity == activity.intensity
				&& duration.equals(activity.duration)
				&& date.equals(activity.date);
	}

	/**
	 * Saves the activity data to a JSON file.
	 */
	public void save() {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			folder.mkdir();
		}
		String filename =
				FOLDER_NAME
						+ "/"
						+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
						+ ".json";
		saveToFile(filename);
	}

	/**
	 * Clears all saved activity data.
	 */
	public static void clearAllActivities() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * Loads an activity from a JSON file.
	 *
	 * @param filename The name of the file containing activity data.
	 * @return The loaded activity.
	 */
	public static Activity load(String filename) {
		return (Activity) new Activity().loadFromFile(filename);
	}

	// Getters and setters for class attributes.

	/**
	 * Gets the sport of the activity.
	 *
	 * @return The sport of the activity.
	 */
	public Sport getSport() {
		return sport;
	}

	/**
	 * Sets the sport of the activity.
	 *
	 * @param sport The sport of the activity.
	 */
	public void setSport(Sport sport) {
		this.sport = sport;
	}

	/**
	 * Gets the intensity of the activity.
	 *
	 * @return The intensity of the activity.
	 */
	public Intensity getIntensity() {
		return intensity;
	}

	/**
	 * Sets the intensity of the activity.
	 *
	 * @param intensity The intensity of the activity.
	 */
	public void setIntensity(Intensity intensity) {
		this.intensity = intensity;
	}

	/**
	 * Gets the duration of the activity.
	 *
	 * @return The duration of the activity.
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the activity.
	 *
	 * @param duration The duration of the activity.
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	/**
	 * Gets the date and time when the activity was performed.
	 *
	 * @return The date and time of the activity.
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Sets the date and time when the activity was performed.
	 *
	 * @param date The date and time of the activity.
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	// Utility methods.

	/**
	 * Gets the duration of the activity in minutes.
	 *
	 * @return The duration of the activity in minutes.
	 */
	@JsonIgnore
	public int getDurationInMinutes() {
		return (int) duration.toMinutes();
	}

	/**
	 * Calculates the calories burned during the activity based on user weight.
	 *
	 * @param weight The weight of the user.
	 * @return The calories burned during the activity.
	 */
	public int getCaloriesBurned(float weight) {
		return (int) CalorieCalculator.compute(this, weight);
	}

	/**
	 * Converts the activity duration to a string format.
	 *
	 * @param duration The duration of the activity.
	 * @return The duration of the activity as a string (e.g., "2h30m").
	 */
	public String durationToString(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.minusHours(hours).toMinutes();
		return hours + "h" + minutes + "m";
	}

	/**
	 * Converts the date to a formatted string.
	 *
	 * @param date The date to be formatted.
	 * @return The formatted date string.
	 */
	public String changeDateFormat(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
		return date.format(formatter);
	}

	/**
	 * Saves the activity data to a JSON file.
	 *
	 * @param filename The name of the file to save the activity data.
	 */
	@Override
	public void saveToFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(filename), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the activity data from a JSON file.
	 *
	 * @param filename The name of the file containing the activity data.
	 * @return The loaded Activity object.
	 */
	@Override
	public JsonSerializable loadFromFile(String filename) {
		File file = new File(filename);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(file, Activity.class);
		} catch (IOException e) {
			System.out.println("No activity found");
			return null;
		}
	}
}
