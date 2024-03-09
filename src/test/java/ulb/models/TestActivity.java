/* (C)2024 */
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
		Sport sport = Sport.RUNNING;
		Intensity intensity = Intensity.INTENSE;
		Duration duration = Duration.ofMinutes(30);
		LocalDateTime date = LocalDateTime.now().withNano(0);

		String filename =
				"activities"
						+ "/"
						+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
						+ ".json";

		Activity activity = new Activity(sport, intensity, duration, date);
		activity.save();
		Activity activity2 = Activity.load(filename);

		// Remove the file
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}

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

	private void createDummyActivityFiles() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
		for (int i = 1; i <= 3; i++) {
			// Create dummy activity files with dif timestamps and save them
			Activity activity = new Activity(Sport.RUNNING, Intensity.INTENSE, Duration.ofMinutes(30), now.minusDays(i));
			activity.save();
		}
	}

	private boolean checkDummyActivityFilesExist() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
		boolean filesExist = true;
		for (int i = 1; i <= 3; i++) {
			// Check if the dummy activity files still exist
			String filename = "activities/" + now.minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".json";
			File file = new File(filename);
			if (!file.exists()) {
				filesExist = false;
				break;
			}
		}
		return filesExist;
	}
}

