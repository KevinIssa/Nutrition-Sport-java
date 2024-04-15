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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.enums.Sport;

public class ActivityCreateViewController implements ViewController {
	private static final Logger logger =
			LoggerFactory.getLogger(ActivityCreateViewController.class);
	@FXML private Slider intensitySlider;
	@FXML private TextField duration;
	@FXML
	private Button buttonWalking, buttonRunning, buttonBiking, buttonSwimming, buttonVolleyball;
	@FXML private DatePicker activityDate;
	@FXML private TextField hour, minutes;

	private Sport selectedSport = Sport.WALKING;
	private Button selectedButton;
	private Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.selectedButton = this.buttonWalking;
		this.intensitySlider.setLabelFormatter(new IntensityStringConverter());
		this.initTime();
	}

	/**
	 * This method initializes the date picker and time fields with the current date and time.
	 */
	private void initTime() {
		LocalDateTime now = LocalDateTime.now();
		this.activityDate.setValue(now.toLocalDate());
		this.hour.setText(String.valueOf(now.getHour()));
		this.minutes.setText(String.valueOf(now.getMinute()));
	}

	/**
	 * This method returns the time of the activity entered by the user and rise a pop up if the time entered is not valid.
	 */
	public LocalTime getActivityTime() {
		try {
			String hourText = hour.getText();
			String minutesText = minutes.getText();

			if (hourText == null || minutesText == null) {
				showAlert("Erreur", "Veuillez entrer une heure valide.");
				return null;
			}

			int intHour = Integer.parseInt(hourText);
			int intMinutes = Integer.parseInt(minutesText);

			if (intHour < 0 || intHour > 23 || intMinutes < 0 || intMinutes > 59) {
				showAlert(
						"Heure invalide",
						"L'heure doit être comprise entre 0 et 23 et les minutes entre 0 et 59");
				return null;
			}

			return LocalTime.of(intHour, intMinutes);
		} catch (NumberFormatException e) {
			showAlert("Heure invalide", "L'heure doit être un nombre");
			return null;
		}
	}

	/**
	 * This method returns the date and time of the activity entered by the user.
	 */
	public LocalDateTime getActivityDate() {
		LocalDate currentDate = LocalDate.now();
		if (isDateInFuture(activityDate.getValue(), currentDate)) {
			showAlert("Date invalide", "La date ne peut pas être dans le futur");
			return null;
		}

		LocalTime activityTime = getActivityTime();
		if (activityTime == null) {
			return null; // An error occurred, return null
		}

		return LocalDateTime.of(activityDate.getValue(), activityTime);
	}

	/**
	 * This method saves the activity.
	 */
	public void saveActivity() {
		LocalDateTime activityDateTime = getActivityDate();
		String durationText = this.duration.getText();

		if (!isActivityDateValid(activityDateTime) || !isDurationValid(durationText)) {
			return;
		}

		// Attempt to save the activity
		try {
			this.listener.saveActivity(
					this.selectedSport,
					(int) intensitySlider.getValue(),
					durationText,
					activityDateTime);
			returnHome();
		} catch (Exception e) {
			showAlert("Erreur", "Une erreur s'est produite lors de l'enregistrement de l'activité.");
		}
	}

	/**
	 * This method checks if a date is in the future.
	 */
	public boolean isDateInFuture(LocalDate date1, LocalDate date2) {
		return date1.compareTo(date2) > 0;
	}

	/**
	 * Checks if the provided activity date and time is valid.
	 * Displays an error message if the date and time are not valid.
	 */
	private boolean isActivityDateValid(LocalDateTime activityDateTime) {
		if (activityDateTime == null) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the provided duration text is valid.
	 * Displays an error message if the duration is not valid.
	 */
	private boolean isDurationValid(String durationText) {
		try {
			int duration = Integer.parseInt(durationText);
			if (duration < 0 || duration > 500) {
				showAlert("Erreur", "La durée doit être un nombre valide et ne peut pas dépasser 500.");
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			showAlert("Erreur", "La durée doit être un nombre valide.");
			return false;
		}
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * This method changes the selected sport and updates the UI accordingly.
	 */
	public void clickedButton(Button button, Sport sport) {
		this.selectedButton.setStyle("-fx-background-color: #9960f2;");
		button.setStyle("-fx-background-color: #b7ed65;");
		this.selectedSport = sport;
		this.selectedButton = button;
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

	public void showCaloriesConsumed(double calories) {
		this.showAlert(
				"Calcul du nombre de calories",
				"Vous avez dépensé " + calories + " calories durant cette activité");
	}

	// Method to set the listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
	}

	// Listener interface for communication with the controller
	public interface Listener {
		void saveActivity(
				Sport selectedSport,
				int selectedIntensity,
				String selectedDuration,
				LocalDateTime activityDateTime);

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
