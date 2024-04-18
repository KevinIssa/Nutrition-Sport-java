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
package ulb.services;

import ulb.dtos.ProfileDTO;

public interface ProfileService {

	/**
	 * Saves a ProfileDTO object.
	 *
	 * @param profileDTO the ProfileDTO object to be saved
	 */
	void saveProfile(ProfileDTO profileDTO);

	/**
	 * Loads a ProfileDTO object.
	 *
	 * @return the loaded ProfileDTO object
	 */
	ProfileDTO loadProfile();

	/**
	 * Updates a ProfileDTO object.
	 *
	 * @param profileDTO the ProfileDTO object to be updated
	 */
	void updateProfile(ProfileDTO profileDTO);

	/**
	 * Checks if a ProfileDTO object has been created.
	 *
	 * @return true if a ProfileDTO object has been created, false otherwise
	 */
	boolean isProfileCreated();

	/**
	 * Deletes a ProfileDTO object.
	 */
	void deleteProfile();

	String getProfileImagePath();
}
