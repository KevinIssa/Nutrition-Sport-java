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
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class ProfileViewController implements ViewController {
	@FXML private ImageView profileimage;
	@FXML private TextField firstname_text;
	@FXML private Label firstname_label;
	@FXML private Button firstnameswitch;

	@FXML private TextField lastname_text;
	@FXML private Label lastname_label;
	@FXML private Button lastnameswitch;

	@FXML private DatePicker birthdate_text;
	@FXML private Label birthdate_label;
	@FXML private Button birthdateswitch;

	@FXML private TextField height_text;
	@FXML private Label height_label;
	@FXML private Button heightswitch;

	@FXML private TextField weight_text;
	@FXML private Label weight_label;
	@FXML private Button weightswitch;

	@FXML private Label sex_label;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;
	@FXML private Button sexswitch;

	private Image pen;
	private Image check;

	private ProfileViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.firstname_text.setVisible(false);
		this.firstname_label.setVisible(true);
		this.lastname_text.setVisible(false);
		this.lastname_label.setVisible(true);
		this.birthdate_text.setVisible(false);
		this.birthdate_label.setVisible(true);
		this.height_text.setVisible(false);
		this.height_label.setVisible(true);
		this.weight_text.setVisible(false);
		this.weight_label.setVisible(true);
		this.maleButton.setVisible(false);
		this.femaleButton.setVisible(false);
		this.sex_label.setVisible(true);
	}

	// Set default values from listener
	public void setDefaultValue() {
		String relativePath = "images/pen.png";
		double desiredWidth = 15; // Desired width in pixels
		double desiredHeight = 15; // Desired height in pixels
		this.pen = this.listener.getImage(relativePath, desiredWidth, desiredHeight);
		relativePath = "images/check.png";
		this.check  = this.listener.getImage(relativePath, desiredWidth, desiredHeight);

		this.firstnameswitch.setGraphic(new ImageView(pen));
		this.lastnameswitch.setGraphic(new ImageView(pen));
		this.birthdateswitch.setGraphic(new ImageView(pen));
		this.sexswitch.setGraphic(new ImageView(pen));
		this.heightswitch.setGraphic(new ImageView(pen));
		this.weightswitch.setGraphic(new ImageView(pen));

		this.firstname_label.setText(listener.getFirstName());
		this.lastname_label.setText(listener.getLastName());
		this.birthdate_label.setText(listener.getBirthDate().toString());
		this.height_label.setText(Float.toString(listener.getHeight()));
		this.weight_label.setText(Float.toString(listener.getWeight()));
		this.sex_label.setText(listener.getSex());
		this.setProfileImage();
	}
	public void setProfileImage(){
		String relativePath = "images/profile.png";
		double desiredWidth = 200; // Desired width in pixels
		double desiredHeight = 150; // Desired height in pixels
		Image image = this.listener.getImage(relativePath, desiredWidth, desiredHeight);
		if ((image != null)){
			this.profileimage.setImage(image);
		}
	}
	public void switcher(ActionEvent event){
		Node node = (Node) event.getSource();
		String id = node.getId();
		switch (id) {
			case "firstnameswitch":
				if (Objects.equals(this.firstnameswitch.getText(), "Edit")){
					this.firstnameswitch.setText("Done");
					this.firstnameswitch.setGraphic(new ImageView(check));
					this.firstname_text.setVisible(true);
					this.firstname_label.setVisible(false);
					this.firstname_text.setText(this.firstname_label.getText());
				}else{
					this.firstnameswitch.setText("Edit");
					this.firstnameswitch.setGraphic(new ImageView(pen));
					this.firstname_text.setVisible(false);
					this.firstname_label.setVisible(true);
					this.firstname_label.setText(this.firstname_text.getText());
				}
				break;
			case "lastnameswitch":
				if (Objects.equals(this.lastnameswitch.getText(), "Edit")){
					this.lastnameswitch.setText("Done");
					this.lastnameswitch.setGraphic(new ImageView(check));
					this.lastname_text.setVisible(true);
					this.lastname_label.setVisible(false);
					this.lastname_text.setText(this.lastname_label.getText());
				}else{
					this.lastnameswitch.setText("Edit");
					this.lastnameswitch.setGraphic(new ImageView(pen));
					this.lastname_text.setVisible(false);
					this.lastname_label.setVisible(true);
					this.lastname_label.setText(this.lastname_text.getText());
				}
				break;
			case "birthdateswitch":
				if (Objects.equals(this.birthdateswitch.getText(), "Edit")){
					this.birthdateswitch.setText("Done");
					this.birthdateswitch.setGraphic(new ImageView(check));
					this.birthdate_text.setVisible(true);
					this.birthdate_label.setVisible(false);

					this.birthdate_text.setValue(LocalDate.parse(birthdate_label.getText(), DateTimeFormatter.ofPattern("yyyy-MM-d")));
				}else{
					this.birthdateswitch.setText("Edit");
					this.birthdateswitch.setGraphic(new ImageView(pen));
					this.birthdate_text.setVisible(false);
					this.birthdate_label.setVisible(true);
					this.birthdate_label.setText(this.birthdate_text.getValue().toString());
				}
				break;
			case "heightswitch":
				if (Objects.equals(this.heightswitch.getText(), "Edit")){
					this.heightswitch.setText("Done");
					this.heightswitch.setGraphic(new ImageView(check));
					this.height_text.setVisible(true);
					this.height_label.setVisible(false);
					this.height_text.setText(this.height_label.getText());
				}else{
					this.heightswitch.setText("Edit");
					this.heightswitch.setGraphic(new ImageView(pen));
					this.height_text.setVisible(false);
					this.height_label.setVisible(true);
					this.height_label.setText(this.height_text.getText());
				}
				break;
			case "weightswitch":
				if (Objects.equals(this.weightswitch.getText(), "Edit")){
					this.weightswitch.setText("Done");
					this.weightswitch.setGraphic(new ImageView(check));
					this.weight_text.setVisible(true);
					this.weight_label.setVisible(false);
					this.weight_text.setText(this.weight_label.getText());
				}else{
					this.weightswitch.setText("Edit");
					this.weightswitch.setGraphic(new ImageView(pen));
					this.weight_text.setVisible(false);
					this.weight_label.setVisible(true);
					this.weight_label.setText(this.weight_text.getText());
				}
				break;
			case "sexswitch":
				if (Objects.equals(this.sexswitch.getText(), "Edit")){
					this.sexswitch.setText("Done");
					this.sexswitch.setGraphic(new ImageView(check));
					this.maleButton.setVisible(true);
					this.femaleButton.setVisible(true);
					this.sex_label.setVisible(false);
					if (Objects.equals(this.sex_label.getText(), "♂")){
						this.maleButton.setSelected(true);
						this.femaleButton.setSelected(false);
					}else {
						this.maleButton.setSelected(false);
						this.femaleButton.setSelected(true);
					}

				}else{
					this.sexswitch.setText("Edit");
					this.sexswitch.setGraphic(new ImageView(pen));
					this.maleButton.setVisible(false);
					this.femaleButton.setVisible(false);
					this.sex_label.setVisible(true);
					if (this.maleButton.isSelected()){
						this.sex_label.setText("♂");
					}else{
						this.sex_label.setText("♀");
					}
				}
				break;
			// more cases can be added as needed
			default:
				// code to be executed if expression doesn't match any case
		}
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = lastname_label.getText();
			String savedFirstName = firstname_label.getText();
			String savedSex = this.sex_label.getText();
			LocalDate localDate = LocalDate.parse(birthdate_label.getText(), DateTimeFormatter.ofPattern("yyyy-MM-d"));
			float floatHeight = Float.parseFloat(height_label.getText());
			float floatWeight = Float.parseFloat(weight_label.getText());
			this.listener.saveProfile(
					savedFirstName,
					savedLastName,
					savedSex,
					localDate,
					floatHeight,
					floatWeight);
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
	}
}
