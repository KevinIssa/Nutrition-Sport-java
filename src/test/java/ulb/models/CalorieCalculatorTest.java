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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ulb.enums.Intensity;
import ulb.enums.Sport;

public class CalorieCalculatorTest {

	@Test
	public void computeRunning() {
		testActivity(Sport.RUNNING, 137, 672);
	}

	@Test
	public void computeVolleyball() {
		testActivity(Sport.VOLLEYBALL, 47, 252);
	}

	@Test
	public void computeWalking() {
		testActivity(Sport.WALKING, 47, 252);
	}

	@Test
	public void computeBiking() {
		testActivity(Sport.BIKING, 105, 336);
	}

	@Test
	public void computeSwimming() {
		testActivity(Sport.SWIMMING, 105, 504);
	}

	private void testActivity(Sport sport, int intenseCalories, int slowCalories) {
		Activity activity =
				new Activity(sport, Intensity.INTENSE, Duration.ofMinutes(15), LocalDateTime.now());
		assertEquals(intenseCalories, CalorieCalculator.compute(activity, 40), 1);
		activity = new Activity(sport, Intensity.SLOW, Duration.ofHours(1), LocalDateTime.now());
		assertEquals(slowCalories, CalorieCalculator.compute(activity, 80), 1);
	}
}
