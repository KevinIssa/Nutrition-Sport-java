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
	public Label calories;
	public ListView foodList;

	private MealDetailsViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Empty method, no initialization needed (java:S1186)
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealDetailsViewController.Listener) listener;
	}

	public void setMeal(RecipeDTO recipeDTO) {
		mealName.setText(recipeDTO.name());
		calories.setText(this.listener.getCaloriesConsumed(recipeDTO) + "kcal");
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
		Label labelMealName = createLabel(food.name(), 100);
		Label labelMealQuantity = createLabel("quantite: " + food.quantity() + food.unit(), 100);
		hbox.getChildren().add(0, labelMealName);
		hbox.getChildren().add(1, labelMealQuantity);
	}

	@SuppressWarnings("SameParameterValue") // Suppressing warning for the width parameter
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

	/**
	 * Listener interface for the MealDetailsViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the MealDetailsViewController.
	 */
	public interface Listener {

		/**
		 * Triggers the return to the home view.
		 * This method should be called when the user wants to return to the home view.
		 */
		void returnHome();

		/**
		 * Retrieves the total calories consumed for a given recipe.
		 * This method should be implemented to return the total calories consumed for a given recipe.
		 *
		 * @param recipeDTO The RecipeDTO object representing the recipe for which the total calories consumed are to be retrieved.
		 * @return The total calories consumed for the given recipe.
		 */
		double getCaloriesConsumed(RecipeDTO recipeDTO);
	}
}
