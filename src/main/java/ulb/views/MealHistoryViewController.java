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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.ConsumedMealDTO;

public class MealHistoryViewController implements ViewController {
	private static final Logger logger = LoggerFactory.getLogger(MealHistoryViewController.class);

	private MealHistoryViewController.Listener
			listener; // Listener interface for communication with the controller

	@FXML private ListView<HBox> historyList;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println(listener);
	}

	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealHistoryViewController.Listener) listener;
		this.loadConsumedMeals();
	}

	private void loadConsumedMeals() {
		List<ConsumedMealDTO> meals = this.listener.getAllMeals();
		for (ConsumedMealDTO meal : meals) {
			for (ConsumedFoodDTO food : meal.consumedFoods()) {
				HBox mealHBox = createHistoryHBox(food, meal.date());
				historyList.getItems().add(mealHBox);
			}
		}
	}

	private HBox createHistoryHBox(ConsumedFoodDTO food, LocalDateTime date) {
		HBox hbox = createHBox();
		// setIconInHBox(hbox);
		setTextInHBox(food, date, hbox);
		setButtonInHBox(hbox);
		return hbox;
	}

	private static HBox createHBox() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		return hbox;
	}

	private void setTextInHBox(ConsumedFoodDTO food, LocalDateTime date, HBox hbox) {
		Label LabelMealName = createLabel(food.name(), 100);
		Label LabelQuantity = createLabel(food.quantity() + " " + food.unit(), 40);
		Label LabelDate =
				createLabel(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")), 120);
		Label LabelCalorie = createLabel(food.calories() + " kcal", 50);
		hbox.getChildren().add(0, LabelMealName);
		hbox.getChildren().add(1, LabelDate);
		hbox.getChildren().add(2, LabelQuantity);
		hbox.getChildren().add(3, LabelCalorie);
	}

	private void setButtonInHBox(HBox hbox) {
		ImageView imageDelete = createImageView("/ulb/images/trash.png", 30, 30);
		Button deleteActivityButton = new Button("");
		deleteActivityButton.setGraphic(imageDelete);
		deleteActivityButton.setOnAction(e -> deleteFoodInHistory(hbox));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		hbox.getChildren().addAll(spacer, deleteActivityButton);
	}

	private void deleteFoodInHistory(HBox foodBox) {
		// delete food in model and controller
		listener.deleteFood(foodBox);
		// delete food in view
		historyList.getItems().remove(foodBox);
	}

	private ImageView createImageView(String imagePath, int width, int height) {
		URL path = getClass().getResource(imagePath);
        assert path != null;
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

	public void loadAddMeal() {
		this.listener.addMeal();
	}

	public interface Listener {

		List<ConsumedMealDTO> getAllMeals(); // Load all meals

		void returnHome(); // Return to the home view

		void addMeal();

		void deleteFood(HBox foodBox);
	}
}
