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
package ulb.repositories;

import java.time.LocalDateTime;
import java.util.List;
import ulb.exceptions.SavingException;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;

/**
 * This is the ConsumeMealRepository interface.
 * It provides the necessary methods for managing ConsumedMeal and ConsumedFood objects in the repository.
 */
public interface ConsumeMealRepository {

	/**
	 * Saves a ConsumedMeal object to the repository.
	 *
	 * @param consumedMeal The ConsumedMeal object to be saved.
	 */
	void save(ConsumedMeal consumedMeal) throws SavingException;

	/**
	 * Retrieves all ConsumedMeal objects from the repository.
	 *
	 * @return A list of all ConsumedMeal objects in the repository.
	 */
	List<ConsumedMeal> loadAll();

	/**
	 * Deletes a ConsumedFood object from the repository.
	 *
	 * @param consumedFood The ConsumedFood object to be deleted.
	 */
	void delete(ConsumedFood consumedFood, LocalDateTime date);

	/**
	 * Deletes a ConsumedMeal object from the repository.
	 *
	 * @param consumedMeal The ConsumedMeal object to be deleted.
	 */
	void delete(ConsumedMeal consumedMeal);
}
