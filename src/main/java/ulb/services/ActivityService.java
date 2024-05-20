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
package ulb.services;

import java.time.Duration;
import java.util.List;
import ulb.dtos.ActivityDTO;
import ulb.exceptions.SavingException;
import ulb.models.Activity;
import ulb.repositories.ActivityRepository;

/**
 * This class provides services related to activities.
 * It uses an ActivityRepository to perform operations on Activity objects.
 */
public class ActivityService {
	private final ActivityRepository activityRepository;

	/**
	 * Constructor for ActivityService.
	 * @param activityRepository The repository to be used for operations on Activity objects.
	 */
	public ActivityService(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	/**
	 * Saves an ActivityDTO object by converting it to an Activity object and saving it using the repository.
	 * @param activityDTO The ActivityDTO object to be saved.
	 */
	public void saveActivity(ActivityDTO activityDTO) throws SavingException {
		this.activityRepository.save(this.convertToActivity(activityDTO));
	}

	/**
	 * Loads all Activity objects from the repository, converts them to ActivityDTO objects, and returns them as a list.
	 * @return A list of all loaded ActivityDTO objects.
	 */
	public List<ActivityDTO> loadActivities() {
		return this.activityRepository.loadAll().stream().map(this::convertToActivityDTO).toList();
	}

	/**
	 * Deletes an ActivityDTO object by converting it to an Activity object and deleting it using the repository.
	 * @param activityDTO The ActivityDTO object to be deleted.
	 */
	public void deleteActivity(ActivityDTO activityDTO) {
		this.activityRepository.delete(this.convertToActivity(activityDTO));
	}

	/**
	 * Deletes all Activity objects from the repository.
	 */
	public void deleteAllActivities() {
		this.activityRepository.deleteAll();
	}

	/**
	 * Calculates the calories burned during an activity for a given weight.
	 * @param activityDTO The ActivityDTO object for which the calories burned is to be calculated.
	 * @param weight The weight of the user.
	 * @return The number of calories burned.
	 */
	public int calculateCaloriesBurnedDuringActivity(ActivityDTO activityDTO, float weight) {
		return this.calculateCaloriesBurnedDuringActivity(
				this.convertToActivity(activityDTO), weight);
	}

	/**
	 * Converts an ActivityDTO object to an Activity object.
	 * @param activityDTO The ActivityDTO object to be converted.
	 * @return The converted Activity object.
	 */
	private Activity convertToActivity(ActivityDTO activityDTO) {
		return new Activity(
				activityDTO.sport(),
				activityDTO.intensity(),
				Duration.ofMinutes(activityDTO.duration()),
				activityDTO.date(),
				activityDTO.burnedCalories());
	}

	/**
	 * Converts an Activity object to an ActivityDTO object.
	 * @param activity The Activity object to be converted.
	 * @return The converted ActivityDTO object.
	 */
	private ActivityDTO convertToActivityDTO(Activity activity) {
		return new ActivityDTO(
				activity.getSport(),
				activity.getIntensity(),
				(int) activity.getDuration().toMinutes(),
				activity.getDate(),
				activity.getBurnedCalories());
	}

	/**
	 * Calculates the calories burned during an activity for a given weight.
	 * @param activity The Activity object for which the calories burned is to be calculated.
	 * @param weight The weight of the user.
	 * @return The number of calories burned.
	 */
	private int calculateCaloriesBurnedDuringActivity(Activity activity, float weight) {
		return this.activityRepository.calculateCaloriesBurned(activity, weight);
	}
}
