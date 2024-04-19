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

public record ActivityDTO(
		Sport sport, Intensity intensity, int duration, LocalDateTime date, int burnedCalories) {

	public ActivityDTO(Sport sport, Intensity intensity, int duration, LocalDateTime date) {
		this(sport, intensity, duration, date, -1);
	}

	public ActivityDTO(ActivityDTO activityDTO, int burnedCalories) {
		this(
				activityDTO.sport(),
				activityDTO.intensity(),
				activityDTO.duration(),
				activityDTO.date(),
				burnedCalories);
	}
}
