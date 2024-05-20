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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;

public class MealDetailsViewController implements ViewController {

	private static final Logger logger = LoggerFactory.getLogger(MealDetailsViewController.class);
	public Label mealName;
	public Label Calories;
	public ListView foodList;

	private MealDetailsViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Empty method, no initialization needed (java:S1186)
	}

	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealDetailsViewController.Listener) listener;
	}

	public void setMeal(RecipeDTO recipeDTO) {
		mealName.setText(recipeDTO.name());
		Calories.setText(this.listener.getCaloriesConsumed(recipeDTO) + "kcal");
		for (FoodDTO foodDTO : recipeDTO.foods()) {
			addMealBox(foodDTO);
		}
	}

	private void addMealBox(FoodDTO foodDTO) {
		HBox mealHBox = createMealHBox(foodDTO);
		this.foodList.getItems().add(mealHBox);
	}

	private HBox createMealHBox(FoodDTO foodDTO) {
		HBox hbox = createHBox();
		setTextInHBox(foodDTO, hbox);
		return hbox;
	}

	private static HBox createHBox() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		return hbox;
	}

	private void setTextInHBox(FoodDTO food, HBox hbox) {
		Label LabelMealName = createLabel(food.name(), 100);
		Label LabelMealQuantity = createLabel("quantite: " + food.quantity() + food.unit(), 100);
		hbox.getChildren().add(0, LabelMealName);
		hbox.getChildren().add(1, LabelMealQuantity);
	}

	private Label createLabel(String text, int width) {
		Label label = new Label(text);
		label.setMinWidth(width);
		label.setMaxWidth(width);
		return label;
	}

	@FXML
	public void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {

		void returnHome();

		double getCaloriesConsumed(RecipeDTO recipeDTO);
	}
}
