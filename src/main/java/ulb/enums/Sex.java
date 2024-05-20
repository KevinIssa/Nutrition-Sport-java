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
 * The Sex enum represents the sex of a person or animal.
 * It has two values: MALE and FEMALE.
 */
public enum Sex {
	MALE,
	FEMALE;

	/**
	 * Converts a string to its corresponding Sex enum value.
	 *
	 * @param sex The string to be converted.
	 * @return The corresponding Sex enum value.
	 * @throws IllegalArgumentException If the string does not correspond to any Sex enum value.
	 */
	public static Sex fromString(String sex) {
		return switch (sex) {
			case "♂" -> MALE;
			case "♀" -> FEMALE;
			default -> throw new IllegalArgumentException("Invalid sex: " + sex);
		};
	}

	/**
	 * Converts the enum value to its corresponding string representation.
	 *
	 * @return The string representation of the sex.
	 * @throws IllegalStateException If the enum value is not recognized.
	 */
	@Override
	public String toString() {
		return switch (this) {
			case MALE -> "♂";
			case FEMALE -> "♀";
		};
	}
}
