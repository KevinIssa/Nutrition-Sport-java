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
import ulb.views.templates.AbstractFieldTemplate;

public class ProfileViewController implements ViewController {
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
		this.firstnameController.setLabelText(this.listener.getFirstName());
		this.lastnameController.setLabelText(this.listener.getLastName());
		this.birthdateController.setLabelText(this.listener.getBirthDate().toString());
		this.heightController.setLabelText(Float.toString(this.listener.getHeight()));
		this.weightController.setLabelText(Float.toString(this.listener.getWeight()));
		this.sexController.setLabelText(this.listener.getSex());
	}

	public void setProfileImage() {
		try {
			Image image = loadImage(new File(listener.getProfileImagePath()).toURL(), 200, 150);
			this.profileImage.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void eventHandler(ActionEvent event) {
		File selectedFile =
				new FileChooser().showOpenDialog(this.imageSelection.getScene().getWindow());
		if (selectedFile != null) {
			try {
				this.profileImage.setImage(loadImage(selectedFile.toURL(), 129, 125));
				this.imagePath = selectedFile.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void saveProfile() {
		try {
			this.listener.saveProfile(
					this.firstnameController.getText(),
					this.lastnameController.getText(),
					this.sexController.getText(),
					LocalDate.parse(this.birthdateController.getText()),
					Float.parseFloat(this.heightController.getText()),
					Float.parseFloat(this.weightController.getText()));
			this.listener.saveProfileImage(this.imagePath);
		} catch (NumberFormatException e) {
			System.err.println("Height and weight must be numbers");
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
		void saveProfile(
				String firstName,
				String lastName,
				String sex,
				LocalDate birthDate,
				float height,
				float weight);

		void deleteProfileView();

		void returnHome();

		String getFirstName();

		String getLastName();

		String getSex();

		LocalDate getBirthDate();

		float getHeight();

		float getWeight();

		void saveProfileImage(String imagePath);

		String getProfileImagePath();
	}
}
