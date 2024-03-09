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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ProfileCreateViewController implements ViewController {

	// Injected fields from the FXML file
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private DatePicker birthdate;
	@FXML private TextField height;
	@FXML private TextField weight;
	@FXML private ToggleGroup sex; // radio button

	private ProfileCreateViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.birthdate.setValue(LocalDate.now()); // Set default birthdate to current date
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = this.lastname.getText();
			String savedFirstName = this.firstname.getText();
			String savedSex = ((RadioButton) this.sex.getSelectedToggle()).getText();
			LocalDate selectedDate = this.birthdate.getValue();
			float floatHeight = Float.parseFloat(this.height.getText());
			float floatWeight = Float.parseFloat(this.weight.getText());
			this.listener.saveProfile(
					savedFirstName,
					savedLastName,
					savedSex,
					selectedDate,
					floatHeight,
					floatWeight);
		} catch (NumberFormatException e) {
			// If height or weight is not a valid number, do nothing
			return;
		}
		this.listener.returnHome(); // Return to the home view after saving the profile
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	// Listener interface for communication with the controller
	public interface Listener {
		// Save profile information
		void saveProfile(
				String firstName,
				String lastName,
				String sex,
				LocalDate birthDate,
				float height,
				float weight);

		void returnHome(); // Return to the home view
	}
}
