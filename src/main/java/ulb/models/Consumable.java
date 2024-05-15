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

import ulb.enums.Unit;

/**
 * The Consumable interface represents any item that can be consumed and has a caloric value.
 * It provides methods to get the caloric value by unit and by serving, as well as methods to get the name, unit, and serving of the consumable.
 * This interface also extends Comparable, allowing for comparison between Consumable objects.
 */
public interface Consumable extends Comparable<Consumable> {

	/**
	 * Checks if the current object is equal to the provided object.
	 *
	 * @param obj The object to compare with the current object.
	 * @return true if the objects are equal, false otherwise.
	 */
	boolean equals(Object obj);

	/**
	 * Compares the current object with the provided Consumable object.
	 *
	 * @param obj The Consumable object to compare with the current object.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	default int compareTo(Consumable obj) {
		return this.getName().toLowerCase().compareTo(obj.getName().toLowerCase());
	}

	/**
	 * Returns the number of calories consumed by a given unit.
	 *
	 * @param unit The unit of the consumable to calculate the calories for.
	 * @return The number of calories consumed by the given unit.
	 */
	double getCaloriesConsumedByUnit(double unit);

	/**
	 * Returns the number of calories consumed by a given number of servings.
	 *
	 * @param servings The number of servings to calculate the calories for.
	 * @return The number of calories consumed by the given number of servings.
	 */
	double getCaloriesConsumedByServing(double servings);

	/**
	 * Returns the name of the consumable.
	 *
	 * @return The name of the consumable.
	 */
	String getName();

	/**
	 * Returns the unit of the consumable.
	 *
	 * @return The unit of the consumable.
	 */
	Unit getUnit();

	/**
	 * Returns the serving of the consumable.
	 *
	 * @return The serving of the consumable.
	 */
	String getServingQuantity();
}
