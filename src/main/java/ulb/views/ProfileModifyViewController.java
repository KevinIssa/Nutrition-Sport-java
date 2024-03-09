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

public class ProfileModifyViewController implements ViewController {

	// Injected fields from the FXML file
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private DatePicker birthdate;
	@FXML private TextField height;
	@FXML private TextField weight;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;

	private ProfileModifyViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	// Set default values from listener
	public void setDefaultValue() {
		this.firstname.setText(listener.getFirstName());
		this.lastname.setText(listener.getLastName());
		this.birthdate.setValue(listener.getBirthDate());
		this.height.setText(Float.toString(listener.getHeight()));
		this.weight.setText(Float.toString(listener.getWeight()));
		if (listener.getSex().equals("♂")) {
			maleButton.setSelected(true);
		} else {
			femaleButton.setSelected(true);
		}
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = lastname.getText();
			String savedFirstName = firstname.getText();
			String savedSex = ((RadioButton) this.sex.getSelectedToggle()).getText();
			LocalDate selectedDate = birthdate.getValue();
			float floatHeight = Float.parseFloat(height.getText());
			float floatWeight = Float.parseFloat(weight.getText());
			this.listener.saveProfile(
					savedFirstName,
					savedLastName,
					savedSex,
					selectedDate,
					floatHeight,
					floatWeight);
		} catch (NumberFormatException e) {
			// Log error if height and weight are not numbers
			System.err.println("Height and weight must be numbers");
			return;
		}
		this.listener.returnHome();
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		this.listener = (ProfileModifyViewController.Listener) listener;
		this.setDefaultValue();
	}

	// Listener interface for communication with the controller
	public interface Listener {
		void saveProfile(
				String firstName,
				String lastName,
				String sex,
				LocalDate birthDate,
				float height,
				float weight);

		void returnHome();

		String getFirstName();

		String getLastName();

		String getSex();

		LocalDate getBirthDate();

		float getHeight();

		float getWeight();
	}
}
