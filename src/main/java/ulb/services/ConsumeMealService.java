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
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.ConsumedMealDTO;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.repositories.ConsumeMealRepository;

public class ConsumeMealService {

	private final ConsumeMealRepository consumeMealRepository;

	private final FoodLoader foodLoader = new FoodLoader(); // TODO: Change this

	public ConsumeMealService(ConsumeMealRepository consumeMealRepository) {
		this.consumeMealRepository = consumeMealRepository;
	}

	public void saveConsumedMeal(ConsumedMealDTO consumedMealDTO) {
		this.consumeMealRepository.save(this.convertToConsumedMeal(consumedMealDTO));
	}

	public List<ConsumedMealDTO> getConsumedMeals() {
		return this.consumeMealRepository.loadAll().stream()
				.map(this::convertToConsumedMealDTO)
				.toList();
	}

	public void deleteConsumedMeal(ConsumedMealDTO consumedMealDTO) {
		this.consumeMealRepository.delete(this.convertToConsumedMeal(consumedMealDTO));
	}

	public void deleteAllConsumedMeals() {
		this.consumeMealRepository.deleteAll();
	}

	private ConsumedMeal convertToConsumedMeal(ConsumedMealDTO consumedMealDTO) {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		for (ConsumedFoodDTO consumedFoodDTO : consumedMealDTO.consumedFoods()) {
			consumedMeal.addConsumedFood(this.convertToConsumedFood(consumedFoodDTO));
		}
		return consumedMeal;
	}

	private ConsumedFood convertToConsumedFood(ConsumedFoodDTO consumedFoodDTO) {
		String name = consumedFoodDTO.name();
		Food food = this.foodLoader.getFoodByName(name);
		return new ConsumedFood(
				name,
				consumedFoodDTO.quantity(),
				food.getCaloriesConsumedByGrams(consumedFoodDTO.quantity()),
				food.getServingType());
	}

	private ConsumedMealDTO convertToConsumedMealDTO(ConsumedMeal consumedMeal) {
		List<ConsumedFoodDTO> consumedFoods =
				consumedMeal.getConsumedFoods().stream()
						.map(this::convertToConsumedFoodDTO)
						.toList();
		return new ConsumedMealDTO(consumedFoods, consumedMeal.getDate());
	}

	private ConsumedFoodDTO convertToConsumedFoodDTO(ConsumedFood consumedFood) {
		Food food = foodLoader.getFoodByName(consumedFood.getName());
		return new ConsumedFoodDTO(
				consumedFood.getName(),
				consumedFood.getQuantity(),
				consumedFood.getCalories(),
				food != null ? food.getServingType() : "g");
	}
}
