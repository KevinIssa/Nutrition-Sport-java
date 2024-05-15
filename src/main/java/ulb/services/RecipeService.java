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
package ulb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.FoodDTO;
import ulb.dtos.MealDTO;
import ulb.models.Food;
import ulb.models.Meal;
import ulb.repositories.RecipeRepository;

public class RecipeService {

	private final RecipeRepository recipeRepository;
	private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);
	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public void deleteMeal(MealDTO meal) {
		logger.info("Deleting a recipe and its associated file");
		meal.delete();
	}

	public List<MealDTO> loadAllRecipes() {
		// TODO: change that
		List<Meal> meals = Meal.loadAll();
		List<MealDTO> mealDTOS = new ArrayList<>();
		for (Meal meal : meals) {
			List<FoodDTO> foodDTOS = new ArrayList<>();
			for (Map.Entry<Food, Double> food : meal.getIngredients())
				foodDTOS.add(
						new FoodDTO(
								food.getKey().getName(), food.getValue(), food.getKey().getUnit()));
			mealDTOS.add(new MealDTO(meal.getName(), foodDTOS));
		}
		return mealDTOS;
	}
}
