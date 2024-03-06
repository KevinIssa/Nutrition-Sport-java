package ulb.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Activity {

	private static final String FOLDERNAME = "activities";

	private Sport sport;
	private Intensity intensity;
	private Duration duration;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss")
	private LocalDateTime date;

	public Activity() {}

	public Activity(Sport sport, Intensity intensity, Duration duration, LocalDateTime date) {
		this.sport = sport;
		this.intensity = intensity;
		this.duration = duration;
		this.date = date;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Activity) {
			Activity activity = (Activity) obj;
			return this.sport.equals(activity.sport) &&
					this.intensity.equals(activity.intensity) &&
					this.duration.equals(activity.duration) &&
					this.date.equals(activity.date);
		}
		return false;
	}

	public void save() {
		File folder = new File(FOLDERNAME);
		if (!folder.exists()) {
			folder.mkdir();
		}
		String filename = FOLDERNAME + "/" + this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".json";
		System.out.println(filename);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(filename), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Activity load(String filename) {
		System.out.println(filename);
		File file = new File(filename);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(file, Activity.class);
		} catch (IOException e) {
			System.out.println("No activity found");
			return null;
		}
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Intensity getIntensity() {
		return intensity;
	}

	public void setIntensity(Intensity intensity) {
		this.intensity = intensity;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@JsonIgnore
	public int getDurationInMinutes() {
		return (int) duration.toMinutes();
	}

	public double getCaloriesBurned(Weight weight) {
		return CalorieCalculator.compute(this, weight);
	}

	public String toString() {
		return "Activity{" +
				"sport=" + sport +
				", intensity=" + intensity +
				", duration=" + duration +
				", date=" + date +
				'}';
	}

}
