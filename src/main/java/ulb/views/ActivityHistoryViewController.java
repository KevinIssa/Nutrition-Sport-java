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
import ulb.models.Activity;
import ulb.models.Profile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListCell;

public class ActivityHistoryViewController implements ViewController {

	// Folder name where activity files are stored
	private static final String FOLDERNAME = "activities";
	private int caloriesBurnedTotal = 0;
	private ActivityHistoryViewController.Listener
			listener; // Listener interface for communication with the controller

	@FXML private ListView<Image> historyList; // ListView to display activity history

	// Method called after FXML file has been loaded; overridden from Initializable
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	// Add an activity to the activity history list
	public void addActivity(Activity activity) {
		LocalDateTime date = activity.getDate();
		Duration duration = activity.getDuration();

		Label label =
				new Label(
						"Date: "
								+ activity.changeDateFormat(date)
								+ "   Activité: "
								+ activity.getSport().toString()
								+ "   Intensité: "
								+ activity.getIntensity().toString()
								+ "   Durée: "
								+ activity.durationToString(duration)
								+ "   Calories brûlées: "
								+ activity.getCaloriesBurned(Profile.load().getWeight()));
		caloriesBurnedTotal += activity.getCaloriesBurned(Profile.load().getWeight());
		URL path = getClass().getResource("/ulb/images/sport_img/Marche.png");
		Image image1 = new Image(path.toString(), 30, 30, false, false);
		historyList.getItems().add(image1);

		historyList.setCellFactory(listView -> new ListCell<Image>() {
			private final ImageView imageView = new ImageView();

			@Override
			public void updateItem(Image item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					imageView.setImage(item);
					setGraphic(imageView);
				}
			}
		});
	}

	// Return to the home view
	public void returnHome() {
		this.listener.returnHome();
	}

	// Add all activities to the activity history list
	public void addActivities() {
		historyList.getItems().clear(); // Clear existing items from the list
		File directory = new File(FOLDERNAME); // Specify the directory path

		// Get list of files in the directory
		File[] files = directory.listFiles();

		// Add activities to the list
		if (files != null) {
			for (File file : files) {
				this.addActivity(
						listener.loadActivity(
								file.getPath())); // Load activity from file and add it to the list
			}
		}
		Label label = new Label("Total des calories brûlées: " + caloriesBurnedTotal);
		//historyList.getItems().add(label);
	}

	// Set listener for communication with the controller
	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
		this.addActivities(); // Add activities when listener is set
	}

	// Listener interface for communication with the controller
	public interface Listener {

		Activity loadActivity(String filename); // Load activity from file

		void returnHome(); // Return to the home view
	}
}
