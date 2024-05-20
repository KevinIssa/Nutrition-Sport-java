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
package ulb.enums;

/**
 * This is an enumeration named Unit. An enumeration is a special type of class in Java that represents a group of constants (unchangeable variables, like final variables).
 * This enumeration has three constants: GRAMS, MILLILITERS, and ALL.
 * <p>
 * The toString method is overridden to return a specific string based on the constant.
 */
public enum Unit {
	GRAMS, // Represents the unit of measurement in grams.
	MILLILITERS, // Represents the unit of measurement in milliliters.
	ALL; // Represents both units of measurement.

	/**
	 * This method is overridden from the Object class.
	 * It returns a string representation of the constant.
	 *
	 * @return A string that represents the constant.
	 */
	@Override
	public String toString() {
		return switch (this) {
			case GRAMS -> "g"; // If the constant is GRAMS, it returns "g".
			case MILLILITERS -> "ml"; // If the constant is MILLILITERS, it returns "ml".
			case ALL -> "g/ml"; // If the constant is ALL, it returns "g/ml".
		};
	}
}
