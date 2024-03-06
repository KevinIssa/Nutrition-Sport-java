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

	private String firstName;

	private String lastName;
	private Sex sex;
	private Weight weight;
	private Height height;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	public Profile (String firstName, String lastName, String sex, float weight, float height, LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		sexStringToSex(sex);
		this.weight = new Weight(weight);
		this.height = new Height(height);
		this.birthDate = birthDate;
	}

	private void sexStringToSex(String sex) throws IllegalArgumentException{
		switch (sex) {
			case "♀":
				this.sex = Sex.FEMALE;
				break;
			case "♂":
				this.sex = Sex.MALE;
				break;
			default:
				throw new IllegalArgumentException("no valid sex given to the profile constructor");
		}
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public float getWeight() {
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
				"First Name=" + firstName +
				", Last Name=" + lastName +
				", sex=" + sex +
				", weight=" + weight.getWeight() +
				", height=" + height.getHeight() +
				", birthDate=" + birthDate +
				'}';
	}
}
