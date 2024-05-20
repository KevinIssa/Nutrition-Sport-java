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

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.ConsumedMealDTO;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;
import ulb.repositories.ConsumableRepository;
import ulb.repositories.ConsumeMealRepository;

public class ConsumeMealService {
	private static final Logger logger = // NOSONAR
			LoggerFactory.getLogger(
					ConsumeMealService
							.class); // java:S1068 - Unused private fields should be removed (future use)

	private final ConsumeMealRepository consumeMealRepository;
	private final ConsumableRepository consumableRepository; //java:S1068 - check if future use

	public ConsumeMealService(
			ConsumeMealRepository consumeMealRepository,
			ConsumableRepository consumableRepository) {
		this.consumeMealRepository = consumeMealRepository;
		this.consumableRepository = consumableRepository;
	}

	public void saveConsumedMeal(ConsumedMealDTO consumedMealDTO) {
		this.saveConsumedMeal(this.convertToConsumedMeal(consumedMealDTO));
	}

	public void saveConsumedMeal(ConsumedMeal consumedMeal) {
		this.consumeMealRepository.save(consumedMeal);
	}

	public List<ConsumedMealDTO> getConsumedMeals() {
		return this.consumeMealRepository.loadAll().stream()
				.map(this::convertToConsumedMealDTO)
				.toList();
	}

	public void deleteConsumedMeal(ConsumedMealDTO consumedMealDTO) {
		this.consumeMealRepository.delete(this.convertToConsumedMeal(consumedMealDTO));
	}

	public void deleteConsumedFood(ConsumedFoodDTO consumedFoodDTO, LocalDateTime date) {
		this.consumeMealRepository.delete(this.convertToConsumedFood(consumedFoodDTO), date);
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
		return new ConsumedFood(
				name,
				consumedFoodDTO.calories(),
				consumedFoodDTO.quantity(),
				consumedFoodDTO.unit());
	}

	private ConsumedMealDTO convertToConsumedMealDTO(ConsumedMeal consumedMeal) {
		List<ConsumedFoodDTO> consumedFoods =
				consumedMeal.getConsumedFoods().stream()
						.map(this::convertToConsumedFoodDTO)
						.toList();
		return new ConsumedMealDTO(consumedFoods, consumedMeal.getDate());
	}

	private ConsumedFoodDTO convertToConsumedFoodDTO(ConsumedFood consumedFood) {
		return new ConsumedFoodDTO(
				consumedFood.getName(),
				consumedFood.getQuantity(),
				consumedFood.getCalories(),
				consumedFood.getUnit());
	}
}
