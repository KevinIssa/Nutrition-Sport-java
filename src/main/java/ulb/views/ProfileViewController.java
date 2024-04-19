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
import ulb.widgets.AbstractFieldTemplate;

public class ProfileViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileViewController.class);
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

	private void setImages() {
		Image pen = loadImage(this.getURL("/ulb/images/pen.png"), 15, 15);
		Image check = loadImage(this.getURL("/ulb/images/check.png"), 15, 15);
		this.fields.forEach(field -> field.setImages(pen, check));
	}

	private URL getURL(String path) {
		return this.getClass().getResource(path);
	}

	private Image loadImage(URL url, double width, double height) {
		return new Image(url.toString(), width, height, true, true);
	}

	public void setDefaultValue() {
		ImageView penView = new ImageView(loadImage(this.getURL("/ulb/images/pen.png"), 15, 15));
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(1);
		penView.setEffect(colorAdjust);
		this.imageSelection.setGraphic(penView);
		this.fields.forEach(AbstractFieldTemplate::setDefault);
		this.setProfileData();
		this.setProfileImage();
	}

	private void setProfileData() {
		ProfileDTO profileDTO = this.listener.getProfile();
		this.firstnameController.setLabelText(profileDTO.firstName());
		this.lastnameController.setLabelText(profileDTO.lastName());
		this.birthdateController.setLabelText(profileDTO.birthDate().toString());
		this.heightController.setLabelText(Float.toString(profileDTO.height()));
		this.weightController.setLabelText(Float.toString(profileDTO.weight()));
		this.sexController.setLabelText(profileDTO.sex());
	}

	public void setProfileImage() {
		try {
			Image image = loadImage(new File(listener.getProfileImagePath()).toURL(), 129, 125);
			this.profileImage.setImage(image);
		} catch (MalformedURLException e) {
			logger.error("Error loading profile image due to malformed URL {}", e.getMessage());
			System.exit(1);
		}
	}

	public void eventHandler(ActionEvent event) {
		File selectedFile =
				new FileChooser().showOpenDialog(this.imageSelection.getScene().getWindow());
		if (selectedFile != null) {
			try {
				this.profileImage.setImage(loadImage(selectedFile.toURL(), 129, 125));
				this.imagePath = selectedFile.toURI().toString();
			} catch (MalformedURLException e) {
				logger.error("Error loading profile image due to malformed URL {}", e.getMessage());
				System.exit(1);
			}
		}
	}

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
		} catch (NumberFormatException e) {
			logger.warn("Height and weight must be positive numbers");
		} catch (IllegalArgumentException | NullPointerException e) {
			logger.warn("All fields must be filled");
			//		} catch (IllegalImageFormatException e) {
			//			logger.warn("Image format not supported");
			//		} catch (InvalidImageException e) {
			//			logger.warn("Failed to save image");
			//		} catch (
			//				ImageException
			//						e) { // this should not be caught it should be caught in catch block above
			//			logger.warn("error while saving image");
		}
		this.listener.returnHome();
	}

	public void deleteProfile() {
		this.listener.deleteProfileView();
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		this.listener = (ProfileViewController.Listener) listener;
		this.setDefaultValue();
	}

	// Listener interface for communication with the controller
	public interface Listener {
		void updateProfile(ProfileDTO profileDTO);

		ProfileDTO getProfile();

		String getProfileImagePath();

		void deleteProfileView();

		void returnHome();
	}
}
