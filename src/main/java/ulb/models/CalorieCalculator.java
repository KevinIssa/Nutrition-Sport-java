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

import java.util.HashMap;
import java.util.Map;
import ulb.enums.Intensity;
import ulb.enums.Sport;

/**
 * Utility class to calculate calories burned during various activities.
 */
public class CalorieCalculator {

	// Map to store MET values for different sports and intensities
	private static final Map<Map.Entry<Sport, Intensity>, Double> MET_MAP = createMetMap();

	// Initialize MET values for different sports and intensities
	private static Map<Map.Entry<Sport, Intensity>, Double> createMetMap() {
		Map<Map.Entry<Sport, Intensity>, Double> metMap = new HashMap<>();
		metMap.put(Map.entry(Sport.WALKING, Intensity.SLOW), 3.0);
		metMap.put(Map.entry(Sport.WALKING, Intensity.MODERATE), 3.75);
		metMap.put(Map.entry(Sport.WALKING, Intensity.INTENSE), 4.5);
		metMap.put(Map.entry(Sport.RUNNING, Intensity.SLOW), 8.0);
		metMap.put(Map.entry(Sport.RUNNING, Intensity.MODERATE), 10.0);
		metMap.put(Map.entry(Sport.RUNNING, Intensity.INTENSE), 13.0);
		metMap.put(Map.entry(Sport.BIKING, Intensity.SLOW), 4.0);
		metMap.put(Map.entry(Sport.BIKING, Intensity.MODERATE), 7.0);
		metMap.put(Map.entry(Sport.BIKING, Intensity.INTENSE), 10.0);
		metMap.put(Map.entry(Sport.SWIMMING, Intensity.SLOW), 6.0);
		metMap.put(Map.entry(Sport.SWIMMING, Intensity.MODERATE), 8.0);
		metMap.put(Map.entry(Sport.SWIMMING, Intensity.INTENSE), 10.0);
		metMap.put(Map.entry(Sport.VOLLEYBALL, Intensity.SLOW), 3.0);
		metMap.put(Map.entry(Sport.VOLLEYBALL, Intensity.MODERATE), 3.75);
		metMap.put(Map.entry(Sport.VOLLEYBALL, Intensity.INTENSE), 4.5);
		return metMap;
	}

	/**
	 * Retrieves the MET (Metabolic Equivalent of Task) value based on the sport and intensity.
	 *
	 * @param sport     The sport of the activity.
	 * @param intensity The intensity of the activity.
	 * @return The MET value for the given sport and intensity.
	 */
	private static double getMET(Sport sport, Intensity intensity) {
		return MET_MAP.get(Map.entry(sport, intensity));
	}

	/**
	 * Computes the calories burned during the activity.
	 *
	 * @param activity The activity for which calories are to be calculated.
	 * @param weight   The weight of the person performing the activity.
	 * @return The number of calories burned during the activity.
	 */
	public static double compute(Activity activity, float weight) {
		double met = getMET(activity.getSport(), activity.getIntensity());
		int durationInMinutes = activity.getDurationInMinutes();
		return ((met * 3.5 * weight) / 200) * durationInMinutes;
	}
}
