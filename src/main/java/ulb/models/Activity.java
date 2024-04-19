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
import java.time.Duration;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.enums.Intensity;
import ulb.enums.Sport;

/**
 * Represents an activity performed by a user.
 */
public class Activity {
	public static final Logger logger = LoggerFactory.getLogger(Activity.class);
	private Sport sport;
	private Intensity intensity = Intensity.MODERATE;
	private Duration duration;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss")
	private LocalDateTime date;

	private int burnedCalories;

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
		this(sport, intensity, duration, date, -1);
	}

	public Activity(
			Sport sport,
			Intensity intensity,
			Duration duration,
			LocalDateTime date,
			int burnedCalories) {
		this.sport = sport;
		this.intensity = intensity;
		this.duration = duration;
		this.date = date;
		this.burnedCalories = burnedCalories;
		logger.trace("Creating activity: {} ", this);
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

	// Getters and setters for class attributes.
	// These are used by Jackson to serialize and deserialize JSON data.

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

	/**
	 * Gets the number of calories burned during the activity.
	 *
	 * @return The number of calories burned during the activity.
	 */
	public int getBurnedCalories() {
		return burnedCalories;
	}

	/**
	 * Sets the number of calories burned during the activity.
	 *
	 * @param burnedCalories The number of calories burned during the activity.
	 */
	public void setBurnedCalories(int burnedCalories) {
		this.burnedCalories = burnedCalories;
	}
}
