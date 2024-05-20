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
import ulb.dtos.ActivityDTO;
import ulb.enums.Intensity;
import ulb.enums.Sport;
import ulb.exceptions.SavingException;
import ulb.widgets.NumberField;

public class ActivityCreateViewController implements ViewController {
	@FXML private Slider intensitySlider;
	@FXML
	private Button buttonWalking,
			buttonRunning, // NOSONAR
			buttonBiking,
			buttonSwimming,
			buttonVolleyball; // issue with sonar lint "java:S1659 - Multiple variables
	// should not be declared in the same declaration" and our code style formatter
	@FXML private DatePicker activityDate;
	@FXML private Label burnedCalories;

	@FXML private NumberField durationNumber;
	@FXML private NumberField hourNumber;
	@FXML private NumberField minuteNumber;
	private static final Logger logger =
			LoggerFactory.getLogger(ActivityCreateViewController.class);
	private Sport selectedSport = Sport.WALKING;
	private Button selectedButton;
	private Listener listener;
	private boolean isEdit = false;
	private ActivityDTO defaultActivityDTO;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.selectedButton = this.buttonWalking;
		this.intensitySlider.setLabelFormatter(new IntensityStringConverter());
		this.intensitySlider.setStyle("-fx-control-inner-background: #f2e060;");
		this.initTime();
		this.selectWalking();
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
	public LocalTime getActivityTime() throws NumberFormatException {
		int hourValue = hourNumber.getValue();
		int minuteValue = minuteNumber.getValue();
		return LocalTime.of(hourValue, minuteValue);
	}

	private void checkTime() throws NumberFormatException {
		if (this.hourNumber.getValue() < 0
				|| this.hourNumber.getValue() > 23
				|| this.minuteNumber.getValue() < 0
				|| this.minuteNumber.getValue() > 59) {
			throw new NumberFormatException();
		}
	}

	private void checkDate() {
		if (this.activityDate.getValue().isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("La date ne peut pas être dans le futur");
		}
	}

	/**
	 * This method returns the date and time of the activity entered by the user.
	 */
	public LocalDateTime getDateTime() throws IllegalArgumentException {
		LocalTime activityTime = getActivityTime();
		return LocalDateTime.of(activityDate.getValue(), activityTime);
	}

	public void setIntensitySliderColor() {
		if (intensitySlider.getValue() == 0) {
			intensitySlider.setStyle("-fx-control-inner-background: #98ff00;");
		} else if (intensitySlider.getValue() == 1) {
			intensitySlider.setStyle("-fx-control-inner-background: #ffe000;");
		} else if (intensitySlider.getValue() == 2) {
			intensitySlider.setStyle("-fx-control-inner-background: #ff0000;");
		}
		// update the number of calorie showed
		setCaloriesBurned();
	}

	public ActivityDTO getActivityDTO() throws IllegalArgumentException {
		this.checkDate();
		this.checkTime();
		LocalDateTime activityDateTime = getDateTime();
		int durationValue = this.durationNumber.getValue();
		return new ActivityDTO(
				this.selectedSport,
				Intensity.fromInt((int) intensitySlider.getValue()),
				durationValue,
				activityDateTime);
	}

	public ActivityDTO getDefaultActivityDTO() {
		return this.defaultActivityDTO;
	}

	/**
	 * This method saves the activity.
	 */
	public void saveActivity() {
		try {
			logger.info("Saving activity");
			ActivityDTO activityDTO = getActivityDTO();
			if (activityDTO != null) {
				this.listener.saveActivity(getActivityDTO());
				goToActivityHistory();
			}
		} catch (NumberFormatException ignored) {
			logger.info("Invalid time entered");
			showAlert("Erreur de timestamp", "Veuillez entrer une heure valide.");
		} catch (IllegalArgumentException e) {
			showAlert("Erreur de validité", e.getMessage());
		} catch (SavingException ignored) {
			showAlert(
					"Erreur de sauvegarde",
					"Une erreur s'est produite lors de l'enregistrement de l'activité.");
		} catch (Exception ignored) {
			showAlert(
					"Erreur de inconnue",
					"Une erreur s'est produite lors de l'enregistrement de l'activité.");
		} // this can be done better with message management
	}

	@FXML
	private void setCaloriesBurned() {
		try {
			ActivityDTO activityDTO = getActivityDTO();
			if (activityDTO == null) {
				return;
			}
			this.burnedCalories.setText(
					String.valueOf(this.listener.calculateCalorie(activityDTO)));
		} catch (IllegalArgumentException ignored) {
			// ignore
		}
	}

	public void returnHome() {
		if (this.isEdit) {
			try {
				this.listener.saveActivity(getDefaultActivityDTO());
			} catch (SavingException e) {
				logger.error("Error while saving activity", e);
				showAlert(
						"Erreur de sauvegarde", "Une erreur s'est produite lors de la sauvegarde.");
			}
		}
		this.listener.returnHome();
	}

	public void goToActivityHistory() {
		this.listener.goToActivityHistory();
	}

	/**
	 * This method changes the selected sport and updates the UI accordingly.
	 */
	public void clickedButton(Button button, Sport sport) {
		this.selectedButton.setStyle("-fx-background-color: #9960f2;");
		button.setStyle("-fx-background-color: #b7ed65;");
		this.selectedSport = sport;
		this.selectedButton = button;
		// update the number of calorie showed
		setCaloriesBurned();
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

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
	}

	public void setDefaultActivity(ActivityDTO activityDTO) {
		this.selectedSport = activityDTO.sport();
		this.intensitySlider.setValue(activityDTO.intensity().ordinal());
		this.durationNumber.setValue(activityDTO.duration());
		this.activityDate.setValue(activityDTO.date().toLocalDate());
		this.hourNumber.setValue(activityDTO.date().getHour());
		this.minuteNumber.setValue(activityDTO.date().getMinute());
		this.isEdit = true;
		this.defaultActivityDTO = activityDTO;
		this.setCaloriesBurned();
		chooseButton(this.selectedSport);
	}

	public void chooseButton(Sport sport) {
		switch (sport) {
			case WALKING -> selectWalking();
			case RUNNING -> selectRunning();
			case BIKING -> selectBiking();
			case SWIMMING -> selectSwimming();
			case VOLLEYBALL -> selectVolleyball();
		}
	}

	/**
	 * Listener interface for communication with the controller.
	 * This interface should be implemented by any class that needs to respond to actions from the ActivityCreateViewController.
	 */
	public interface Listener {

		/**
		 * Saves an activity.
		 * This method should be implemented to handle the action of saving an activity.
		 *
		 * @param activityDTO The ActivityDTO object representing the activity to be saved.
		 * @throws SavingException If an error occurs while saving the activity.
		 */
		void saveActivity(ActivityDTO activityDTO) throws SavingException;

		/**
		 * Calculates the calories burned during an activity.
		 * This method should be implemented to return the number of calories burned during an activity.
		 *
		 * @param activityDTO The ActivityDTO object representing the activity for which the calories burned are to be calculated.
		 * @return The number of calories burned during the activity.
		 */
		int calculateCalorie(ActivityDTO activityDTO);

		/**
		 * Triggers the return to the home view.
		 * This method should be implemented to handle the action of returning to the home view.
		 */
		void returnHome();

		/**
		 * Navigates to the activity history view.
		 * This method should be implemented to handle the action of navigating to the activity history view.
		 */
		void goToActivityHistory();
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
			return switch (intensity) {
				case "Slow" -> 0d;
				case "Moderate" -> 1d;
				default -> 2d;
			};
		}
	}
}
