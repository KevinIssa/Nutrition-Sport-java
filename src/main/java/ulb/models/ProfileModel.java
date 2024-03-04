package ulb.models;

import java.util.Date;

public class ProfileModel implements Model {

	private String name;
	private double weight;
	private double height;
	private Date birthDate;

	public ProfileModel() {
	}

	public boolean load() {
		// If there is the json file of the profile, load it and return true
		// Otherwise, return false

		return false;
	}

	public void save() {
		// Save the profile to a json file
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
