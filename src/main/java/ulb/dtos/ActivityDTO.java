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
package ulb.dtos;

import java.time.LocalDateTime;
import ulb.enums.Intensity;
import ulb.enums.Sport;

/**
 * This record represents an Activity Data Transfer Object (DTO).
 * It is used to transfer data related to a user's activity between processes or components.
 *
 * @param sport The type of sport for the activity.
 * @param intensity The intensity level of the activity.
 * @param duration The duration of the activity in minutes.
 * @param date The date and time when the activity took place.
 * @param burnedCalories The number of calories burned during the activity.
 */
public record ActivityDTO(
		Sport sport, Intensity intensity, int duration, LocalDateTime date, double burnedCalories) {

	/**
	 * Constructor for ActivityDTO with specified sport, intensity, duration, and date.
	 * The burnedCalories is set to -1 by default.
	 *
	 * @param sport The type of sport for the activity.
	 * @param intensity The intensity level of the activity.
	 * @param duration The duration of the activity in minutes.
	 * @param date The date and time when the activity took place.
	 */
	public ActivityDTO(Sport sport, Intensity intensity, int duration, LocalDateTime date) {
		this(sport, intensity, duration, date, -1);
	}

	/**
	 * Constructor for ActivityDTO with an existing ActivityDTO and specified burnedCalories.
	 *
	 * @param activityDTO An existing ActivityDTO.
	 * @param burnedCalories The number of calories burned during the activity.
	 */
	public ActivityDTO(ActivityDTO activityDTO, double burnedCalories) {
		this(
				activityDTO.sport(),
				activityDTO.intensity(),
				activityDTO.duration(),
				activityDTO.date(),
				burnedCalories);
	}
}
