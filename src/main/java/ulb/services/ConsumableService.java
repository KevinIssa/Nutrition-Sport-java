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

import java.util.List;
import ulb.dtos.ConsumableDTO;
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;
import ulb.models.Consumable;
import ulb.models.Recipe;
import ulb.repositories.ConsumableRepository;

public class ConsumableService {

	private final ConsumableRepository consumableRepository;

	public ConsumableService(ConsumableRepository consumableRepository) {
		this.consumableRepository = consumableRepository;
	}

	public void saveMeal(RecipeDTO recipeDTO) {
		this.consumableRepository.save(this.convertToMeal(recipeDTO));
	}

	public List<ConsumableDTO> loadConsumables() {
		return this.consumableRepository.loadAll().stream()
				.map(this::convertToConsumableDTO)
				.toList();
	}

	public List<ConsumableDTO> loadConsumablesBeginningWith(String prefix) {
		return this.consumableRepository.loadAllBeginningWith(prefix).stream()
				.map(this::convertToConsumableDTO)
				.toList();
	}

	public ConsumableDTO loadConsumable(String name) {
		return this.convertToConsumableDTO(this.consumableRepository.load(name));
	}

	private ConsumableDTO convertToConsumableDTO(Consumable consumable) {
		return new ConsumableDTO(
				consumable.getName(), consumable.getUnit(), consumable.getServingQuantity());
	}

	private Recipe convertToMeal(RecipeDTO recipeDTO) {
		Recipe recipe = new Recipe(recipeDTO.name());
		recipeDTO
				.foods()
				.forEach(
						foodDTO ->
								recipe.addIngredient(
										this.convertToConsumable(foodDTO), foodDTO.quantity()));
		return recipe;
	}

	private Consumable convertToConsumable(FoodDTO foodDTO) {
		return this.consumableRepository.load(foodDTO.name());
	}
}
