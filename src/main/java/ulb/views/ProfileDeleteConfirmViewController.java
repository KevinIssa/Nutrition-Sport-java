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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileDeleteConfirmViewController implements ViewController {

	private ProfileDeleteConfirmViewController.Listener
			listener; // Listener interface for communication with the controller

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	// Action when "Yes" button is clicked
	public void setYesButton() throws IOException {
		this.listener.deleteProfile();
	}

	// Action when "No" button is clicked
	public void setNoButton() {
		this.listener.returnHome();
	}

	// Set listener for button actions
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (ProfileDeleteConfirmViewController.Listener) listener;
	}

	// Listener interface for button actions
	public interface Listener {
		void deleteProfile() throws IOException; // Action when "Yes" button is clicked

		void returnHome(); // Action when "No" button is clicked
	}
}
