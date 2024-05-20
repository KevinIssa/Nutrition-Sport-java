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
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ProfileDTO;
import ulb.exceptions.ProfileParameterException;
import ulb.widgets.AbstractFieldTemplate;

/**
 * Controller for the profile view.
 */
public class ProfileViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileViewController.class);

	public static final String PEN_PNG = "/ulb/images/pen.png";
	public static final String CHECK_PNG = "/ulb/images/check.png";

	@FXML private ImageView profileImage;
	@FXML private Button imageSelection;
	@FXML private AbstractFieldTemplate firstnameController;
	@FXML private AbstractFieldTemplate lastnameController;
	@FXML private AbstractFieldTemplate birthdateController;
	@FXML private AbstractFieldTemplate heightController;
	@FXML private AbstractFieldTemplate weightController;
	@FXML private AbstractFieldTemplate sexController;

	private List<AbstractFieldTemplate> fields;
	private String imagePath;
	private ProfileViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.fields =
				new ArrayList<>(
						Arrays.asList(
								firstnameController,
								lastnameController,
								birthdateController,
								heightController,
								weightController,
								sexController));
		this.setImages();
	}

	/**
	 * Sets the images for the fields.
	 * It loads the pen and check images and sets them for each field.
	 */
	private void setImages() {
		Image pen = loadImage(this.getURL(PEN_PNG), 15, 15);
		Image check = loadImage(this.getURL(CHECK_PNG), 15, 15);
		this.fields.forEach(field -> field.setImages(pen, check));
	}

	/**
	 * Returns the URL of the resource at the specified path.
	 *
	 * @param path The path of the resource.
	 * @return The URL of the resource.
	 */
	private URL getURL(String path) {
		return this.getClass().getResource(path);
	}

	/**
	 * Loads an image from the specified URL with the specified dimensions.
	 *
	 * @param url The URL of the image.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 * @return The loaded image.
	 */
	private Image loadImage(URL url, double width, double height) {
		return new Image(url.toString(), width, height, true, true);
	}

	/**
	 * Sets the default values for the view.
	 * It sets the pen image for the image selection button, sets the default values for the fields,
	 * and sets the profile data and image.
	 */
	public void setDefaultValue() {
		ImageView penView = new ImageView(loadImage(this.getURL(PEN_PNG), 15, 15));
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(1);
		penView.setEffect(colorAdjust);
		this.imageSelection.setGraphic(penView);
		this.fields.forEach(AbstractFieldTemplate::setDefault);
		this.setProfileData();
		this.setProfileImage();
	}

	/**
	 * Sets the profile data in the view.
	 * It gets the current profile data from the listener and sets the labels of the fields accordingly.
	 */
	private void setProfileData() {
		ProfileDTO profileDTO = this.listener.getProfile();
		this.imagePath = profileDTO.imagePath();
		this.firstnameController.setLabelText(profileDTO.firstName());
		this.lastnameController.setLabelText(profileDTO.lastName());
		this.birthdateController.setLabelText(profileDTO.birthDate().toString());
		this.heightController.setLabelText(Float.toString(profileDTO.height()));
		this.weightController.setLabelText(Float.toString(profileDTO.weight()));
		this.sexController.setLabelText(profileDTO.sex());
	}

	/**
	 * Handles the exception thrown when there is an error loading the profile image due to a malformed URL.
	 * Logs the error message, shows an alert to the user, and then exits the system.
	 *
	 * @param e The MalformedURLException that was thrown.
	 */
	private void handleImageLoadingError(MalformedURLException e) {
		logger.error("Error loading profile image due to malformed URL {}", e.getMessage());
		this.showAlert(
				"Erreur de chargement",
				"Une erreur inconnue s'est produite. veuillez contacter le support et leur"
						+ " fournir le fichier de log car une erreur inconnue s'est produit.");
		System.exit(1);
	}

	/**
	 * Sets the profile image.
	 * Tries to load the image from the file at the path returned by the listener's getProfileImagePath method.
	 * If a MalformedURLException is thrown, it is handled by the handleImageLoadingError method.
	 */
	public void setProfileImage() {
		try {
			Image image = loadImage(new File(listener.getProfileImagePath()).toURL(), 129, 125);
			this.profileImage.setImage(image);
		} catch (MalformedURLException e) {
			handleImageLoadingError(e);
		}
	}

	/**
	 * Handles the event when the user selects a new profile image.
	 * Opens a file chooser for the user to select a new image file.
	 * If a file is selected, tries to load the image from the file and set it as the profile image.
	 * If a MalformedURLException is thrown, it is handled by the handleImageLoadingError method.
	 *
	 * @param ignored The ActionEvent that triggered this method.
	 */
	public void eventHandler(ActionEvent ignored) { // NOSONAR
		File selectedFile =
				new FileChooser().showOpenDialog(this.imageSelection.getScene().getWindow());
		if (selectedFile != null) {
			try {
				this.profileImage.setImage(loadImage(selectedFile.toURL(), 129, 125));
				this.imagePath = selectedFile.toURI().toString();
			} catch (MalformedURLException e) {
				handleImageLoadingError(e);
			}
		}
	}

	/**
	 * Handles the exception thrown when there is an error updating the profile due to invalid data.
	 * Logs the error message and shows an alert to the user with the error message.
	 *
	 * @param e The ProfileParameterException that was thrown.
	 */
	private void handleProfileParameterException(ProfileParameterException e) {
		logger.warn("Error while updating profile", e);
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

	/**
	 * This method is used to save the profile data.
	 * It creates a new ProfileDTO object with the data from the form fields, then calls the listener's updateProfile method to save the data.
	 * If all fields are filled and the data is valid, it returns to the home view.
	 * If any fields are empty or null, it handles the exception and shows an error message.
	 * If the data is not valid, it handles the ProfileParameterException and shows an error message.
	 * If any other exception occurs, it handles the general exception and shows an error message.
	 */
	public void saveProfile() {
		try {
			ProfileDTO profileDTO =
					new ProfileDTO(
							this.firstnameController.getText(),
							this.lastnameController.getText(),
							this.sexController.getText(),
							Float.parseFloat(this.weightController.getText()),
							Float.parseFloat(this.heightController.getText()),
							LocalDate.parse(this.birthdateController.getText()),
							this.imagePath);

			this.listener.updateProfile(profileDTO);
			this.returnHome();

		} catch (IllegalArgumentException | NullPointerException e) {
			handleIncompleteFieldsException();

		} catch (ProfileParameterException e) {
			handleProfileParameterException(e);

		} catch (Exception e) {

			handleGeneralException(e);
		}
	}

	@FXML
	public void deleteProfile() {
		this.listener.deleteProfileView();
	}

	@FXML
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void setListener(Object listener) {
		this.listener = (ProfileViewController.Listener) listener;
		this.setDefaultValue();
	}

	/**
	 * This is an interface for communication with the controller.
	 * It provides methods for updating the profile, getting profile data, deleting the profile view, and returning to the home view.
	 */
	public interface Listener {
		/**
		 * Updates the profile with the given ProfileDTO.
		 *
		 * @param profileDTO The ProfileDTO containing the new profile data.
		 * @throws ProfileParameterException If an error occurs while updating the profile.
		 */
		void updateProfile(ProfileDTO profileDTO) throws ProfileParameterException;

		/**
		 * Gets the current profile data.
		 *
		 * @return The current ProfileDTO.
		 */
		ProfileDTO getProfile();

		/**
		 * Gets the path of the profile image.
		 *
		 * @return The path of the profile image.
		 */
		String getProfileImagePath();

		/**
		 * Deletes the profile view.
		 */
		void deleteProfileView();

		/**
		 * Returns to the home view.
		 */
		void returnHome();
	}
}
