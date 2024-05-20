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

import java.util.List;
import ulb.dtos.RecipeDTO;
import ulb.exceptions.SavingException;
import ulb.models.Consumable;
import ulb.models.Recipe;

/**
 * This is an interface named ConsumableRepository.
 * It defines methods for performing CRUD operations on Consumables and Recipes.
 */
public interface ConsumableRepository {

	/**
	 * This method is used to load all Consumables.
	 *
	 * @return A list of all Consumables.
	 */
	List<Consumable> loadAll();

	/**
	 * This method is used to load all Recipes.
	 *
	 * @return A list of all Recipes.
	 */
	List<Recipe> loadAllMeals();

	/**
	 * This method is used to load a specific Consumable by its name.
	 *
	 * @param name The name of the Consumable to load.
	 * @return The Consumable with the specified name.
	 */
	Consumable load(String name);

	/**
	 * This method is used to load all Consumables that begin with a specific prefix.
	 *
	 * @param prefix The prefix of the Consumables to load.
	 * @return A list of all Consumables that begin with the specified prefix.
	 */
	List<Consumable> loadAllBeginningWith(String prefix);

	/**
	 * This method is used to save a Recipe.
	 *
	 * @param recipe The Recipe to save.
	 * @throws SavingException If an error occurs while saving the Recipe.
	 */
	void save(Recipe recipe) throws SavingException;

	/**
	 * This method is used to delete all Consumables.
	 */
	void deleteAll();

	/**
	 * This method is used to delete a specific Recipe.
	 *
	 * @param meal The Recipe to delete.
	 */
	void deleteRecipe(RecipeDTO meal);

	/**
	 * This method is used to delete all Recipes.
	 */
	void deleteAllRecipes();
}
