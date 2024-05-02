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
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ProfileDTO;
import ulb.exceptions.ProfileParameterException;

public class ProfileCreateViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileCreateViewController.class);
	@FXML private ImageView image;
	@FXML private Button imageSelection;
	@FXML private TextField firstname, lastname, height, weight;
	@FXML private DatePicker birthdate;
	@FXML private ToggleGroup sex;

	private String imagePath = null;
	private ProfileCreateViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.birthdate.setValue(LocalDate.now().minusYears(18));
		this.imagePath = getClass().getResource("/ulb/images/default_profile.png").toString();
	}

	/**
	 * Handles the event when a user interacts with the image selection button.
	 * Opens a file chooser dialog for the user to select an image file.
	 * If a file is selected, it sets the image view with the selected image and stores the image path.
	 */
	public void chooseImage() {
		File selectedFile =
				new FileChooser().showOpenDialog(this.imageSelection.getScene().getWindow());
		if (selectedFile != null) {
			this.image.setImage(new Image(selectedFile.toURI().toString(), 200, 150, true, true));
			this.imagePath = selectedFile.toURI().toString();
		}
	}

	/**
	 * Saves the profile information entered by the user.
	 * After successfully saving the profile, it make the user return to the home view.
	 */
	public void saveProfile() {
		try {
			ProfileDTO profile =
					new ProfileDTO(
							this.firstname.getText(),
							this.lastname.getText(),
							((RadioButton) this.sex.getSelectedToggle()).getText(),
							Float.parseFloat(this.weight.getText()),
							Float.parseFloat(this.height.getText()),
							this.birthdate.getValue(),
							this.imagePath);
			this.listener.saveProfile(profile);
			this.listener.returnHome();
		} catch (ProfileParameterException e) {
			logger.warn("Error while saving profile", e);
			this.showAlert("Error de saisie", e.getMessage());
		} catch (IllegalArgumentException | NullPointerException e) {
			logger.warn("All fields must be filled");
			this.showAlert("Erreur", "Tous les champs doivent être remplis.");
		} catch (Exception e) {
			logger.error("Error while saving profile", e);
			this.showAlert(
					"Erreur",
					"Une erreur est survenue lors de la sauvegarde du profil. Veuillez réessayer."
						+ " Si le problème persiste, veuillez contacter le support et leur fournir"
						+ " le fichier de log.");
		}
	}

	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	public interface Listener {
		void saveProfile(ProfileDTO profileDTO) throws ProfileParameterException;

		void returnHome();
	}
}
