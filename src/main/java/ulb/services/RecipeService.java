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
import ulb.repositories.ConsumableRepository;
import ulb.repositories.RecipeRepository;

public class RecipeService {
	private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

	private final RecipeRepository recipeRepository;
	private final ConsumableRepository consumableRepository;

	public RecipeService(
			RecipeRepository recipeRepository, ConsumableRepository consumableRepository) {
		this.recipeRepository = recipeRepository;
		this.consumableRepository = consumableRepository;
	}

	public void deleteMeal(MealDTO meal) {
		logger.info("Deleting a recipe and its associated file");
		meal.delete();
	}

	public List<MealDTO> loadAllRecipes() {
		List<MealDTO> meals = new ArrayList<>();
		for (Meal meal : this.consumableRepository.loadAllMeals()) {
			List<FoodDTO> foods = new ArrayList<>();
			for (Map.Entry<Food, Double> entry : meal.getIngredients()) {
				Food food = entry.getKey();
				Double quantity = entry.getValue();
				FoodDTO foodDTO = new FoodDTO(food.getName(), quantity, food.getUnit());
				foods.add(foodDTO);
			}
			MealDTO mealDTO = new MealDTO(meal.getName(), foods);
			meals.add(mealDTO);
		}
		return meals;
	}
}
