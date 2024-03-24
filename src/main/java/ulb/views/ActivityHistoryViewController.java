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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
	@FXML private AnchorPane test;
	private boolean isFilterVisible = false;
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
		HBox totalCalorieHBox = createTotalCalorieBox(caloriesBurnedTotal) ;
		historyList.getItems().add(totalCalorieHBox);

		test.setVisible(false);


	}

	public HBox createTotalCalorieBox(int caloriesBurnedTotal){
		HBox hbox = createHBox();
		Label totalCalorieLabel = createLabel(String.valueOf(caloriesBurnedTotal) + " kcal");
		ImageView dateImageView = createImageView("/ulb/images/history_img/total_calories.png",30,30);
		hbox.getChildren().addAll( dateImageView,totalCalorieLabel );
		return hbox;
	}
	
	


	// Method to get the image path for a given sport
	private String getImagePathForSport(String sport) {
		return "/ulb/images/sport_img/" + sport + ".png";
	}

	private String getIntensityPathForSport(String intensity) {
		return "/ulb/images/intensity_img/" + intensity.toString() + ".png";
	}

	public void addActivity(Activity activity) {
		LocalDateTime date = activity.getDate();
		Duration duration = activity.getDuration();

		HBox activityHBox = createHistoryHBox(activity, date,duration);
		historyList.getItems().add(activityHBox);

		// Update total calories ( ! ne devrait pas être là je pense )
		caloriesBurnedTotal += activity.getCaloriesBurned(Profile.load().getWeight());
	}

	private Label createLabel(String text) {
		Label label = new Label(text);
		return label;
	}

	private HBox createHistoryHBox(Activity activity, LocalDateTime date, Duration duration) {
		ImageView intensityImageView = createIntensityImageView(activity);
		ImageView sportImageView = createSportImageView(activity);
		ImageView dateImageView = createImageView("/ulb/images/history_img/calendrier.png",30,30);
		ImageView durationImageView = createImageView("/ulb/images/history_img/chronometre.png",30,30);
		ImageView calorieImageView = createImageView("/ulb/images/history_img/calories.png",30,30);

		Label label_date = createLabel( activity.changeDateFormat(date).toString());
		Label label_duration = createLabel( activity.durationToString(duration));
		Label label_calorie = createLabel( String.valueOf(activity.getCaloriesBurned(Profile.load().getWeight()) + " kcal"));
		HBox hbox = createHBox();
		hbox.getChildren().addAll(sportImageView,intensityImageView, dateImageView, label_date, durationImageView, label_duration, calorieImageView, label_calorie );
		return hbox;
	}

	private static HBox createHBox() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		return hbox;
	}

	private ImageView createImageView(String imagePath, int width, int height) {
		URL path = getClass().getResource(imagePath);
		Image image = new Image(path.toString(), width, height, false, false);
		return new ImageView(image);
	}

	private ImageView createIntensityImageView(Activity activity) {
		String intensityStringPath = getIntensityPathForSport(activity.getIntensity().toString());
		return createImageView(intensityStringPath, 30, 30);
	}

	private ImageView createSportImageView(Activity activity) {
		String imagePath = getImagePathForSport(activity.getSport().toString());
		return createImageView(imagePath, 30, 30);
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


	public void toggleShowFilter() {
		if (isFilterVisible) {
			test.setVisible(false);
			isFilterVisible = false;
		} else {
			test.setVisible(true);
			isFilterVisible = true;
		}
	}

	public void filterRunning(){
		System.out.println("filterRunning");
	}

	public void filterWalking(){
		//TODO
	}

	public void filterBiking(){
		//TODO
	}

	public void filterSwimming(){
		//TODO
	}

	public void filterVolleyball(){
		//TODO
	}

	// Listener interface for communication with the controller
	public interface Listener {

		Activity loadActivity(String filename); // Load activity from file

		void returnHome(); // Return to the home view
	}
}
