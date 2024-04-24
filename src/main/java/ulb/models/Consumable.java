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

/**
 * The Consumable interface represents any item that can be consumed.
 * It provides methods to calculate the calories consumed when this item is eaten.
 */
public interface Consumable {

	/**
	 * Computes the calories consumed by eating this consumable.
	 * The specific implementation of this method depends on the class that implements this interface.
	 *
	 * @return the number of calories consumed by eating this consumable
	 */
	int getCaloriesConsumed();

	/**
	 * Computes the calories consumed by eating a certain amount of this consumable, measured in grams.
	 * The specific implementation of this method depends on the class that implements this interface.
	 *
	 * @param grams the amount of the consumable in grams
	 * @return the number of calories consumed by eating the given amount of this consumable
	 */
	int getCaloriesConsumedByGrams(int grams);

	/**
	 * Computes the calories consumed by eating a certain amount of this consumable, measured in servings.
	 * The specific implementation of this method depends on the class that implements this interface.
	 *
	 * @param servings the amount of the consumable in servings
	 * @return the number of calories consumed by eating the given amount of this consumable
	 */
	int getCaloriesConsumedByServing(int servings);
}
