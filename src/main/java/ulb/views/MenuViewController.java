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

import java.net.URL;
import java.util.ResourceBundle;

public class MenuViewController implements ViewController {

	private MenuViewController.Listener
			listener; // Listener interface for communication with the controller

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void consultProfile() {
		this.listener.loadConsultProfileView();
	}

	public void modifyProfile() {
		this.listener.loadModifyProfileView();
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
	}

	public interface Listener {
		void loadConsultProfileView(); // Load the consult profile view

		void loadCreateActivityView(); // Load the create activity view

		void loadModifyProfileView(); // Load the modify profile view

		void loadDeleteProfileView(); // Load the delete profile view

		void loadCreateProfileView(); // Load the create profile view

		void loadMenuView(); // Load the menu view

		void loadWelcomeView(); // Load the welcome view

		void loadActivityHistoryView(); // Load the activity history view
	}
}
