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
package ulb.controllers;

import java.util.List;
import javafx.stage.Stage;
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;
import ulb.enums.Unit;
import ulb.views.MakeMealViewController;

public class EditMealController implements MakeMealViewController.Listener {
	private final Stage stage;
	private final MakeMealViewController makeMealViewController;
	private final Listener listener;

	public EditMealController(Stage stage, EditMealController.Listener listener) {
		this.stage = stage;
		this.makeMealViewController = new MakeMealViewController();
		this.makeMealViewController.setListener(this);
		this.listener = listener;
	}

	public void hide() {
		this.stage.hide();
	}

	public void show(RecipeDTO meal) {
		this.stage.show();
		this.makeMealViewController.setMeal(meal);
	}

	@Override
	public void askUserFoodQuantity(String searchText) {
		// TODO Implement

	}

	@Override
	public List<String> getUserSearch(String searchText) {
		// TODO Implement

		return List.of();
	}

	@Override
	public double getCaloriesConsumed(FoodDTO foodDTO) {
		// TODO Implement

		return 0;
	}

	@Override
	public Unit getFoodUnit(String food) {
		// TODO Implement
		return Unit.GRAMS;
	}

	@Override
	public void returnHome() {
		// TODO Implement

	}

	@Override
	public void saveMeal(RecipeDTO recipeDTO) {
		// TODO Implement
		this.returnHome();
	}


	@Override
	public void changeMode() {
		// TODO Implement

	}

	public void editMeal(RecipeDTO meal) {}

	public interface Listener {
		// TODO Implement

	}
}
