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

	@FXML private DatePicker activityDate;
	@FXML private TextField hour;
	@FXML private TextField minutes;

	private Button selectedButton;

	/**
	 * This class is a controller to create activites in the UI. It implements the ViewController interface.
	 * It is responsible for handling the user interactions with the activity creation view.
	 */
	private ActivityCreateViewController.Listener
			listener; // Listener interface for communication with the controller

	/**
	 * This method is called after the FXML file has been loaded.
	 * It initializes the view with the current date and time.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		selectedButton = buttonWalking;
		// Populate ComboBox with sports
		intensitySlider.setLabelFormatter(new IntensityStringConverter());
		this.initTime();
	}

	/**
	 * This method initializes the date picker and time fields with the current date and time.
	 */
	private void initTime() {
		activityDate.setValue(LocalDate.now());
		hour.setText(String.valueOf(LocalTime.now().getHour()));
		minutes.setText(String.valueOf(LocalTime.now().getMinute()));
	}

	/**
	 * This method returns the time of the activity entered by the user and rise a pop up if the time entered is not valid.
	 */
	public LocalTime getActivityTime() {
		try {
		// Get the current time
		LocalTime currentTime = LocalTime.now();

		// Extract seconds from the current time

		int intHour = Integer.parseInt(hour.getText());
		int intMinutes = Integer.parseInt(minutes.getText());
		int intSeconds = currentTime.getSecond();

		if (intHour < 0 || intHour > 23 || intMinutes < 0 || intMinutes > 59) {
			showAlert(
					"Heure invalide",
					"L'heure doit être comprise entre 0 et 23 et les minutes entre 0 et 59");
			return null;
		}

		LocalTime time = LocalTime.of(intHour, intMinutes, intSeconds);
		return time;
		} catch (NumberFormatException e) {
			showAlert("Heure invalide", "L'heure doit être un nombre");
			return null;
		}
	}

	/*public boolean isDateInFuture(LocalDate date1, LocalDate date2) {
		return date1.compareTo(date2) > 0;
	}*/

	public LocalDateTime getActivityDate() {

		/*if (isDateInFuture(activityDate.getValue(), currentDate)) {
			showAlert("Date invalide", "La date ne peut pas être dans le futur");
			return null;
		}*/

		LocalDateTime activityDateTime =
				LocalDateTime.of(activityDate.getValue(), getActivityTime());
		return activityDateTime;
	}

	// Method to save the activity
	public void saveActivity() {
		this.listener.saveActivity(
				this.selectedSport,
				(int) intensitySlider.getValue(),
				this.duration.getText(),
				this.getActivityDate());
		this.listener.returnHome();
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * This method changes the selected sport and updates the UI accordingly.
	 */
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
	/**
	 * This method shows an alert with the number of calories burned during the activity and is a base code for the others pop up.
	 */
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
