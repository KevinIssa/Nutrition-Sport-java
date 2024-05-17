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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.RecipeDTO;
import ulb.widgets.MealBox;

public class MealRecipeViewController implements ViewController {

	private static final Logger logger = LoggerFactory.getLogger(MealRecipeViewController.class);
	@FXML private ListView<MealBox> mealList;

	private MealRecipeViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		logger.info("Initializing MealRecipeViewController");
	}

	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealRecipeViewController.Listener) listener;
		this.showRecipes();
	}

	private MealBox makeMealBox(RecipeDTO meal) throws IOException {
		Button deleteButton = new Button("");
		Button editButton = new Button("");
		Button checkButton = new Button("");

		MealBox mealBox = new MealBox(meal, checkButton, deleteButton, editButton);

		checkButton.setOnAction(e -> this.checkMeal(mealBox));
		editButton.setOnAction(e -> this.editMeal(mealBox));
		deleteButton.setOnAction(e -> this.deleteMeal(mealBox));
		return mealBox;
	}

	private void showRecipes() {
		this.mealList.getItems().clear();
		List<RecipeDTO> meals = this.loadRecipes();
		for (RecipeDTO meal : meals) {
			try {
				MealBox mealBox = this.makeMealBox(meal);
				this.mealList.getItems().add(mealBox);
			} catch (IOException e) {
				logger.error("Error while creating meal box", e);
				this.showAlert("Erreur de chargement d'Image", e.getMessage());
				this.returnHome();
			}
		}
	}

	private List<RecipeDTO> loadRecipes() {
		return this.listener.loadRecipes();
	}

	private void refreshMealList() {
		this.mealList.getItems().clear();
		this.showRecipes();
	}

	private void checkMeal(MealBox meal) {
		this.listener.checkMeal(meal.getMealDTO());
		this.refreshMealList();
	}

	private void editMeal(MealBox meal) {
		this.deleteMeal(meal);
		this.listener.editMeal(meal.getMealDTO());
		this.refreshMealList();
	}

	private void deleteMeal(MealBox meal) {
		this.listener.deleteMeal(meal.getMealDTO());
		this.refreshMealList();
	}

	@FXML
	private void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		List<RecipeDTO> loadRecipes();

		void checkMeal(RecipeDTO meal);

		void editMeal(RecipeDTO meal);

		void deleteMeal(RecipeDTO meal);

		void returnHome();
	}
}
