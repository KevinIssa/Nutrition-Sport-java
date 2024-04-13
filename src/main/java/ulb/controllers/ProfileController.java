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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import ulb.models.Profile;
import ulb.models.enums.Sex;
import ulb.views.ProfileViewController;

public class ProfileController implements AppController, ProfileViewController.Listener {

	private final Profile profile = Profile.load();
	private final ProfileController.Listener listener;

	public ProfileController(ProfileController.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void saveProfile(
			String firstName,
			String lastName,
			String sex,
			java.time.LocalDate birthDate,
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
	public void deleteProfileView() {
		this.listener.deleteProfile();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public String getFirstName() {
		return profile.getFirstName();
	}

	@Override
	public String getLastName() {
		return profile.getLastName();
	}

	@Override
	public String getSex() {
		return profile.getSex().toString();
	}

	@Override
	public java.time.LocalDate getBirthDate() {
		return profile.getBirthDate();
	}

	@Override
	public float getHeight() {
		return profile.getHeight();
	}

	@Override
	public float getWeight() {
		return profile.getWeight();
	}

	@Override
	public void saveProfileImage(String imagepath) {
		try {
			URL imageurl = new URL(imagepath);
			URI destinationuri = new File("profile.png").toURI();
			Path destinationpath = Paths.get(destinationuri);
			Files.copy(imageurl.openStream(), destinationpath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Image getProfileImage(double width, double height) {
		try {
			File file = new File("profile.png");
			if (!file.exists()) {
				return null;
			}
			URL path = file.toURL();
			return new Image(path.toString(), width, height, true, true);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public interface Listener {
		void deleteProfile();

		void returnHome();
	}
}
