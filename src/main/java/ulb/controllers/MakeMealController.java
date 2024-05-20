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
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;
import ulb.enums.Unit;
import ulb.views.MakeMealViewController;

public class MakeMealController implements MakeMealViewController.Listener {

	@Override
	public void askUserFoodQuantity(String searchText) {}

	@Override
	public List<String> getUserSearch(String searchText) {
		return List.of();
	}

	@Override
	public double getCaloriesConsumed(FoodDTO foodDTO) {
		return 0;
	}

	@Override
	public Unit getFoodUnit(String food) {
		return Unit.GRAMS;
	}

	@Override
	public void returnHome() {}

	@Override
	public void saveMeal(RecipeDTO recipeDTO) {}

	@Override
	public void changeMode() {}
}
