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

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ulb.enums.Intensity;
import ulb.enums.Sport;

class ActivityTest {

	@Test
	void equalsReturnsTrueWhenActivitiesAreIdentical() {
		Sport sport = Sport.RUNNING;
		Intensity intensity = Intensity.INTENSE;
		Duration duration = Duration.ofHours(1);
		LocalDateTime date = LocalDateTime.now();
		Activity activity1 = new Activity(sport, intensity, duration, date);
		Activity activity2 = new Activity(sport, intensity, duration, date);
		assertEquals(activity1, activity2);
	}

	@Test
	void equalsReturnsFalseWhenActivitiesAreDifferent() {
		Activity activity1 =
				new Activity(
						Sport.RUNNING, Intensity.INTENSE, Duration.ofHours(1), LocalDateTime.now());
		Activity activity2 =
				new Activity(
						Sport.SWIMMING,
						Intensity.MODERATE,
						Duration.ofHours(2),
						LocalDateTime.now());
		assertFalse(activity1.equals(activity2));
	}

	@Test
	void hashCodeIsConsistent() {
		Activity activity =
				new Activity(
						Sport.RUNNING, Intensity.INTENSE, Duration.ofHours(1), LocalDateTime.now());
		int initialHashCode = activity.hashCode();
		assertEquals(initialHashCode, activity.hashCode());
	}
}
