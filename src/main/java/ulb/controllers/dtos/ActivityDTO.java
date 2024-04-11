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

public class ActivityDTO {
	public final String date;
	public final String burnedCalories;
	public final String intensity;
	public final String sport;
	public final String duration;

	public ActivityDTO(Activity activity, Profile profile) {
		date = activity.changeDateFormat(activity.getDate());
		burnedCalories = String.valueOf(activity.getCaloriesBurned(Profile.load().getWeight()));
		intensity = activity.getIntensity().toString();
		sport = activity.getSport().toString();
		duration = activity.durationToString(activity.getDuration());
	}
}
