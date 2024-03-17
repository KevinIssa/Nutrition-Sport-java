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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileConsultViewController implements ViewController {

	@FXML private Label firstname;
	@FXML private Label lastname;
	@FXML private Label sex;
	@FXML private Label birthdate;
	@FXML private Label height;
	@FXML private Label weight;

	private ProfileConsultViewController.Listener
			listener; // Listener interface for communication with the controller

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void setDefaultValue() {
		this.firstname.setText(listener.getFirstName());
		this.lastname.setText(listener.getLastName());
		this.birthdate.setText(birthDateToString(listener.getBirthDate()));
		this.height.setText(Float.toString(listener.getHeight()));
		this.weight.setText(Float.toString(listener.getWeight()));
		this.sex.setText(listener.getSex());
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void setListener(Object listener) {
		this.listener = (Listener) listener;
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.setDefaultValue();
	}

	public String birthDateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}
	public interface Listener {
		String getFirstName();

		String getLastName();

		String getSex();

		LocalDate getBirthDate();

		float getHeight();

		float getWeight();

		void returnHome(); // Return to the home page
	}
}
