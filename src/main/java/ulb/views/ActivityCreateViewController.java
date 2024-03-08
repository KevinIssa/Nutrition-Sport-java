/* (C)2024 */
package ulb.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import ulb.models.enums.Sport;

public class ActivityCreateViewController implements ViewController {
	@FXML private TextField sport;
	@FXML private Slider intensitySlider;
	@FXML private TextField intensity;
	@FXML private TextField duration;
	@FXML ComboBox<Sport> cbSport;

	private Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO: hugo will refactor this 💩
		this.cbSport.getItems().addAll(Sport.values());
		intensitySlider.setLabelFormatter(
				new StringConverter<Double>() {
					@Override
					public String toString(Double aDouble) {
						if (aDouble < 0.5) return "Slow";
						if (aDouble < 1.5) return "Moderate";
						if (aDouble < 2.5) return "Intense";

						return "Intense";
					}

					@Override
					public Double fromString(String s) {
						switch (s) {
							case "Slow":
								return 0d;
							case "Moderate":
								return 1d;
							case "Intense":
								return 2d;

							default:
								return 2d;
						}
					}
				});
		intensitySlider.setMin(0);
		intensitySlider.setMax(2);
		intensitySlider.setValue(1);
		intensitySlider.setMinorTickCount(0);
		intensitySlider.setSnapToTicks(true);
		intensitySlider.setShowTickMarks(true);
		intensitySlider.setShowTickLabels(true);
		intensitySlider.setMajorTickUnit(1);
		intensitySlider
				.valueProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							if (newValue.intValue() == 0) intensity.setText("Slow");
							if (newValue.intValue() == 1) intensity.setText("Moderate");
							if (newValue.intValue() == 2) intensity.setText("Intense");
						});
	}

	public void showAlert(double calories) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Calcul du nombre de calories");
		alert.setHeaderText(null);
		String text = "Vous avez dépensé " + calories + " calories durant cette activité";
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void saveActivity() {
		try {
			Sport selectedSport = cbSport.getValue();
			String selectedIntensity = intensity.getText();
			float selectedDuration = Float.parseFloat(duration.getText());
			//computeCalories()
			this.listener.saveActivity(selectedSport, selectedIntensity, selectedDuration);
		} catch (NumberFormatException e) {
			return;
		}
		this.listener.returnHome();
	}

	public void setListener(Object listener) {
		this.listener = (Listener) listener;
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
	}

	public interface Listener {
		void saveActivity(Sport selectedSport, String selectedIntensity, float selectedDuration);
		void returnHome();
	}
}
