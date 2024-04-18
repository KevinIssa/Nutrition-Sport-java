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

import ulb.models.Activity;

/**
 * The ActivityDTO class is a Data Transfer Object (DTO) for the Activity model.
 * It is used to transfer data of an Activity object in a serializable way.
 * This class contains the date, burned calories, intensity, sport, and duration of an activity.
 * The data is initialized in the constructor using an Activity object.
 */
public class ActivityDTO {
	public final String date;
	public final String burnedCalories;
	public final String intensity;
	public final String sport;
	public final String duration;

	/**
	 * This constructor initializes the data of the ActivityDTO object using an Activity object.
	 * It converts the date, burned calories, intensity, sport, and duration of the activity to strings.
	 *
	 * @param activity The Activity object to initialize the ActivityDTO object with.
	 */
	public ActivityDTO(Activity activity) {
		date = activity.getDateToString();
		//		burnedCalories = String.valueOf(activity.getCaloriesBurned(Profile.load().getWeight()));
		burnedCalories = String.valueOf(activity.getCaloriesBurned(0));
		intensity = activity.getIntensity().toString();
		sport = activity.getSport().toString();
		duration = activity.getDurationToString();
	}
}
