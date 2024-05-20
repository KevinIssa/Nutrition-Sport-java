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
import ulb.dtos.ConsumableDTO;
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;
import ulb.exceptions.SavingException;
import ulb.models.Consumable;
import ulb.models.Recipe;
import ulb.repositories.ConsumableRepository;

/**
 * This class provides services related to Consumables.
 * It uses a ConsumableRepository to perform CRUD operations on Consumables and Recipes.
 */
public class ConsumableService {
	/**
	 * Logger for this class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConsumableService.class);

	/**
	 * Repository for performing CRUD operations on Consumables and Recipes.
	 */
	private final ConsumableRepository consumableRepository;

	/**
	 * Constructor for ConsumableService.
	 *
	 * @param consumableRepository Repository for performing CRUD operations on Consumables and Recipes.
	 */
	public ConsumableService(ConsumableRepository consumableRepository) {
		this.consumableRepository = consumableRepository;
	}

	/**
	 * Saves a meal.
	 *
	 * @param recipeDTO The meal to save.
	 * @throws SavingException If an error occurs while saving the meal.
	 */
	public void saveMeal(RecipeDTO recipeDTO) throws SavingException {
		this.consumableRepository.save(this.convertToMeal(recipeDTO));
	}

	/**
	 * Loads all Consumables that begin with a specific prefix.
	 *
	 * @param prefix The prefix of the Consumables to load.
	 * @return A list of all Consumables that begin with the specified prefix.
	 */
	public List<ConsumableDTO> loadConsumablesBeginningWith(String prefix) {
		return this.consumableRepository.loadAllBeginningWith(prefix).stream()
				.map(this::convertToConsumableDTO)
				.toList();
	}

	/**
	 * Loads a specific Consumable by its name.
	 *
	 * @param name The name of the Consumable to load.
	 * @return The Consumable with the specified name.
	 */
	public ConsumableDTO loadConsumable(String name) {
		return this.convertToConsumableDTO(this.consumableRepository.load(name));
	}

	/**
	 * Converts a Consumable to a ConsumableDTO.
	 *
	 * @param consumable The Consumable to convert.
	 * @return The converted ConsumableDTO.
	 */
	private ConsumableDTO convertToConsumableDTO(Consumable consumable) {
		return new ConsumableDTO(
				consumable.getName(), consumable.getUnit(), consumable.getServingQuantity());
	}

	/**
	 * Converts a RecipeDTO to a Recipe.
	 *
	 * @param recipeDTO The RecipeDTO to convert.
	 * @return The converted Recipe.
	 */
	private Recipe convertToMeal(RecipeDTO recipeDTO) {
		Recipe recipe = new Recipe(recipeDTO.name());
		recipeDTO
				.foods()
				.forEach(
						foodDTO ->
								recipe.addIngredient(
										this.convertToConsumable(foodDTO),
										foodDTO.quantity() / recipeDTO.personAmount()));
		return recipe;
	}

	/**
	 * Converts a FoodDTO to a Consumable.
	 *
	 * @param foodDTO The FoodDTO to convert.
	 * @return The converted Consumable.
	 */
	private Consumable convertToConsumable(FoodDTO foodDTO) {
		return this.consumableRepository.load(foodDTO.name());
	}

	/**
	 * Loads a specific Consumable by its name.
	 *
	 * @param foodName The name of the Consumable to load.
	 * @return The Consumable with the specified name.
	 */
	public Consumable getConsumableByName(String foodName) {
		return this.consumableRepository.load(foodName);
	}

	/**
	 * Deletes all Consumables.
	 */
	public void deleteAllConsumables() {
		this.consumableRepository.deleteAll();
	}

	/**
	 * Calculates the total calories consumed in a meal.
	 *
	 * @param recipeDTO The meal to calculate the total calories for.
	 * @return The total calories consumed in the meal.
	 */
	public double getCaloriesConsumed(RecipeDTO recipeDTO) {
		return recipeDTO.foods().stream()
				.mapToDouble(
						foodDTO ->
								this.consumableRepository
										.load(foodDTO.name())
										.getCaloriesConsumedByUnit(foodDTO.quantity()))
				.sum();
	}

	/**
	 * Deletes a specific meal.
	 *
	 * @param meal The meal to delete.
	 */
	public void deleteMeal(RecipeDTO meal) {
		logger.info("Deleting a recipe and its associated file");
		this.consumableRepository.deleteRecipe(meal);
	}

	/**
	 * Loads all meals.
	 *
	 * @return A list of all meals.
	 */
	public List<RecipeDTO> loadAllRecipes() {
		List<RecipeDTO> meals = new ArrayList<>();
		for (Recipe recipe : this.consumableRepository.loadAllMeals()) {
			List<FoodDTO> foods = new ArrayList<>();
			for (Map.Entry<Consumable, Double> entry : recipe.getIngredients()) {
				Consumable food = entry.getKey();
				Double quantity = entry.getValue();
				FoodDTO foodDTO = new FoodDTO(food.getName(), quantity, food.getUnit());
				foods.add(foodDTO);
			}
			RecipeDTO recipeDTO = new RecipeDTO(recipe.getName(), foods);
			meals.add(recipeDTO);
		}
		return meals;
	}

	/**
	 * Deletes all meals.
	 */
	public void deleteAllRecipes() {
		logger.info("Deleting all recipes");
		this.consumableRepository.deleteAllRecipes();
	}
}
