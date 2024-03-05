package ulb.models;

import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

import java.time.Duration;
import java.time.LocalDate;


public class Activity {

	private final Sport sport;
	private final Intensity intensity;
	private final Duration duration;
	private final LocalDate date;


	public Activity(Sport sport, Intensity intensity, Duration duration, LocalDate date) {
		this.sport = sport;
		this.intensity = intensity;
		this.duration = duration;
		this.date = date;
	}

	public Sport getSport() {
		return sport;
	}

	public Intensity getIntensity() {
		return intensity;
	}

	public Duration getDuration() {
		return duration;
	}

	public int getDurationInMinutes() {
		return (int) duration.toMinutes();
	}

	public String toString() {
		return "Activity{" +
				"sport=" + sport +
				", intensity=" + intensity +
				", duration=" + duration +
				", date=" + date +
				'}';
	}

	public String toJSON() {
		// TODO: implement with Jackson
		return "{ \"sport\": \"" + sport + "\", \"intensity\": \"" + intensity + "\", \"duration\": \"" + duration + "\", \"date\": \"" + date + "\" }";
	}

	public static Activity fromJSON(String json) {
		// TODO: implement with Jackson
		return null;
	}

	public double getCaloriesBurned(Weight weight) {
		return CalorieCalculator.compute(this, weight);
	}

}
