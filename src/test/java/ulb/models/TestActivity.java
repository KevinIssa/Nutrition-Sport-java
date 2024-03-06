package ulb.models;

import org.junit.Test;
import ulb.models.enums.Sport;
import ulb.models.enums.Intensity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class TestActivity {

	@Test
	public void testActivity() {
		Activity activity = new Activity(Sport.RUNNING, Intensity.INTENSE, Duration.ofMinutes(30), LocalDateTime.now().withNano(0));
		activity.save();
		Activity activity2 = Activity.load("activities" + "/" + activity.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".json");
		assertEquals(activity, activity2);
	}

}
