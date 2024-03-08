package ulb.models;

import ulb.models.enums.Sex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


public class Profile implements JsonSerializable {

	public static final String FILENAME = "profile.json";

	private String firstName;
	private String lastName;
	private Sex sex;
	private Weight weight;
	private Height height;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;


	public Profile() {}

	public Profile(String firstName, String lastName, Sex sex, Weight weight, Height height, LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.weight = weight;
		this.height = height;
		this.birthDate = birthDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Profile) {
			Profile profile = (Profile) obj;
			return this.firstName.equals(profile.firstName) &&
					this.lastName.equals(profile.lastName) &&
					this.sex.equals(profile.sex) &&
					this.weight.equals(profile.weight) &&
					this.height.equals(profile.height) &&
					this.birthDate.equals(profile.birthDate);
		}
		return false;
	}

	public static boolean isCreated() {
		File file = new File(FILENAME);
		return file.exists();
	}

	public void save() {
		this.saveToFile(FILENAME);
	}

	public static Profile load() {
		return (Profile) new Profile().loadFromFile(FILENAME);
	}

	public void delete() {
		File file = new File(FILENAME);
		if (file.exists()) {
			boolean deleted = file.delete();
			if (!deleted) {
				System.err.println("Failed to delete the file: " + FILENAME);
			}
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
				"firstName=" + firstName +
				", lastName=" + lastName +
				", sex=" + sex +
				", weight=" + weight.getWeight() +
				", height=" + height.getHeight() +
				", birthDate=" + birthDate +
				'}';
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
			return mapper.readValue(file, Profile.class);
		} catch (IOException e) {
			System.out.println("No valid profile found");
			return null;
		}
	}

}
