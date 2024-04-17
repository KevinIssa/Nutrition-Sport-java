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
import ulb.widgets.NumberField;

public class ActivityCreateViewController implements ViewController {
	@FXML private Slider intensitySlider;
	@FXML private TextField duration;
	@FXML
	private Button buttonWalking, buttonRunning, buttonBiking, buttonSwimming, buttonVolleyball;
	@FXML private DatePicker activityDate;
	@FXML private TextField hour, minute;

	private NumberField durationNumber;
	private NumberField hourNumber;
	private NumberField minuteNumber;
	private static final Logger logger =
			LoggerFactory.getLogger(ActivityCreateViewController.class);
	private Sport selectedSport = Sport.WALKING;
	private Button selectedButton;
	private Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.selectedButton = this.buttonWalking;
		this.intensitySlider.setLabelFormatter(new IntensityStringConverter());
		this.durationNumber = new NumberField(this.duration);
		this.hourNumber = new NumberField(this.hour);
		this.minuteNumber = new NumberField(this.minute);
		this.initTime();
	}

	/**
	 * This method initializes the date picker and time fields with the current date and time.
	 */
	private void initTime() {
		LocalDateTime now = LocalDateTime.now();
		this.activityDate.setValue(now.toLocalDate());
		this.hourNumber.setValue(now.getHour());
		this.minuteNumber.setValue(now.getMinute());
	}

	/**
	 * This method returns the time of the activity entered by the user and rise a pop up if the time entered is not valid.
	 */
	public LocalTime getActivityTime() {
		int hourValue = hourNumber.getValue();
		int minuteValue = minuteNumber.getValue();
		return LocalTime.of(hourValue, minuteValue);
	}

	private void checkTime() {
		if (this.hourNumber.getValue() < 0
				|| this.hourNumber.getValue() > 23
				|| this.minuteNumber.getValue() < 0
				|| this.minuteNumber.getValue() > 59) {
			throw new NumberFormatException();
		}
	}

	private void checkDate() {
		LocalDate now = LocalDate.now();
		LocalDate activityDate = this.activityDate.getValue();
		if (activityDate.isAfter(now)) {
			throw new IllegalArgumentException("La date ne peut pas être dans le futur");
		}
	}

	/**
	 * This method returns the date and time of the activity entered by the user.
	 */
	public LocalDateTime getDateTime() {
		LocalTime activityTime = getActivityTime();
		return LocalDateTime.of(activityDate.getValue(), activityTime);
	}

	/**
	 * This method saves the activity.
	 */
	public void saveActivity() {
		try {
			this.checkDate();
			this.checkTime();
			LocalDateTime activityDateTime = getDateTime();
			int durationValue = this.durationNumber.getValue();

			this.listener.saveActivity(
					this.selectedSport,
					(int) intensitySlider.getValue(),
					durationValue,
					activityDateTime);
			returnHome();
		} catch (NumberFormatException e) {
			showAlert("Erreur", "Veuillez entrer une heure valide.");
		} catch (IllegalArgumentException e) {
			showAlert("Erreur", e.getMessage());
		} catch (Exception e) {
			showAlert(
					"Erreur", "Une erreur s'est produite lors de l'enregistrement de l'activité.");
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
				int selectedDuration,
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
