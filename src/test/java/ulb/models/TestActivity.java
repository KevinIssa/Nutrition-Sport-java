/* (C)2024 */
package ulb.models;

import static org.junit.Assert.assertEquals;

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
	}
}
