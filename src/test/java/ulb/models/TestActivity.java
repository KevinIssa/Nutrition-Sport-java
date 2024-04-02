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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

public class TestActivity {

	@Test
	public void testActivity() {
		Activity activity = createAndSaveActivity();
		Activity activity2 = Activity.load(getFilename(activity.getDate()));

		// Remove the file
		new File(getFilename(activity.getDate())).delete();

		// Test
		assertEquals(activity, activity2);
		Activity.clearAllActivities(); // Clean up
	}

	@Test
	public void testClearAllActivities() {
		// Create dummy activity files
		createDummyActivityFiles();

		Activity.clearAllActivities();

		// Check if the files were deleted
		assertFalse(checkDummyActivityFilesExist());
	}

	private Activity createAndSaveActivity() {
		Activity activity =
				new Activity(
						Sport.RUNNING,
						Intensity.INTENSE,
						Duration.ofMinutes(30),
						LocalDateTime.now().withNano(0));
		activity.save();
		return activity;
	}

	private void createDummyActivityFiles() {
		for (int i = 1; i <= 3; i++) {
			new Activity(
							Sport.RUNNING,
							Intensity.INTENSE,
							Duration.ofMinutes(30),
							LocalDateTime.now().withNano(0).minusDays(i))
					.save();
		}
	}

	private boolean checkDummyActivityFilesExist() {
		for (int i = 1; i <= 3; i++) {
			if (new File(getFilename(LocalDateTime.now().withNano(0).minusDays(i))).exists()) {
				return true;
			}
		}
		return false;
	}

	private String getFilename(LocalDateTime date) {
		return "activities/"
				+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
				+ ".json";
	}
}
