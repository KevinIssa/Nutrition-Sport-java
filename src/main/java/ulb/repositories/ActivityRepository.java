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
import ulb.models.Activity;

/**
 * This interface represents the repository for Activity objects.
 * It provides methods for saving, loading, deleting activities, and calculating calories burned.
 */
public interface ActivityRepository {

	/**
	 * Saves the given Activity object.
	 * @param activity The Activity object to be saved.
	 */
	void save(Activity activity);

	/**
	 * Loads all Activity objects.
	 * @return A list of all Activity objects.
	 */
	List<Activity> loadAll();

	/**
	 * Deletes the given Activity object.
	 * @param activity The Activity object to be deleted.
	 */
	void delete(Activity activity);

	/**
	 * Deletes all Activity objects.
	 */
	void deleteAll();

	/**
	 * Calculates the calories burned for the given Activity object and weight.
	 * @param activity The Activity object for which the calories burned is to be calculated.
	 * @param weight The weight of the user.
	 * @return The number of calories burned.
	 */
	int calculateCaloriesBurned(Activity activity, float weight);
}
