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
import ulb.views.utils.Foo;
import ulb.views.utils.Foo2;
import ulb.views.utils.FooAbstract;

public class ProfileViewController implements ViewController {
	// the following variables are here to be able to access the textfield, label and button but for code maintenance, they should not be manipulated directly
	@FXML private Label _firstname_label;
	@FXML private TextField _firstname_text;
	@FXML private Button _firstname_button;
	@FXML private Label _lastname_label;
	@FXML private TextField _lastname_text;
	@FXML private Button _lastname_button;
	@FXML private Label _birthdate_label;
	@FXML private DatePicker _birthdate_text;
	@FXML private Button _birthdate_button;
	@FXML private Label _height_label;
	@FXML private TextField _height_text;
	@FXML private Button _height_button;
	@FXML private Label _weight_label;
	@FXML private TextField _weight_text;
	@FXML private Button _weight_button;
	// end of variables that should not be manipulated directly
	private Image pen;
	private Image check;

	@FXML private ImageView profileimage;
	@FXML private Button imageselection;


	private FooAbstract firstname;
	private FooAbstract lastname;
	private FooAbstract birthdate;
	private FooAbstract height;
	private FooAbstract weight;

	@FXML Foo test;

	@FXML private Label sex_label;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;
	@FXML private Button sexswitch;

	private ArrayList<FooAbstract> bar;
	//TODO : Rename this variable, this variable has for objective to apply the same method to all FooAbstract objects
	private String imagepath;

	private ProfileViewController.Listener
			listener; // Listener interface for communication with the controller

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("Profile view initializing");
		this.firstname = new Foo(_firstname_text, _firstname_label, _firstname_button);
		this.lastname = new Foo(_lastname_text, _lastname_label, _lastname_button);
		this.birthdate = new Foo2(_birthdate_text, _birthdate_label, _birthdate_button);
		this.height = new Foo(_height_text, _height_label, _height_button);
		this.weight = new Foo(_weight_text, _weight_label, _weight_button);
		bar = new ArrayList<>(Arrays.asList(this.firstname, this.lastname, this.birthdate, this.height, this.weight));
		System.out.println("Profile view initialized");
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
		System.out.println("Images loaded");
		for (FooAbstract foo : this.bar) {
			foo.setImages(pen, check);
		}
		System.out.println("Images set");
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

		for (FooAbstract foo : this.bar) {
			foo.setDefault();
		}
		this.firstname.setLabelText(this.listener.getFirstName());
		this.lastname.setLabelText(this.listener.getLastName());
		this.birthdate.setLabelText(this.listener.getBirthDate().toString());
		this.height.setLabelText(Float.toString(this.listener.getHeight()));
		this.weight.setLabelText(Float.toString(this.listener.getWeight()));


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
		for (FooAbstract foo : bar) { //* need to be optimized
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
				this.firstname.toggleMode();
				break;
			case "_lastname_button":
				this.lastname.toggleMode();
				break;
			case "_birthdate_button":
				this.birthdate.toggleMode();
				break;
			case "_height_button":
				this.height.toggleMode();
				break;
			case "_weight_button":
				this.weight.toggleMode();
				break;
			default:

				throw new IllegalStateException("Unexpected value: " + id);
				// code to be executed if expression doesn't match any case
		}
	}

	// Save profile information
	public void saveProfile() {
		try {
			String savedLastName = lastname.getText();
			String savedFirstName = firstname.getText();
			String savedSex = this.sex_label.getText();
			LocalDate localDate = LocalDate.parse(birthdate.getText());
			float floatHeight = Float.parseFloat(height.getText());
			float floatWeight = Float.parseFloat(weight.getText());
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
