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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.exceptions.IllegalImageFormatException;
import ulb.exceptions.ImageException;
import ulb.exceptions.InvalidImageException;
import ulb.models.enums.Sex;

/**
 * Represents a user profile containing personal information.
 */
public class Profile implements JsonSerializable {
	private static final Logger logger = LoggerFactory.getLogger(Profile.class);

	public static final String FILE_NAME = "profile.json";
	public static final String IMAGE_PATH = "profile.png";

	private String firstName;
	private String lastName;
	private Sex sex;
	private Weight weight;
	private Height height;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	/**
	 * Default constructor for Profile.
	 */
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
			Weight weight,
			Height height,
			LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.weight = weight;
		this.height = height;
		this.birthDate = birthDate;
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
	 * Checks if a profile file exists.
	 * @return True if the profile file exists, false otherwise.
	 */
	public static boolean isCreated() {
		File file = new File(FILE_NAME);
		return file.exists();
	}

	/**
	 * Saves the profile to a file.
	 */
	public void save() {
		this.saveToFile(FILE_NAME);
	}

	/**
	 * Loads the profile from a file.
	 * @return The loaded profile.
	 */
	public static Profile load() {
		return (Profile) new Profile().loadFromFile(FILE_NAME);
	}

	/**
	 * Deletes the profile files.
	 */
	public static void delete() {
		JsonSerializable.deleteFile(FILE_NAME);
		JsonSerializable.deleteFile(IMAGE_PATH);
	}

	/**
	 * Saves an image from a given URL to a local file named "profile.png".
	 * <p>
	 * This method creates a URL object from the provided image path, opens a stream to the URL,
	 * and copies the contents of the stream to a local file named "profile.png". If a file with
	 * the same name already exists, it is replaced.
	 *
	 * @param imagePath The URL of the image to save.
	 * @throws RuntimeException If an IOException occurs during the operation.
	 */
	public static void saveImage(String imagePath) throws ImageException {
		if (!(imagePath.endsWith(".png") || imagePath.endsWith(".jpg"))) {
			logger.warn("Only PNG and JPG images are supported: {}", imagePath);
			throw new IllegalImageFormatException("Only PNG and JPG images are supported");
		}

		try {
			Files.copy(
					new URL(imagePath).openStream(),
					Paths.get(IMAGE_PATH),
					java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error("Failed to save image", e);
			throw new InvalidImageException("Failed to save image");
		}
	}

	/**
	 * Generates a string representation of the profile.
	 * @return A string representation of the profile.
	 */
	@Override
	public String toString() {
		return "Profile{"
				+ "firstName='"
				+ firstName
				+ '\''
				+ ", lastName='"
				+ lastName
				+ '\''
				+ ", sex="
				+ sex
				+ ", weight="
				+ weight.getWeight()
				+ ", height="
				+ height.getHeight()
				+ ", birthDate="
				+ birthDate
				+ '}';
	}

	/**
	 * Saves the profile to a file.
	 * @param filename The name of the file to save the profile to.
	 */
	@Override
	public void saveToFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(filename), this);
		} catch (IOException e) {
			logger.error(
					"Error saving profile to file: {} with value : {}, {}, {}, {}, {}, {}",
					filename,
					this.firstName,
					this.lastName,
					this.birthDate,
					this.sex,
					this.weight,
					this.height);
		}
	}

	/**
	 * Loads the profile from a file.
	 * @param filename The name of the file to load the profile from.
	 * @return The loaded profile.
	 */
	@Override
	public JsonSerializable loadFromFile(String filename) {
		File file = new File(filename);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(file, Profile.class);
		} catch (IOException e) {
			logger.error("Error loading profile from file: {}", filename);
			return null;
		}
	}

	// Getters and setters for Profile properties
	/**
	 * Gets the first name of the user.
	 * @return The first name of the user.
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name of the user.
	 * @param newFirstName The new first name to set.
	 */
	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	/**
	 * Gets the last name of the user.
	 * @return The last name of the user.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name of the user.
	 * @param newLastName The new last name to set.
	 */
	public void setLastName(String newLastName) {
		this.lastName = newLastName;
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
	public void setWeight(float weight) throws IllegalArgumentException {
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
	public void setHeight(float height) {
		this.height = new Height(height);
	}

	/**
	 * Gets the birthdate of the user.
	 * @return The birthdate of the user.
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the birthdate of the user.
	 * @param birthDate The new birthdate to set.
	 */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}
