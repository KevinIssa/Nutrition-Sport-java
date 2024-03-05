package ulb.models;

import org.junit.Assert;
import org.junit.Test;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

import java.time.Duration;
import java.time.LocalDate;


public class TestCalorieCalculator {

	@Test
	public void computeRunning() {
		Activity activity = new Activity(Sport.RUNNING, Intensity.INTENSE, Duration.ofMinutes(15), LocalDate.now());
		Assert.assertEquals(137, activity.getCaloriesBurned(new Weight(40)), 1);
		activity = new Activity(Sport.RUNNING, Intensity.SLOW, Duration.ofHours(1), LocalDate.now());
		Assert.assertEquals(672, activity.getCaloriesBurned(new Weight(80)), 1);
	}

	@Test
	public void computeVolleyball() {
		Activity activity = new Activity(Sport.VOLLEYBALL, Intensity.INTENSE, Duration.ofMinutes(15), LocalDate.now());
		Assert.assertEquals(47, activity.getCaloriesBurned(new Weight(40)), 1);
		activity = new Activity(Sport.VOLLEYBALL, Intensity.SLOW, Duration.ofHours(1), LocalDate.now());
		Assert.assertEquals(252, activity.getCaloriesBurned(new Weight(80)), 1);
	}

	@Test
	public void computeWalking() {
		Activity activity = new Activity(Sport.WALKING, Intensity.INTENSE, Duration.ofMinutes(15), LocalDate.now());
		Assert.assertEquals(47, activity.getCaloriesBurned(new Weight(40)), 1);
		activity = new Activity(Sport.WALKING, Intensity.SLOW, Duration.ofHours(1), LocalDate.now());
		Assert.assertEquals(252, activity.getCaloriesBurned(new Weight(80)), 1);
	}

	@Test
	public void computeBiking() {
		Activity activity = new Activity(Sport.BIKING, Intensity.INTENSE, Duration.ofMinutes(15), LocalDate.now());
		Assert.assertEquals(105, activity.getCaloriesBurned(new Weight(40)), 1);
		activity = new Activity(Sport.BIKING, Intensity.SLOW, Duration.ofHours(1), LocalDate.now());
		Assert.assertEquals(336, activity.getCaloriesBurned(new Weight(80)), 1);
	}

	@Test
	public void computeSwimming() {
		Activity activity = new Activity(Sport.SWIMMING, Intensity.INTENSE, Duration.ofMinutes(15), LocalDate.now());
		Assert.assertEquals(105, activity.getCaloriesBurned(new Weight(40)), 1);
		activity = new Activity(Sport.SWIMMING, Intensity.MODERATE, Duration.ofHours(1), LocalDate.now());
		Assert.assertEquals(672, activity.getCaloriesBurned(new Weight(80)), 1);
	}
}
