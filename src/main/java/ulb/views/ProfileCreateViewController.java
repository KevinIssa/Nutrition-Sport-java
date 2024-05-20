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
import java.util.Objects;
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

/**
 * Controller for the profile creation view.
 */
public class ProfileCreateViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileCreateViewController.class);
	@FXML private ImageView image;
	@FXML private Button imageSelection;
	@FXML
	private TextField firstname,
			lastname, // NOSONAR
			height,
			weight; // (java:S1659) - Multiple variable one line
	@FXML private DatePicker birthdate;
	@FXML private ToggleGroup sex;

	private String imagePath = null;
	private ProfileCreateViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.birthdate.setValue(LocalDate.now().minusYears(18));
		this.imagePath =
				Objects.requireNonNull(getClass().getResource("/ulb/images/default_profile.png"))
						.toString();
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
	 * Save the profile information entered by the user
	 */
	public void saveProfile() {
		try {
			ProfileDTO profile = createDTOProfile();
			this.listener.saveProfile(profile);
			this.listener.returnHome();

		} catch (ProfileParameterException e) {
			handleProfileParameterException(e);

		} catch (IllegalArgumentException | NullPointerException e) {
			handleIncompleteFieldsException();

		} catch (Exception e) {
			handleGeneralException(e);
		}
	}

	/**
	 * Creates a ProfileDTO object from the input fields in the view.
	 * It retrieves the text from the firstname, lastname, weight, and height fields,
	 * the selected value from the sex toggle group, the selected date from the birthdate picker,
	 * and the stored image path, and uses these values to create a new ProfileDTO object.
	 *
	 * @return A new ProfileDTO object with the data from the input fields.
	 */
	private ProfileDTO createDTOProfile() {
		return new ProfileDTO(
				this.firstname.getText(),
				this.lastname.getText(),
				((RadioButton) this.sex.getSelectedToggle()).getText(),
				Float.parseFloat(this.weight.getText()),
				Float.parseFloat(this.height.getText()),
				this.birthdate.getValue(),
				this.imagePath);
	}

	/**
	 * Handles the exception thrown when there is an error saving the profile due to invalid data.
	 * Logs the warning message and shows an alert to the user with the error message.
	 *
	 * @param e The ProfileParameterException that was thrown.
	 */
	private void handleProfileParameterException(ProfileParameterException e) {
		logger.warn("Error while saving profile", e);
		this.showAlert("Error de saisie", e.getMessage());
	}

	/**
	 * Handles the exception thrown when not all fields are filled.
	 * Logs a warning message and shows an alert to the user.
	 */
	private void handleIncompleteFieldsException() {
		logger.warn("All fields must be filled");
		this.showAlert("Erreur", "Tous les champs doivent être remplis.");
	}

	/**
	 * Handles the exception thrown when there is a general error saving the profile.
	 * Logs the error message and shows an alert to the user.
	 *
	 * @param e The Exception that was thrown.
	 */
	private void handleGeneralException(Exception e) {
		logger.error("Error while saving profile", e);
		this.showAlert(
				"Erreur inconnue",
				"Une erreur est survenue lors de la sauvegarde du profil. Veuillez réessayer."
						+ " Si le problème persiste, veuillez contacter le support et leur fournir"
						+ " le fichier de log.");
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	/**
	 * Listener interface for the ProfileCreateViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the ProfileCreateViewController.
	 */
	public interface Listener {
		/**
		 * Saves the profile when the "Save" button is clicked.
		 * This method should be implemented to handle the action of saving the profile.
		 *
		 * @param profileDTO The ProfileDTO object that contains the profile data to be saved.
		 * @throws ProfileParameterException If the profile data is not valid.
		 */
		void saveProfile(ProfileDTO profileDTO) throws ProfileParameterException;

		/**
		 * Returns to the home view when the "Cancel" button is clicked.
		 * This method should be implemented to handle the action of returning to the home view.
		 */
		void returnHome();
	}
}
