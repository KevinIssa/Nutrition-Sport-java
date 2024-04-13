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
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ProfileCreateViewController implements ViewController {
	@FXML private ImageView image;
	@FXML private Button imageselection;
	@FXML private TextField firstname, lastname, height, weight;
	@FXML private DatePicker birthdate;
	@FXML private ToggleGroup sex;
	private String imagepath = null;
	private Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.birthdate.setValue(LocalDate.now());
	}

	/**
	 * Handles the event when a user interacts with the image selection button.
	 * Opens a file chooser dialog for the user to select an image file.
	 * If a file is selected, it sets the image view with the selected image and stores the image path.
	 *
	 * @param event The action event triggered by the user interaction.
	 */
	public void eventHandler(ActionEvent event) {
		File selectedFile = new FileChooser().showOpenDialog(imageselection.getScene().getWindow());
		if (selectedFile != null) {
			this.image.setImage(new Image(selectedFile.toURI().toString(), 200, 150, true, true));
			this.imagepath = selectedFile.toURI().toString();
		}
	}

	/**
	 * Saves the profile information entered by the user.
	 * After successfully saving the profile, it make the user return to the home view.
	 */
	public void saveProfile() {
		try {
			this.listener.saveProfile(
					this.firstname.getText(),
					this.lastname.getText(),
					((RadioButton) this.sex.getSelectedToggle()).getText(),
					this.birthdate.getValue(),
					Float.parseFloat(this.height.getText()),
					Float.parseFloat(this.weight.getText()));
			if (this.imagepath != null) {
				this.listener.saveProfileImage(this.imagepath);
			}
		} catch (NumberFormatException | IOException e) {
			return;
		}
		this.listener.returnHome();
	}

	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
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

		void saveProfileImage(String image) throws IOException;
	}
}
