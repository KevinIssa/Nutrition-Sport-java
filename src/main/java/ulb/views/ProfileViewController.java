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
	private Image pen;
	private Image check;

	@FXML private ImageView profileimage;
	@FXML private Button imageselection;

	@FXML private AbstractFieldTemplate firstnameController;
	@FXML private AbstractFieldTemplate lastnameController;
	@FXML private AbstractFieldTemplate birthdateController;
	@FXML private AbstractFieldTemplate heightController;
	@FXML private AbstractFieldTemplate weightController;
	@FXML private AbstractFieldTemplate sexController;

	private List<AbstractFieldTemplate> bar;

	private String imagepath;

	private ProfileViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("Profile view initializing");
		this.bar =
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
		String pathpen = this.getPath("/ulb/images/pen.png");
		String pathcheck = this.getPath("/ulb/images/check.png");
		this.pen = new Image(pathpen, 15, 15, true, true);
		this.check = new Image(pathcheck, 15, 15, true, true);

		for (AbstractFieldTemplate foo : this.bar) {
			foo.setImages(pen, check);
		}
	}

	private String getPath(String path) {
		URL url = this.getClass().getResource(path);
		if (url == null) {
			throw new NullPointerException("Image not found");
		}
		return url.toString();
	}

	// Set default values from listener
	public void setDefaultValue() {
		ImageView penView = new ImageView(pen);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(1);
		penView.setEffect(colorAdjust);
		this.imageselection.setGraphic(penView);

		for (AbstractFieldTemplate foo : bar) {
			foo.setDefault();
		}

		this.firstnameController.setLabelText(this.listener.getFirstName());
		this.lastnameController.setLabelText(this.listener.getLastName());
		this.birthdateController.setLabelText(this.listener.getBirthDate().toString());
		this.heightController.setLabelText(Float.toString(this.listener.getHeight()));
		this.weightController.setLabelText(Float.toString(this.listener.getWeight()));
		this.sexController.setLabelText(this.listener.getSex());
		this.setProfileImage();
	}

	public void setProfileImage() {
		double desiredWidth = 200; // Desired width in pixels
		double desiredHeight = 150; // Desired height in pixels
		Image image =
				this.loadImage(this.listener.getProfileImagePath(), desiredWidth, desiredHeight);
		if ((image != null)) {
			this.profileimage.setImage(image);
		}
	}

	private Image loadImage(String filePath, double width, double height) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				return null;
			}
			URL path = file.toURL();
			return new Image(path.toString(), width, height, true, true);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public void eventHandler(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image File");
		File selectedFile = fileChooser.showOpenDialog(imageselection.getScene().getWindow());
		if (selectedFile != null) {
			Image image = new Image(selectedFile.toURI().toString());
			double desiredWidth = 129; // Desired width in pixels
			double desiredHeight = 125; // Desired height in pixels
			Image resizedImage = new Image(image.getUrl(), desiredWidth, desiredHeight, true, true);
			this.profileimage.setImage(resizedImage);
			this.imagepath = selectedFile.toURI().toString();
		}
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = lastnameController.getText();
			String savedFirstName = firstnameController.getText();
			String savedSex = this.sexController.getText();
			LocalDate localDate = LocalDate.parse(birthdateController.getText());
			float floatHeight = Float.parseFloat(heightController.getText());
			float floatWeight = Float.parseFloat(weightController.getText());
			this.listener.saveProfile(
					savedFirstName, savedLastName, savedSex, localDate, floatHeight, floatWeight);
			if (this.imagepath != null) {
				this.listener.saveProfileImage(this.imagepath);
			}
		} catch (NumberFormatException e) {
			// Log error if height and weight are not numbers
			System.err.println("Height and weight must be numbers");
			return;
		}
		this.listener.returnHome();
	}

	public void deleteProfile() {
		listener.deleteProfileView();
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
