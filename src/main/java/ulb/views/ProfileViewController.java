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

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;
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

public class ProfileViewController implements ViewController {
	static final Image pen = new Image("/ulb/images/pen.png", 15, 15, true, true);
	static final Image check = new Image("/ulb/images/check.png", 15, 15, true, true);
	public class Foo { //TODO : Rename this class "this class is a container for the textfield, label and button"
		public TextField _txtField;
		public Label _label;
		public Button _button;

		private boolean mode = false; // folse is done, true is edit

		public void setDefault() {
			_txtField.setVisible(false);
			_label.setVisible(true);
		}

		public void setGraphic(ImageView imageView) {
			_button.setGraphic(imageView);
		}

		public void setLabelText(String text) {
			_label.setText(text);
		}

		public void toggleMode() {
            if (this.mode) {
                this.setEditMode(); // from done to edit
            } else {
                this.setDoneMode(); // from edit to done
            }
        }

		private void setEditMode() {
			_button.setText("Done");
			setGraphic(new ImageView(check));
			_txtField.setVisible(true);
			_label.setVisible(false);
			_txtField.setText(_label.getText());
			mode = true;
		}

		private void setDoneMode() {
			_button.setText("Edit");
			setGraphic(new ImageView(pen));
			_txtField.setVisible(false);
			_label.setVisible(true);
			_label.setText(_txtField.getText());
			mode = false;
		}

	}
	@FXML private ImageView profileimage;
	@FXML private Button imageselection;
	@FXML private Foo firstname;
	@FXML private Foo lastname;
	@FXML private Foo birthdate;
	@FXML private Foo height;
	@FXML private Foo weight;

	@FXML private Label sex_label;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;
	@FXML private Button sexswitch;

	private ArrayList<Foo> bar = new ArrayList<>(Collection.of(this.firstname, this.lastname, this.birthdate, this.height, this.weight));
	//TODO : Rename this variable, this variable has for objective to apply the same method to all Foo objects
	private String imagepath;

	private ProfileViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		for (Foo foo : bar) {
			foo.setDefault();
		}
		this.maleButton.setVisible(false);
		this.femaleButton.setVisible(false);
		this.sex_label.setVisible(true);
	}

	// Set default values from listener
	public void setDefaultValue() {;
		this.imageselection.setGraphic(new ImageView(pen));
		this.sexswitch.setGraphic(new ImageView(pen));
		for (Foo foo : this.bar) {
			foo.setGraphic(new ImageView(pen));
			foo.setLabelText(...); //TODO : get the right function to get the right value
		}
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
		for (Foo foo : bar) { //* need to be optimized
			if (Objects.equals(foo._button.getId(), id)) {
				foo.toggleMode();
			}
		}
		switch (id) {
			case "birthdateswitch":
				if (Objects.equals(this.birthdateswitch.getText(), "Edit")) {
					this.birthdateswitch.setText("Done");
					this.birthdateswitch.setGraphic(new ImageView(check));
					this.birthdate_text.setVisible(true);
					this.birthdate_label.setVisible(false);

					this.birthdate_text.setValue(
							LocalDate.parse(
									birthdate_label.getText(),
									DateTimeFormatter.ofPattern("yyyy-MM-d")));
				} else {
					this.birthdateswitch.setText("Edit");
					this.birthdateswitch.setGraphic(new ImageView(pen));
					this.birthdate_text.setVisible(false);
					this.birthdate_label.setVisible(true);
					this.birthdate_label.setText(this.birthdate_text.getValue().toString());
				}
				break;
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
			default:
				throw new IllegalStateException("Unexpected value: " + id);
				// code to be executed if expression doesn't match any case
		}
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = lastname_label.getText();
			String savedFirstName = firstname_label.getText();
			String savedSex = this.sex_label.getText();
			LocalDate localDate =
					LocalDate.parse(
							birthdate_label.getText(), DateTimeFormatter.ofPattern("yyyy-MM-d"));
			float floatHeight = Float.parseFloat(height_label.getText());
			float floatWeight = Float.parseFloat(weight_label.getText());
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

		Image getImage(String relativePath, double width, double height);

		void saveProfileImage(String imagepath);

		Image getProfileImage(double width, double height);
	}
}
