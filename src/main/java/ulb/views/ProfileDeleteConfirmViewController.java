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

/**
 * Controller for the profile deletion confirmation view.
 */
public class ProfileDeleteConfirmViewController implements ViewController {

	private ProfileDeleteConfirmViewController.Listener
			listener; // Listener interface for communication with the controller

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Empty method,no initialization needed (java:S1186)
	}

	/**
	 * This method is triggered when the "Yes" button is clicked.
	 * It calls the deleteProfile method of the listener to delete the profile.
	 *
	 * @throws IOException If an input or output exception occurred
	 */
	public void setYesButton() throws IOException {
		this.listener.deleteProfile();
	}

	/**
	 * This method is triggered when the "No" button is clicked.
	 * It calls the returnHome method of the listener to return to the home view.
	 */
	public void setNoButton() {
		this.listener.returnHome();
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (ProfileDeleteConfirmViewController.Listener) listener;
	}

	/**
	 * Listener interface for handling button actions in the ProfileDeleteConfirmViewController.
	 */
	public interface Listener {
		/**
		 * Deletes the profile when the "Yes" button is clicked.
		 * This method should be implemented to handle the action of deleting the profile.
		 *
		 * @throws IOException If an input or output exception occurred
		 */
		void deleteProfile() throws IOException;

		/**
		 * Returns to the home view when the "No" button is clicked.
		 * This method should be implemented to handle the action of returning to the home view.
		 */
		void returnHome();
	}
}
