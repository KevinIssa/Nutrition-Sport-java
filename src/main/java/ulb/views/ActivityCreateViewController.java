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
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import ulb.models.enums.Sport;

public class ActivityCreateViewController implements ViewController {
	private Sport selectedSport;
	private String intensity;
	@FXML private Slider intensitySlider;
	@FXML private TextField duration;

	@FXML private Button button_walking;
	@FXML private Button button_running;
	@FXML private Button button_biking;
	@FXML private Button button_swimming;
	@FXML private Button button_volleyball;

	private ActivityCreateViewController.Listener
			listener; // Listener interface for communication with the controller

	// Initialize method called after FXML file has been loaded
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Populate ComboBox with sports

		// Set up intensity slider
		configIntensitySlider();

		// Listen for changes in slider value and update intensity text field accordingly
		intensitySlider
				.valueProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							if (newValue.intValue() == 0) intensity = "Slow";
							if (newValue.intValue() == 1) intensity = "Moderate";
							if (newValue.intValue() == 2) intensity = "Intense";
						});
	}

	private void configIntensitySlider() {

		intensitySlider.setLabelFormatter(new IntensityStringConverter());
		intensitySlider.setMin(0);
		intensitySlider.setMax(2);
		intensitySlider.setValue(1);
		intensitySlider.setMinorTickCount(0);
		intensitySlider.setSnapToTicks(true);
		intensitySlider.setShowTickMarks(true);
		intensitySlider.setShowTickLabels(true);
		intensitySlider.setMajorTickUnit(1);
		intensity = "Moderate"; // * Default value

	}

	// Method to show an alert with the calculated calories

	// Method to save the activity
	public void saveActivity() {
		try {
			String selectedIntensity = intensity;
			float selectedDuration = Float.parseFloat(duration.getText());
			this.listener.saveActivity(selectedSport, selectedIntensity, selectedDuration);
		} catch (NumberFormatException e) {
			System.out.println("Invalid duration");
			return;
		}
		this.listener.returnHome();
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public void setButtonDefaultColor() {
		String color = "-fx-background-color: #9960f2;";
		List<Button> buttons =
				List.of(
						button_walking,
						button_running,
						button_biking,
						button_swimming,
						button_volleyball);
		for (Button button : buttons) {
			button.setStyle(color);
		}
		selectedSport = null;
	}

	public void selectWalking() {
		this.selectSport(this.button_walking, Sport.WALKING);
	}

	public void selectRunning() {
		this.selectSport(this.button_running, Sport.RUNNING);
	}

	public void selectBiking() {
		this.selectSport(this.button_biking, Sport.BIKING);
	}

	public void selectSwimming() {
		this.selectSport(this.button_swimming, Sport.SWIMMING);
	}

	public void selectVolleyball() {
		this.selectSport(this.button_volleyball, Sport.VOLLEYBALL);
	}

	public void selectSport(Button button, Sport sport) {
		this.setButtonDefaultColor();
		String color = "-fx-background-color: #b7ed65;";
		button.setStyle(color);
		selectedSport = sport;
	}

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
		void saveActivity(Sport selectedSport, String selectedIntensity, float selectedDuration);

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
