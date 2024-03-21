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
package ulb.models.enums;

/**
 * Enum representing the biological sex of an individual.
 */
public enum Sex {
	MALE,
	FEMALE;

	/**
	 * Converts a string representation of sex symbol to the corresponding enum value.
	 *
	 * @param sex The string representation of sex symbol (♂ for male, ♀ for female).
	 * @return The enum value corresponding to the given string.
	 * @throws IllegalArgumentException If the provided string does not match any known sex symbol.
	 */
	public static Sex fromString(String sex) {
		switch (sex) {
			case "♂":
				return MALE;
			case "♀":
				return FEMALE;
			default:
				throw new IllegalArgumentException("Invalid sex: " + sex);
		}
	}

	/**
	 * Converts the enum value to its corresponding sex symbol string representation.
	 *
	 * @return The string representation of the sex symbol (♂ for male, ♀ for female).
	 */
	@Override
	public String toString() {
		switch (this) {
			case MALE:
				return "♂";
			case FEMALE:
				return "♀";
			default:
				throw new IllegalStateException("Unexpected value: " + this);
		}
	}
}
