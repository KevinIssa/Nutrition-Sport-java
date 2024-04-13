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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import ulb.models.Profile;
import ulb.models.enums.Sex;
import ulb.views.ProfileCreateViewController;

public class ProfileCreateController
		implements AppController, ProfileCreateViewController.Listener {

	private final ProfileCreateController.Listener listener;

	public ProfileCreateController(ProfileCreateController.Listener listener) {
		this.listener = listener;
	}

	@Override
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
						new ulb.models.Weight(weight),
						new ulb.models.Height(height),
						birthDate);
		profile.save();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void saveProfileImage(String imagePath) throws IOException {
		try {
			URL imageurl = new URL(imagePath);
			URI destinationuri = new File("profile.png").toURI();
			Path destinationpath = Paths.get(destinationuri);
			Files.copy(imageurl.openStream(), destinationpath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public interface Listener {

		void returnHome();
	}
}
