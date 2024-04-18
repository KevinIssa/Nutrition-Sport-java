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
import ulb.models.Profile;
import ulb.repositories.ProfileRepository;

public class ProfileServiceImpl implements ProfileService {
	private final ProfileRepository profileRepository;

	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public void saveProfile(ProfileDTO profileDTO) {
		this.profileRepository.save(this.convertToProfile(profileDTO));
		this.profileRepository.saveProfileImage(profileDTO.imagePath());
	}

	@Override
	public ProfileDTO loadProfile() {
		return this.convertToProfileDTO(this.profileRepository.load());
	}

	@Override
	public void updateProfile(ProfileDTO profileDTO) {
		this.profileRepository.update(this.convertToProfile(profileDTO));
	}

	@Override
	public boolean isProfileCreated() {
		return this.profileRepository.isCreated();
	}

	@Override
	public void deleteProfile() {}

	@Override
	public String getProfileImagePath() {
		return this.profileRepository.getImagePath();
	}

	private Profile convertToProfile(ProfileDTO profileDTO) {
		return new Profile(
				profileDTO.firstName(),
				profileDTO.lastName(),
				Sex.fromString(profileDTO.sex()),
				profileDTO.weight(),
				profileDTO.height(),
				profileDTO.birthDate());
	}

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
