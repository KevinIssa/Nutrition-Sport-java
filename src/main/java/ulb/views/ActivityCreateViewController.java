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
	@FXML private Button button_walking;
	@FXML private Button button_running;
	@FXML private Button button_biking;
	@FXML private Button button_swimming;
	@FXML private Button button_volleyball;
	private Button selectedButton = button_walking;

	private ActivityCreateViewController.Listener
			listener; // Listener interface for communication with the controller

	// Initialize method called after FXML file has been loaded
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Populate ComboBox with sports

		// Set up intensity slider
		this.configIntensitySlider();
	}

	private void configIntensitySlider() {
		this.intensitySlider.setLabelFormatter(new IntensityStringConverter());
		this.intensitySlider.setMin(0);
		this.intensitySlider.setMax(2);
		this.intensitySlider.setValue(1);
		this.intensitySlider.setMinorTickCount(0);
		this.intensitySlider.setSnapToTicks(true);
		this.intensitySlider.setShowTickMarks(true);
		this.intensitySlider.setShowTickLabels(true);
		this.intensitySlider.setMajorTickUnit(1);
	}

	// Method to save the activity
	public void saveActivity() {
		String selectedIntensity = Double.toString(this.intensitySlider.getValue());
		this.listener.saveActivity(this.selectedSport, selectedIntensity, this.duration.getText());
		this.listener.returnHome();
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void clickedButton(Button button, Sport sport) {
		String color = "-fx-background-color: rgb(255,255,255);";
		selectedButton.setStyle(color);
		color = "-fx-background-color: #3c8000;";
		button.setStyle(color);
		selectedSport = sport;
		selectedButton = button;
	}

	public void selectWalking() {
		this.clickedButton(this.button_walking, Sport.WALKING);
	}

	public void selectRunning() {
		this.clickedButton(this.button_running, Sport.RUNNING);
	}

	public void selectBiking() {
		this.clickedButton(this.button_biking, Sport.BIKING);
	}

	public void selectSwimming() {
		this.clickedButton(this.button_swimming, Sport.SWIMMING);
	}

	public void selectVolleyball() {
		this.clickedButton(this.button_volleyball, Sport.VOLLEYBALL);
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
		void saveActivity(Sport selectedSport, String selectedIntensity, String selectedDuration);

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
