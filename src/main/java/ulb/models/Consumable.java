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
package ulb.models;

public interface Consumable {

	/**
	 * Compute the calories consumed by eating this consumable.
	 */
	int getCaloriesConsumed();

	/**
	 * Compute the calories consumed by eating a certain amount of this consumable.
	 * @param grams the amount of the consumable in grams
	 * @return the calories consumed by eating the given amount of this consumable
	 */
	int getCaloriesConsumedByGrams(int grams);

	/**
	 * Compute the calories consumed by eating a certain amount of this consumable.
	 * @param servings the amount of the consumable in servings
	 * @return the calories consumed by eating the given amount of this consumable
	 */
	int getCaloriesConsumedByServing(int servings);
}
