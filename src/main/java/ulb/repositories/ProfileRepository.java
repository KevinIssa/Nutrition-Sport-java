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

import ulb.exceptions.IllegalImageFormatException;
import ulb.exceptions.InvalidImageException;
import ulb.exceptions.SavingException;
import ulb.models.Profile;

/**
 * The ProfileRepository interface provides the contract for a repository handling Profile objects.
 * It defines the basic CRUD operations to be implemented by any class that handles the persistence of Profile objects.
 */
public interface ProfileRepository {

	/**
	 * Saves a Profile object.
	 *
	 * @param profile the Profile object to be saved
	 */
	void save(Profile profile) throws SavingException;

	/**
	 * Saves the profile image.
	 *
	 * @param imagePath the path to the image
	 * @throws InvalidImageException if the image is invalid
	 * @throws IllegalImageFormatException if the image format is not supported
	 */
	void saveProfileImage(String imagePath)
			throws InvalidImageException, IllegalImageFormatException;

	/**
	 * Loads a Profile object.
	 *
	 * @return the loaded Profile object
	 */
	Profile load();

	/**
	 * Updates a Profile object.
	 *
	 * @param profile the Profile object to be updated
	 */
	void update(Profile profile) throws SavingException;

	/**
	 * Deletes a Profile object.
	 */
	void delete();

	/**
	 * Checks if a Profile object has been created.
	 *
	 * @return true if a Profile object has been created, false otherwise
	 */
	boolean isCreated();

	/**
	 * Retrieves the image path of the profile.
	 *
	 * @return the image path of the profile
	 */
	String getImagePath();

	/**
	 * Retrieves the weight of the profile.
	 *
	 * @return the weight of the profile
	 */
	float getWeight();
}
