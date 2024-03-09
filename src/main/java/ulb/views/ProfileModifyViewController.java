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
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private DatePicker birthdate;
	@FXML private TextField height;
	@FXML private TextField weight;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;
	private ProfileModifyViewController.Listener listener;
	System.Logger logger = System.getLogger(ProfileModifyViewController.class.getName());

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

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
			logger.log(System.Logger.Level.ERROR, "Height and weight must be numbers");
			return;
		}
		this.listener.returnHome();
	}

	public void setListener(Object listener) {
		this.listener = (ProfileModifyViewController.Listener) listener;
		this.setDefaultValue();
	}

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
