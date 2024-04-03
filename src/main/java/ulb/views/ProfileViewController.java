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
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ulb.views.utils.FieldTemplateController;
import ulb.views.utils.AbstractFieldTemplate;

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


	@FXML private Label sex_label;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;
	@FXML private Button sexswitch;

	private String imagepath;

	private ProfileViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("Profile view initializing");
		this.setImages();
		this.maleButton.setVisible(false);
		this.femaleButton.setVisible(false);
		this.sex_label.setVisible(true);
		System.out.println("Profile view initialized");
	}

	private void setImages() {
		String  pathpen = this.getPath("/ulb/images/pen.png");
		String pathcheck = this.getPath("/ulb/images/check.png");
		this.pen = new Image(pathpen, 15, 15, true, true);
		this.check = new Image(pathcheck, 15, 15, true, true);

		this.firstnameController.setImages(pen, check);
		this.lastnameController.setImages(pen, check);
		this.birthdateController.setImages(pen, check);
		this.heightController.setImages(pen, check);
		this.weightController.setImages(pen, check);

		System.out.println("Images loaded");
		/*
		for (AbstractFieldTemplate foo : this.bar) {
			foo.setImages(pen, check);
		}
		*/
	}
	private String getPath(String path){
		URL url = this.getClass().getResource(path);
		if (url == null) {
			throw new NullPointerException("Image not found");
		}
		return url.toString();
	}
	// Set default values from listener
	public void setDefaultValue() {;
		this.imageselection.setGraphic(new ImageView(pen));
		this.sexswitch.setGraphic(new ImageView(pen));

		this.firstnameController.setDefault();
		this.lastnameController.setDefault();
		this.birthdateController.setDefault();
		this.heightController.setDefault();
		this.weightController.setDefault();

		this.firstnameController.setLabelText(this.listener.getFirstName());
		this.lastnameController.setLabelText(this.listener.getLastName());
		this.birthdateController.setLabelText(this.listener.getBirthDate().toString());
		this.heightController.setLabelText(Float.toString(this.listener.getHeight()));
		this.weightController.setLabelText(Float.toString(this.listener.getWeight()));


		this.sex_label.setText(listener.getSex());
		this.setProfileImage();
	}

	public void setProfileImage() {
		double desiredWidth = 200; // Desired width in pixels
		double desiredHeight = 150; // Desired height in pixels
		Image image = this.listener.getProfileImage(desiredWidth, desiredHeight);
		if ((image != null)) {
			this.profileimage.setImage(image);
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

	public void switcher(ActionEvent event) {
		Node node = (Node) event.getSource();
		String id = node.getId();
		/*
		for (AbstractFieldTemplate foo : bar) { //* need to be optimized
			if (Objects.equals(foo._button.getId(), id)) {
				foo.toggleMode();
				break;
			}
		}
		*/
		switch (id) {
			case "sexswitch":
				if (Objects.equals(this.sexswitch.getText(), "Edit")) {
					this.sexswitch.setText("Done");
					this.sexswitch.setGraphic(new ImageView(check));
					this.maleButton.setVisible(true);
					this.femaleButton.setVisible(true);
					this.sex_label.setVisible(false);
					if (Objects.equals(this.sex_label.getText(), "♂")) {
						this.maleButton.setSelected(true);
						this.femaleButton.setSelected(false);
					} else {
						this.maleButton.setSelected(false);
						this.femaleButton.setSelected(true);
					}

				} else {
					this.sexswitch.setText("Edit");
					this.sexswitch.setGraphic(new ImageView(pen));
					this.maleButton.setVisible(false);
					this.femaleButton.setVisible(false);
					this.sex_label.setVisible(true);
					if (this.maleButton.isSelected()) {
						this.sex_label.setText("♂");
					} else {
						this.sex_label.setText("♀");
					}
				}
				break;
				// more cases can be added as needed
			case "_firstname_button":
				this.firstnameController.toggleMode();
				break;
			case "_lastname_button":
				this.lastnameController.toggleMode();
				break;
			case "_birthdate_button":
				this.birthdateController.toggleMode();
				break;
			case "_height_button":
				this.heightController.toggleMode();
				break;
			case "_weight_button":
				this.weightController.toggleMode();
				break;
			default:

				throw new IllegalStateException("Unexpected value: " + id);
				// code to be executed if expression doesn't match any case
		}
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = lastnameController.getText();
			String savedFirstName = firstnameController.getText();
			String savedSex = this.sex_label.getText();
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

		void returnHome();

		String getFirstName();

		String getLastName();

		String getSex();

		LocalDate getBirthDate();

		float getHeight();

		float getWeight();

		void saveProfileImage(String imagepath);

		Image getProfileImage(double width, double height);
	}
}
