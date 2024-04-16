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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

public class TestActivity {

	private Activity activity;

	@BeforeEach
	public void setUp() {
		activity = createAndSaveActivity();
	}

	@AfterEach
	public void tearDown() {
		// Remove the file after each test
		new File(getFilename(activity.getDate())).delete();
	}

	@Test
	public void testActivityCreation() {
		assertNotNull(activity);
	}

	@Test
	public void testActivityLoading() {
		Activity loadedActivity = Activity.load(getFilename(activity.getDate()));
		assertEquals(activity, loadedActivity);
	}

	@Test
	public void testClearAllActivities() {
		// Create dummy activity files
		createDummyActivityFiles();

		Activity.clearAll();

		// Check if the files were deleted
		assertFalse(checkDummyActivityFilesExist());
	}

	@Test
	public void testGetDurationInMinutes() {
		int expectedDurationInMinutes = 30;
		assertEquals(expectedDurationInMinutes, activity.getDurationInMinutes());
	}

	@Test
	public void testGetDurationToString() {
		String expectedDurationString = "0h30m";
		assertEquals(expectedDurationString, activity.getDurationToString());
	}

	@Test
	public void testGetDateToString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
		String expectedDateString = activity.getDate().format(formatter);
		assertEquals(expectedDateString, activity.getDateToString());
	}

	@Test
	public void testLoadAllActivities() {
		List<Activity> activities = Activity.loadAll();
		assertNotNull(activities);
		assertTrue(activities.contains(activity));
	}

	private Activity createAndSaveActivity() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
		Activity activity =
				new Activity(Sport.RUNNING, Intensity.INTENSE, Duration.ofMinutes(30), now);
		activity.save();
		return activity;
	}

	private void createDummyActivityFiles() {
		for (int i = 1; i <= 3; i++) {
			LocalDateTime dateTime = LocalDateTime.now().withNano(0).minusDays(i);
			new Activity(Sport.RUNNING, Intensity.INTENSE, Duration.ofMinutes(30), dateTime).save();
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
		return Activity.FOLDER_NAME
				+ "/"
				+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
				+ ".json";
	}
}
