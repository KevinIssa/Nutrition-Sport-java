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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import ulb.models.enums.Sport;

public class ActivityCreateViewController implements ViewController {
	private Sport selectedSport;
	@FXML private Slider intensitySlider;
	@FXML private TextField duration;
	@FXML private Button buttonWalking;
	@FXML private Button buttonRunning;
	@FXML private Button buttonBiking;
	@FXML private Button buttonSwimming;
	@FXML private Button buttonVolleyball;
	private Button selectedButton;

	private ActivityCreateViewController.Listener
			listener; // Listener interface for communication with the controller

	// Initialize method called after FXML file has been loaded
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		selectedButton = buttonWalking;
		// Populate ComboBox with sports
		intensitySlider.setLabelFormatter(new IntensityStringConverter());
	}

	// Method to save the activity
	public void saveActivity() {
		this.listener.saveActivity(
				this.selectedSport, (int) intensitySlider.getValue(), this.duration.getText());
		this.listener.returnHome();
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void clickedButton(Button button, Sport sport) {
		String color = "-fx-background-color: #9960f2;";
		selectedButton.setStyle(color);
		color = "-fx-background-color: #b7ed65;";
		button.setStyle(color);
		selectedSport = sport;
		selectedButton = button;
	}

	public void selectWalking() {
		this.clickedButton(this.buttonWalking, Sport.WALKING);
	}

	public void selectRunning() {
		this.clickedButton(this.buttonRunning, Sport.RUNNING);
	}

	public void selectBiking() {
		this.clickedButton(this.buttonBiking, Sport.BIKING);
	}

	public void selectSwimming() {
		this.clickedButton(this.buttonSwimming, Sport.SWIMMING);
	}

	public void selectVolleyball() {
		this.clickedButton(this.buttonVolleyball, Sport.VOLLEYBALL);
	}

	// TODO Move that to other class !
	public static void showAlert(double calories) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Calcul du nombre de calories");
		alert.setHeaderText(null);
		String text = "Vous avez dépensé " + calories + " calories durant cette activité";
		alert.setContentText(text);
		alert.showAndWait();
	}

	// Method to set the listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
	}

	// Listener interface for communication with the controller
	public interface Listener {
		void saveActivity(Sport selectedSport, int selectedIntensity, String selectedDuration);

		void returnHome();
	}

	// Custom string converter for intensity slider labels
	private static class IntensityStringConverter extends StringConverter<Double> {
		@Override
		public String toString(Double aDouble) {
			if (aDouble < 0.5) return "Lent";
			if (aDouble < 1.5) return "Modéré";
			return "Intense";
		}

		@Override
		public Double fromString(String intensity) {
			switch (intensity) {
				case "Slow":
					return 0d;
				case "Moderate":
					return 1d;
				default:
					return 2d;
			}
		}
	}
}
