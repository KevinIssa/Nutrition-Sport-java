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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ulb.models.Activity;
import ulb.models.Profile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

public class ActivityHistoryViewController implements ViewController {

	// Folder name where activity files are stored
	private static final String FOLDERNAME = "activities";
	private int caloriesBurnedTotal = 0;
	private ActivityHistoryViewController.Listener
			listener; // Listener interface for communication with the controller

	@FXML private ListView<HBox> historyList; // ListView to display activity history

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	// Add all activities to the activity history list
	public void addActivities() {
		historyList.getItems().clear(); // Clear existing items from the list
		File directory = new File(FOLDERNAME); // Specify the directory path

		// Get list of files in the directory
		File[] files = directory.listFiles();

		// Add activities to the list
		if (files != null) {
			for (File file : files) {
				Activity activity = listener.loadActivity(file.getPath());
				addActivity(activity); // Add each activity individually
			}
		}

		// Add total calories label
		Label totalCaloriesLabel = new Label("Total des calories brûlées: " + caloriesBurnedTotal);
		//Will have to be changed to hbox later to be added

	}


	// Method to get the image path for a given sport
	private String getImagePathForSport(String sport) {
		return "/ulb/images/sport_img/" + sport + ".png";
	}

	private String getIntensityPathForSport(String intensity) {
		return "/ulb/images/intensity_img/" + intensity.toString() + ".png";
	}

	// Add an activity to the activity history list
	public void addActivity(Activity activity) {
		LocalDateTime date = activity.getDate();
		Duration duration = activity.getDuration();

		// Create label with activity details
		Label label = new Label(
				"Date: " + activity.changeDateFormat(date) +
						"   Durée: " + activity.durationToString(duration) +
						"   Calories brûlées: " + activity.getCaloriesBurned(Profile.load().getWeight())
		);
		label.setTextFill(Color.BLACK);

		// Create image
		// Get the image path for the activity's sport
		String imagePath = getImagePathForSport(activity.getSport().toString());

		URL path = getClass().getResource(imagePath);
		Image image1 = new Image(path.toString(), 30, 30, false, false);
		ImageView imageView = new ImageView(image1);

		String intensityStringPath = getIntensityPathForSport(activity.getIntensity().toString()); // Specify the path for the new image
		URL intensityPath = getClass().getResource(intensityStringPath);
		Image intensityImage = new Image(intensityPath.toString(), 30, 30, false, false);
		ImageView intensityImageView = new ImageView(intensityImage);


		// Create an HBox to hold the image and text together
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT); // Align contents to the left
		hbox.setSpacing(10); // Add spacing between image and text
		hbox.getChildren().addAll(intensityImageView,imageView, label);

		// Add the HBox to the list
		historyList.getItems().add(hbox);

		// Update total calories
		caloriesBurnedTotal += activity.getCaloriesBurned(Profile.load().getWeight());
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
		this.addActivities(); // Add activities when listener is set
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	// Listener interface for communication with the controller
	public interface Listener {

		Activity loadActivity(String filename); // Load activity from file

		void returnHome(); // Return to the home view
	}
}
