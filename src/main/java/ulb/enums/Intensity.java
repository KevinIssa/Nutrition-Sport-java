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
 * The Intensity enum represents the intensity levels of a certain activity or condition.
 * It has three levels: SLOW, MODERATE, and INTENSE.
 */
public enum Intensity {
	SLOW,
	MODERATE,
	INTENSE;

	/**
	 * Converts an integer to its corresponding Intensity enum value.
	 *
	 * @param number The integer to be converted.
	 * @return The corresponding Intensity enum value.
	 * @throws IllegalArgumentException If the integer does not correspond to any Intensity enum value.
	 */
	public static Intensity fromInt(int number) {
		return switch (number) {
			case 0 -> SLOW;
			case 1 -> MODERATE;
			case 2 -> INTENSE;
			default -> throw new IllegalArgumentException("Invalid intensity : " + number);
		};
	}

	/**
	 * Converts the enum value to its corresponding string representation.
	 *
	 * @return The string representation of the intensity.
	 * @throws IllegalStateException If the enum value is not recognized.
	 */
	@Override
	public String toString() {
		return switch (this) {
			case SLOW -> "slow";
			case MODERATE -> "moderate";
			case INTENSE -> "intense";
		};
	}
}
