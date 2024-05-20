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
import ulb.exceptions.SavingException;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;
import ulb.repositories.ConsumeMealRepository;

/**
 * This class provides services related to ConsumedMeals.
 * It uses a ConsumeMealRepository to perform CRUD operations on ConsumedMeals and ConsumedFoods.
 */
public class ConsumeMealService {
	/**
	 * Logger for this class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConsumeMealService.class);

	/**
	 * Repository for performing CRUD operations on ConsumedMeals and ConsumedFoods.
	 */
	private final ConsumeMealRepository consumeMealRepository;

	/**
	 * Constructor for ConsumeMealService.
	 *
	 * @param consumeMealRepository Repository for performing CRUD operations on ConsumedMeals and ConsumedFoods.
	 */
	public ConsumeMealService(ConsumeMealRepository consumeMealRepository) {
		this.consumeMealRepository = consumeMealRepository;
	}

	/**
	 * Saves a ConsumedMeal.
	 *
	 * @param consumedMeal The ConsumedMeal to save.
	 * @throws SavingException If an error occurs while saving the ConsumedMeal.
	 */
	public void saveConsumedMeal(ConsumedMeal consumedMeal) throws SavingException {
		this.consumeMealRepository.save(consumedMeal);
	}

	/**
	 * Loads all ConsumedMeals.
	 *
	 * @return A list of all ConsumedMeals.
	 */
	public List<ConsumedMealDTO> getConsumedMeals() {
		return this.consumeMealRepository.loadAll().stream()
				.map(this::convertToConsumedMealDTO)
				.toList();
	}

	/**
	 * Deletes a specific ConsumedFood from a ConsumedMeal on a specific date.
	 *
	 * @param consumedFoodDTO The ConsumedFood to delete.
	 * @param date The date of the ConsumedMeal to delete the ConsumedFood from.
	 */
	public void deleteConsumedFood(ConsumedFoodDTO consumedFoodDTO, LocalDateTime date) {
		this.consumeMealRepository.delete(this.convertToConsumedFood(consumedFoodDTO), date);
	}

	/**
	 * Converts a ConsumedFoodDTO to a ConsumedFood.
	 *
	 * @param consumedFoodDTO The ConsumedFoodDTO to convert.
	 * @return The converted ConsumedFood.
	 */
	private ConsumedFood convertToConsumedFood(ConsumedFoodDTO consumedFoodDTO) {
		String name = consumedFoodDTO.name();
		return new ConsumedFood(
				name,
				consumedFoodDTO.calories(),
				consumedFoodDTO.quantity(),
				consumedFoodDTO.unit());
	}

	/**
	 * Converts a ConsumedMeal to a ConsumedMealDTO.
	 *
	 * @param consumedMeal The ConsumedMeal to convert.
	 * @return The converted ConsumedMealDTO.
	 */
	private ConsumedMealDTO convertToConsumedMealDTO(ConsumedMeal consumedMeal) {
		List<ConsumedFoodDTO> consumedFoods =
				consumedMeal.getConsumedFoods().stream()
						.map(this::convertToConsumedFoodDTO)
						.toList();
		return new ConsumedMealDTO(consumedFoods, consumedMeal.getDate());
	}

	/**
	 * Converts a ConsumedFood to a ConsumedFoodDTO.
	 *
	 * @param consumedFood The ConsumedFood to convert.
	 * @return The converted ConsumedFoodDTO.
	 */
	private ConsumedFoodDTO convertToConsumedFoodDTO(ConsumedFood consumedFood) {
		return new ConsumedFoodDTO(
				consumedFood.getName(),
				consumedFood.getQuantity(),
				consumedFood.getCalories(),
				consumedFood.getUnit());
	}
}
