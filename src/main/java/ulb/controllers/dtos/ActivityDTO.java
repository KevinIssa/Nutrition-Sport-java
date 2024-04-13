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
package ulb.controllers.dtos;

import ulb.models.Activity;
import ulb.models.Profile;

/**
 * The ActivityDTO class is a Data Transfer Object (DTO) for the Activity class.
 * It is used to transfer data of an Activity object in a serializable way or between different parts of the application.
 * It contains the date, burned calories, intensity, sport, and duration of an activity.
 */
public class ActivityDTO {

	// The date of the activity
	public final String date;

	// The calories burned during the activity
	public final String burnedCalories;

	// The intensity of the activity
	public final String intensity;

	// The sport of the activity
	public final String sport;

	// The duration of the activity
	public final String duration;

	/**
	 * The constructor for the ActivityDTO class.
	 * It initializes the date, burned calories, intensity, sport, and duration of the activity DTO with the corresponding values of the provided Activity object.
	 *
	 * @param activity The Activity object.
	 */
	public ActivityDTO(Activity activity) {
		date = activity.changeDateFormat(activity.getDate());
		burnedCalories = String.valueOf(activity.getCaloriesBurned(Profile.load().getWeight()));
		intensity = activity.getIntensity().toString();
		sport = activity.getSport().toString();
		duration = activity.durationToString(activity.getDuration());
	}
}
