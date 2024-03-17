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
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import ulb.models.enums.Sport;

public class ActivityCreateViewController implements ViewController {

	private String intensity;
	@FXML private Slider intensitySlider;
	@FXML private HBox rectanglePane;
	private Rectangle greenRectangle;
    private Rectangle yellowRectangle;
    private Rectangle redRectangle;
	@FXML private TextField duration;
	@FXML private ComboBox<Sport> cbSport;

	private ActivityCreateViewController.Listener
			listener; // Listener interface for communication with the controller

	// Initialize method called after FXML file has been loaded
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Populate ComboBox with sports
		this.cbSport.getItems().addAll(Sport.values());

		// Set up intensity slider
		intensitySlider.setLabelFormatter(new IntensityStringConverter());
		intensitySlider.setMin(0);
		intensitySlider.setMax(2);
		intensitySlider.setValue(1);
		intensitySlider.setMinorTickCount(0);
		intensitySlider.setSnapToTicks(true);
		intensitySlider.setShowTickMarks(true);
		intensitySlider.setShowTickLabels(true);
		intensitySlider.setMajorTickUnit(1);

		// Listen for changes in slider value and update intensity text field accordingly
		intensitySlider
				.valueProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							if (newValue.intValue() == 0) intensity = "Slow";
							if (newValue.intValue() == 1) intensity = "Moderate";
							if (newValue.intValue() == 2) intensity = "Intense";
						});
		greenRectangle = createRectangle(50, 50, "green");
        yellowRectangle = createRectangle(50, 100, "yellow");
        redRectangle = createRectangle(50, 150, "red");

        rectanglePane.getChildren().addAll(greenRectangle, yellowRectangle, redRectangle);

        intensitySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateRectangles(newValue.intValue()));
		updateRectangles(intensitySlider.valueProperty().intValue());
	}

	private void updateRectangles(int value) {
        switch (value) {
			case 0:
				greenRectangle.setVisible(true);
				yellowRectangle.setVisible(false);
				redRectangle.setVisible(false);
				break;
			case 1:
				greenRectangle.setVisible(true);
				yellowRectangle.setVisible(true);
				redRectangle.setVisible(false);
				break;
			case 2:
				greenRectangle.setVisible(true);
				yellowRectangle.setVisible(true);
				redRectangle.setVisible(true);
				break;
			default:
				greenRectangle.setVisible(false);
				yellowRectangle.setVisible(false);
				redRectangle.setVisible(false);
				System.out.println("Invalid value");
				break;
		}
    }

    private Rectangle createRectangle(double width, double height, String color) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.getStyleClass().add("rectangle-" + color);
        return rectangle;
    }

	// Method to show an alert with the calculated calories
	public void showAlert(double calories) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Calcul du nombre de calories");
		alert.setHeaderText(null);
		String text = "Vous avez dépensé " + calories + " calories durant cette activité";
		alert.setContentText(text);
		alert.showAndWait();
	}

	// Method to save the activity
	public void saveActivity() {
		try {
			Sport selectedSport = cbSport.getValue();
			String selectedIntensity = intensity;
			float selectedDuration = Float.parseFloat(duration.getText());
			this.listener.saveActivity(selectedSport, selectedIntensity, selectedDuration);
		} catch (NumberFormatException e) {
			return;
		}
		this.listener.returnHome();
	}

	public void returnHome() {
		this.listener.returnHome();
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
