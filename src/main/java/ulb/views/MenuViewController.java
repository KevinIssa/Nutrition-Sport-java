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
package ulb.views;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuViewController implements ViewController {
	@FXML ImageView profileimage;

	private MenuViewController.Listener
			listener; // Listener interface for communication with the controller

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void openProfile() {
		this.listener.loadOpenProfileView();
	}

	public void deleteProfile() {
		this.listener.loadDeleteProfileView();
	}

	public void createActivity() {
		this.listener.loadCreateActivityView();
	}

	public void activityHistory() {
		this.listener.loadActivityHistoryView();
	}

	public void setListener(Object listener) {
		this.listener = (Listener) listener;
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.setdefault();
	}

	public void setdefault() {
		Image image = this.listener.getProfileImage(30, 30);
		if (image != null) {
			this.profileimage.setImage(image);
		}
	}

	public void foodSearchPage() {
		this.listener.loadFoodSearchPage();
	}

	public interface Listener {

		void loadCreateActivityView(); // Load the create activity view

		void loadOpenProfileView(); // Load the modify profile view

		void loadDeleteProfileView(); // Load the delete profile view

		void loadCreateProfileView(); // Load the create profile view

		void loadMenuView(); // Load the menu view

		void loadWelcomeView(); // Load the welcome view

		void loadActivityHistoryView(); // Load the activity history view

		void loadFoodSearchPage();

		 default Image getProfileImage(double width, double height) {
			try {
				File file = new File("profile.png");
				if (!file.exists()){
					return null;
				}
				URL path = file.toURL();
				return new Image(path.toString(), width, height, true, true);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
