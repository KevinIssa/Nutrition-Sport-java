package ulb.models;

import ulb.models.enums.Sex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


public class Profile {

	public static final String FILENAME = "profile.json";

	private String name;
	private Sex sex;
	private Weight weight;
	private Height height;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	public Profile() {
	}

	public void save() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(FILENAME), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Profile load() {
		File file = new File(FILENAME);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(file, Profile.class);
		} catch (IOException e) {
			System.out.println("No valid profile found");
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public double getWeight() {
		return weight.getWeight();
	}

	public void setWeight(float weight) {
		this.weight = new Weight(weight);
	}

	public float getHeight() {
		return height.getHeight();
	}

	public void setHeight(float height) {
		this.height = new Height(height);
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String toString() {
		return "Profile{" +
				"name=" + name +
				", sex=" + sex +
				", weight=" + weight.getWeight() +
				", height=" + height.getHeight() +
				", birthDate=" + birthDate +
				'}';
	}
}
