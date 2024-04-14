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
package ulb.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import ulb.models.Height;
import ulb.models.Profile;
import ulb.models.Weight;
import ulb.models.enums.Sex;
import ulb.views.ProfileCreateViewController;

public class ProfileCreateController
		implements AppController, ProfileCreateViewController.Listener {

	private final ProfileCreateController.Listener listener;

	public ProfileCreateController(ProfileCreateController.Listener listener) {
		this.listener = listener;
	}

	public void saveProfile(
			String firstName,
			String lastName,
			String sex,
			LocalDate birthDate,
			float height,
			float weight) {
		Profile profile =
				new Profile(
						firstName,
						lastName,
						Sex.fromString(sex),
						new Weight(weight),
						new Height(height),
						birthDate);
		profile.save();
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void saveProfileImage(String imagePath) throws IOException {
		Files.copy(
				new URL(imagePath).openStream(),
				Paths.get("profile.png"),
				java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	}

	public interface Listener {

		void returnHome();
	}
}
