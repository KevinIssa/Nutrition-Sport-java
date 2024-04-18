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
import java.util.stream.Collectors;
import ulb.dtos.ActivityDTO;
import ulb.enums.Intensity;
import ulb.models.Activity;
import ulb.repositories.ActivityRepository;

public class ActivityService {
	private final ActivityRepository activityRepository;

	public ActivityService(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	public void saveActivity(ActivityDTO activityDTO) {
		this.activityRepository.save(this.convertToActivity(activityDTO));
	}

	public List<ActivityDTO> loadActivities() {
		return this.activityRepository.loadAll().stream()
				.map(this::convertToActivityDTO)
				.collect(Collectors.toList());
	}

	public void deleteActivity(ActivityDTO activityDTO) {
		this.activityRepository.delete(this.convertToActivity(activityDTO));
	}

	public void deleteAllActivities() {
		this.activityRepository.deleteAll();
	}

	private Activity convertToActivity(ActivityDTO activityDTO) {
		return new Activity(
				activityDTO.sport(),
				Intensity.fromInt(activityDTO.intensity()),
				Duration.ofMinutes(activityDTO.duration()),
				activityDTO.date());
	}

	private ActivityDTO convertToActivityDTO(Activity activity) {
		return new ActivityDTO(
				activity.getSport(),
				activity.getIntensity().ordinal(),
				(int) activity.getDuration().toMinutes(),
				activity.getDate());
	}
}
