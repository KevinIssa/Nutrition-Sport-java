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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

public class Activity implements JsonSerializable {

	private static final String FOLDER_NAME = "activities";

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
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Activity activity = (Activity) obj;
		return sport == activity.sport
				&& intensity == activity.intensity
				&& duration.equals(activity.duration)
				&& date.equals(activity.date);
	}

	public void save() {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			folder.mkdir();
		}
		String filename =
				FOLDER_NAME
						+ "/"
						+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
						+ ".json";
		saveToFile(filename);
	}

	public static void clearAllActivities() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}
		}
	}

	public static Activity load(String filename) {
		return (Activity) new Activity().loadFromFile(filename);
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

	public double getCaloriesBurned(float weight) {
		return CalorieCalculator.compute(this, weight);
	}

	@Override
	public String toString() {
		return "Activity{"
				+ "sport="
				+ sport
				+ ", intensity="
				+ intensity
				+ ", duration="
				+ duration
				+ ", date="
				+ date
				+ '}';
	}

	@Override
	public void saveToFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(filename), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JsonSerializable loadFromFile(String filename) {
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
}
