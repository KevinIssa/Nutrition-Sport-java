/* (C)2024 */
package ulb.views;

import java.net.URL;
import java.time.LocalDate;
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

	private ProfileConsultViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// if we put setter here, the initialize will be executed before setListener so listener is
		// null
	}

	public void setDefaultValue() {
		this.firstname.setText(listener.getFirstName());
		this.lastname.setText(listener.getLastName());
		this.birthdate.setText(listener.getBirthDate().toString());
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

	public interface Listener {
		String getFirstName();

		String getLastName();

		String getSex();

		LocalDate getBirthDate();

		float getHeight();

		float getWeight();

		void returnHome();
	}
}
