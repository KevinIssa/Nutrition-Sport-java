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
import ulb.models.Consumable;
import ulb.models.Meal;

public interface ConsumableRepository {
	List<Consumable> loadAll();

	List<Meal> loadAllMeals();

	Consumable load(String name);

	List<Consumable> loadAllBeginningWith(String prefix);
}
