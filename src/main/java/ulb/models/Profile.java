package ulb.models;

import ulb.models.enums.Sex;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class Profile implements Model {

	public static final String FILENAME = "profile.json";

	private String name;
	private Sex sex;
	private Weight weight;
	private Height height;
	private Date birthDate;

	public Profile() {
	}

	public static Profile load() throws IOException {
		File file = new File(FILENAME);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(file, Profile.class);
		} catch (IOException e) {
			System.out.println("No valid profile found");
			return null;
		}
	}

	public void save() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(new File(FILENAME), this);
		} catch (IOException e) {
			e.printStackTrace();
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String toString() {
		return "Profile{" +
				"name=" + name +
				", sex=" + sex +
				", weight=" + weight +
				", height=" + height +
				", birthDate=" + birthDate +
				'}';
	}
}
