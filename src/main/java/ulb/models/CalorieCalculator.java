/* (C)2024 */
package ulb.models;

import java.util.HashMap;
import java.util.Map;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

public class CalorieCalculator {

	private static final HashMap<Map.Entry<Sport, Intensity>, Double> metMap =
			new HashMap<>(
					Map.ofEntries(
							Map.entry(Map.entry(Sport.WALKING, Intensity.SLOW), 3.0),
							Map.entry(Map.entry(Sport.WALKING, Intensity.MODERATE), 3.75),
							Map.entry(Map.entry(Sport.WALKING, Intensity.INTENSE), 4.5),
							Map.entry(Map.entry(Sport.RUNNING, Intensity.SLOW), 8.0),
							Map.entry(Map.entry(Sport.RUNNING, Intensity.MODERATE), 10.0),
							Map.entry(Map.entry(Sport.RUNNING, Intensity.INTENSE), 13.0),
							Map.entry(Map.entry(Sport.BIKING, Intensity.SLOW), 4.0),
							Map.entry(Map.entry(Sport.BIKING, Intensity.MODERATE), 7.0),
							Map.entry(Map.entry(Sport.BIKING, Intensity.INTENSE), 10.0),
							Map.entry(Map.entry(Sport.SWIMMING, Intensity.SLOW), 6.0),
							Map.entry(Map.entry(Sport.SWIMMING, Intensity.MODERATE), 8.0),
							Map.entry(Map.entry(Sport.SWIMMING, Intensity.INTENSE), 10.0),
							Map.entry(Map.entry(Sport.VOLLEYBALL, Intensity.SLOW), 3.0),
							Map.entry(Map.entry(Sport.VOLLEYBALL, Intensity.MODERATE), 3.75),
							Map.entry(Map.entry(Sport.VOLLEYBALL, Intensity.INTENSE), 4.5)));

	private static double getMET(Sport sport, Intensity intensity) {
		return metMap.get(Map.entry(sport, intensity));
	}

	public static double compute(Activity activity, float weight) {
		return ((getMET(activity.getSport(), activity.getIntensity()) * 3.5 * weight) / 200)
				* activity.getDurationInMinutes();
	}
}
