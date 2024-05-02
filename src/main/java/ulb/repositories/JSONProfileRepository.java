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
package ulb.repositories;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import ulb.exceptions.IllegalImageFormatException;
import ulb.exceptions.InvalidImageException;
import ulb.models.Profile;

/**
 * This class implements the ProfileRepository interface and provides methods for saving, loading, updating, and deleting Profile objects.
 * It uses JSON files for storing and retrieving profiles.
 */
public class JSONProfileRepository extends JSONRepository<Profile> implements ProfileRepository {
	private static final String FILE_NAME = "profile.json";
	private static final String IMAGE_PATH = "profile.png";

	/**
	 * Saves the given Profile object to a JSON file.
	 * @param profile The Profile object to be saved.
	 */
	@Override
	public void save(Profile profile) {
		try {
			super.save(profile, FILE_NAME);
		} catch (IOException e) {
			// TODO: Handle exception
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Saves the profile image to a specified path.
	 * @param imagePath The path to the image.
	 * @throws InvalidImageException if the image is invalid.
	 * @throws IllegalImageFormatException if the image format is not supported.
	 */
	@Override
	public void saveProfileImage(String imagePath)
			throws InvalidImageException, IllegalImageFormatException {
		if(imagePath.equals(IMAGE_PATH)){
			return;
		}
		if (!(imagePath.endsWith(".png")
				|| imagePath.endsWith(".jpg")
				|| imagePath.endsWith(".jpeg"))) {
			// TODO: Handle exception
			// logger.warn("Only PNG and JPG images are supported: {}", imagePath);
			throw new IllegalImageFormatException("Only PNG, JPG and JPEG images are supported");
		}
		try {
			Files.copy(
					new URL(imagePath).openStream(),
					Paths.get(IMAGE_PATH),
					java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO: Handle exception
			// logger.error("Failed to save image", e);
			throw new InvalidImageException("Failed to save image");
		}
	}

	/**
	 * Loads a Profile object from a JSON file.
	 * @return The loaded Profile object.
	 */
	@Override
	public Profile load() {
		try {
			return super.load(FILE_NAME);
		} catch (IOException e) {
			// TODO: Handle exception
			e.printStackTrace();
			System.exit(1);
			return null; // Unreachable
		}
	}

	/**
	 * Updates a Profile object in the JSON file.
	 * @param profile The Profile object to be updated.
	 */
	@Override
	public void update(Profile profile) {
		this.save(profile);
	}

	/**
	 * Deletes a Profile object from the JSON file.
	 */
	@Override
	public void delete() {
		this.delete(FILE_NAME);
	}

	/**
	 * Checks if a Profile object has been created.
	 * @return true if a Profile object has been created, false otherwise.
	 */
	@Override
	public boolean isCreated() {
		return new File(FILE_NAME).exists() && this.load() != null;
	}

	/**
	 * Retrieves the image path of the profile.
	 * @return The image path of the profile.
	 */
	@Override
	public String getImagePath() {
		return IMAGE_PATH;
	}

	/**
	 * Retrieves the weight of the profile.
	 * @return The weight of the profile.
	 */
	@Override
	public float getWeight() {
		return Optional.ofNullable(this.load()).map(Profile::getWeight).orElse(0f);
	}

	/**
	 * Returns the Class object for type Profile.
	 * It is used by the Jackson library to know the type of the objects to deserialize.
	 * @return the Class object for type Profile
	 */
	@Override
	protected Class<Profile> getObjectType() {
		return Profile.class;
	}
}
