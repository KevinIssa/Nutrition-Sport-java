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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;

public class MealHistoryViewController implements ViewController {

	private static final String FOLDERNAME = "consumed_meals";

	private MealHistoryViewController.Listener
			listener; // Listener interface for communication with the controller

	@FXML private ListView<HBox> historyList;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (MealHistoryViewController.Listener) listener;
		this.addMeals(); // Add activities when listener is set
	}

	public void addMeals() {
		File directory = new File(FOLDERNAME); // Specify the directory path

		File[] files = directory.listFiles();

		// Add activities to the list
		if (files != null) {
			for (File file : files) {
				ConsumedMeal meal = listener.loadMeal(file.getPath());
				for (ConsumedFood food : meal.getConsumedFoods()) {
					addFood(food, meal.changeDateFormat(meal.getDate()));
				}
				// Add each activity individually
			}
		}
	}

	public void addFood(ConsumedFood food, String date) {
		HBox mealHBox = createHistoryHBox(food, date);
		historyList.getItems().add(mealHBox);
	}

	private HBox createHistoryHBox(ConsumedFood food, String date) {
		HBox hbox = createHBox();
		setIconInHBox(hbox);
		setTextInHBox(food, date, hbox);
		setButtonInHBox(food, hbox);
		return hbox;
	}

	private void setIconInHBox(HBox hbox) {
		ImageView dateImageView = createImageView("/ulb/images/history_img/calendrier.png", 30, 30);
		ImageView quantityImageView =
				createImageView("/ulb/images/history_img/quantite.png", 30, 30);
		ImageView calorieImageView =
				createImageView("/ulb/images/history_img/calories_consumed.png", 30, 30);
		hbox.getChildren().addAll(quantityImageView, dateImageView, calorieImageView);
	}

	private void setTextInHBox(ConsumedFood food, String date, HBox hbox) {
		Label LabelMealName = createLabel(food.getName(), 100);
		Label LabelQuantity = createLabel(Integer.toString(food.getQuantity()), 40);
		Label LabelDate = createLabel(date, 120);
		Label LabelCalorie = createLabel(String.valueOf(food.getCalories() + " kcal"), 50);
		hbox.getChildren().add(0, LabelMealName);
		hbox.getChildren().add(2, LabelQuantity);
		hbox.getChildren().add(4, LabelDate);
		hbox.getChildren().add(6, LabelCalorie);
	}

	private void setButtonInHBox(ConsumedFood food, HBox hbox) {
		ImageView imageDelete = createImageView("/ulb/images/poubelle.png", 30, 30);
		Button deleteActivityButton = new Button("");
		deleteActivityButton.setGraphic(imageDelete);
		deleteActivityButton.setOnAction(e -> deleteFoodInHistory(food));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		hbox.getChildren().addAll(spacer, deleteActivityButton);
	}

	private void deleteFoodInHistory(ConsumedFood food) {
		// delete food in model and controller
		System.out.println("C pas fait ");
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

	private Label createLabel(String text, int width) {
		Label label = new Label(text);
		label.setMinWidth(width);
		label.setMaxWidth(width);
		return label;
	}

	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {

		ConsumedMeal loadMeal(String filename); // Load activity from file

		void returnHome(); // Return to the home view
	}
}
