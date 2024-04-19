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
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.enums.Sex;
import ulb.exceptions.BadBirthDateException;
import ulb.exceptions.BadHeightException;
import ulb.exceptions.BadNameException;
import ulb.exceptions.BadWeightException;

public class Profile {
	private static final Logger logger = LoggerFactory.getLogger(Profile.class);

	private Name firstName;
	private Name lastName;
	private Sex sex;
	private Weight weight;
	private Height height;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private BirthDate birthDate;

	public Profile() {}

	/**
	 * Constructs a Profile object with specified attributes.
	 * @param firstName The first name of the user.
	 * @param lastName The last name of the user.
	 * @param sex The gender of the user.
	 * @param weight The weight of the user.
	 * @param height The height of the user.
	 * @param birthDate The birthdate of the user.
	 */
	public Profile(
			String firstName,
			String lastName,
			Sex sex,
			float weight,
			float height,
			LocalDate birthDate)
			throws BadNameException, BadWeightException, BadHeightException, BadBirthDateException {
		this.firstName = new Name(firstName);
		this.lastName = new Name(lastName);
		this.sex = sex;
		this.weight = new Weight(weight);
		this.height = new Height(height);
		this.birthDate = new BirthDate(birthDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Profile profile = (Profile) obj;
		return firstName.equals(profile.firstName)
				&& lastName.equals(profile.lastName)
				&& sex == profile.sex
				&& weight.equals(profile.weight)
				&& height.equals(profile.height)
				&& birthDate.equals(profile.birthDate);
	}

	/**
	 * Generates a string representation of the profile.
	 * @return A string representation of the profile.
	 */
	@Override
	public String toString() {
		return STR."Profile{firstName='\{
				firstName.toString()}\{
				'\''}, lastName='\{
				lastName.toString()}\{
				'\''}, sex=\{
				sex.toString()}, weight=\{
				weight.toString()}, height=\{
				height.toString()}, birthDate=\{
				birthDate.toString()}\{
				'}'}";
	}

	// Getters and setters for class attributes.

	/**
	 * Gets the first name of the user.
	 * @return The first name of the user.
	 */
	public String getFirstName() {
		return this.firstName.getName();
	}

	/**
	 * Sets the first name of the user.
	 * @param newFirstName The new first name to set.
	 */
	public void setFirstName(String newFirstName) throws BadNameException {
		this.firstName = new Name(newFirstName);
	}

	/**
	 * Gets the last name of the user.
	 * @return The last name of the user.
	 */
	public String getLastName() {
		return this.lastName.getName();
	}

	/**
	 * Sets the last name of the user.
	 * @param newLastName The new last name to set.
	 */
	public void setLastName(String newLastName) throws BadNameException {
		this.lastName = new Name(newLastName);
	}

	/**
	 * Gets the gender of the user.
	 * @return The gender of the user.
	 */
	public Sex getSex() {
		return this.sex;
	}

	/**
	 * Sets the gender of the user.
	 * @param sex The new gender to set.
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	/**
	 * Gets the weight of the user.
	 * @return The weight of the user.
	 */
	public float getWeight() {
		return weight.getWeight();
	}

	/**
	 * Sets the weight of the user.
	 * @param weight The new weight to set.
	 */
	public void setWeight(float weight) throws BadWeightException {
		this.weight = new Weight(weight);
	}

	/**
	 * Gets the height of the user.
	 * @return The height of the user.
	 */
	public float getHeight() {
		return height.getHeight();
	}

	/**
	 * Sets the height of the user.
	 * @param height The new height to set.
	 */
	public void setHeight(float height) throws BadHeightException {
		this.height = new Height(height);
	}

	/**
	 * Gets the birthdate of the user.
	 * @return The birthdate of the user.
	 */
	public LocalDate getBirthDate() {
		return birthDate.getValue();
	}

	/**
	 * Sets the birthdate of the user.
	 * @param birthDate The new birthdate to set.
	 */
	public void setBirthDate(LocalDate birthDate) throws BadBirthDateException {
		this.birthDate = new BirthDate(birthDate);
	}
}
