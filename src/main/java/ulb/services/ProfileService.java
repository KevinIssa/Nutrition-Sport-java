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
import ulb.enums.Sex;
import ulb.exceptions.*;
import ulb.models.Profile;
import ulb.repositories.ProfileRepository;

/**
 * This class provides services related to profiles.
 * It uses a ProfileRepository to perform operations on Profile objects.
 */
public class ProfileService {
	private final ProfileRepository profileRepository;

	/**
	 * Constructor for ProfileService.
	 * @param profileRepository The repository to be used for operations on Profile objects.
	 */
	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	/**
	 * Saves a ProfileDTO object by converting it to a Profile object and saving it using the repository.
	 * Also saves the profile image.
	 * @param profileDTO The ProfileDTO object to be saved.
	 * @throws ValueObjectException if the value object is invalid.
	 * @throws IllegalImageFormatException if the image format is not supported.
	 * @throws InvalidImageException if the image is invalid.
	 */
	public void saveProfile(ProfileDTO profileDTO)
			throws ValueObjectException, IllegalImageFormatException, InvalidImageException {
		this.profileRepository.save(this.convertToProfile(profileDTO));
		this.profileRepository.saveProfileImage(profileDTO.imagePath());
	}

	/**
	 * Loads a Profile object from the repository, converts it to a ProfileDTO object, and returns it.
	 * @return The loaded ProfileDTO object.
	 */
	public ProfileDTO loadProfile() {
		return this.convertToProfileDTO(this.profileRepository.load());
	}

	/**
	 * Updates a ProfileDTO object by converting it to a Profile object and updating it using the repository.
	 * @param profileDTO The ProfileDTO object to be updated.
	 * @throws ValueObjectException if the value object is invalid.
	 */
	public void updateProfile(ProfileDTO profileDTO)
			throws ValueObjectException, IllegalImageFormatException, InvalidImageException {
		this.profileRepository.update(this.convertToProfile(profileDTO));
		this.profileRepository.saveProfileImage(profileDTO.imagePath());
	}

	/**
	 * Checks if a Profile object has been created.
	 * @return true if a Profile object has been created, false otherwise.
	 */
	public boolean isProfileCreated() {
		return this.profileRepository.isCreated();
	}

	/**
	 * Deletes a Profile object from the repository.
	 */
	public void deleteProfile() {
		this.profileRepository.delete();
	}

	/**
	 * Retrieves the image path of the profile.
	 * @return The image path of the profile.
	 */
	public String getProfileImagePath() {
		return this.profileRepository.getImagePath();
	}

	/**
	 * Retrieves the weight of the profile.
	 * @return The weight of the profile.
	 */
	public float getProfileWeight() {
		return this.profileRepository.getWeight();
	}

	/**
	 * Converts a ProfileDTO object to a Profile object.
	 * @param profileDTO The ProfileDTO object to be converted.
	 * @return The converted Profile object.
	 * @throws ValueObjectException if the value object is invalid.
	 */
	private Profile convertToProfile(ProfileDTO profileDTO) throws ValueObjectException {
		return new Profile(
				profileDTO.firstName(),
				profileDTO.lastName(),
				Sex.fromString(profileDTO.sex()),
				profileDTO.weight(),
				profileDTO.height(),
				profileDTO.birthDate());
	}

	/**
	 * Converts a Profile object to a ProfileDTO object.
	 * @param profile The Profile object to be converted.
	 * @return The converted ProfileDTO object.
	 */
	private ProfileDTO convertToProfileDTO(Profile profile) {
		return new ProfileDTO(
				profile.getFirstName(),
				profile.getLastName(),
				profile.getSex().toString(),
				profile.getWeight(),
				profile.getHeight(),
				profile.getBirthDate(),
				this.profileRepository.getImagePath());
	}
}
