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
		switch (number) {
			case 0:
				return SLOW;
			case 1:
				return MODERATE;
			case 2:
				return INTENSE;
			default:
				throw new IllegalArgumentException("Invalid intensity : " + number);
		}
	}

	/**
	 * Converts a string to its corresponding Intensity enum value.
	 *
	 * @param selectedIntensity The string to be converted.
	 * @return The corresponding Intensity enum value.
	 * @throws IllegalArgumentException If the string does not correspond to any Intensity enum value.
	 */
	public static Intensity fromString(String selectedIntensity) {
		switch (selectedIntensity) {
			case "Slow":
				return SLOW;
			case "Moderate":
				return MODERATE;
			case "Intense":
				return INTENSE;
			default:
				throw new IllegalArgumentException("Invalid intensity : " + selectedIntensity);
		}
	}

	/**
	 * Converts the enum value to its corresponding string representation.
	 *
	 * @return The string representation of the intensity.
	 * @throws IllegalStateException If the enum value is not recognized.
	 */
	@Override
	public String toString() {
		switch (this) {
			case SLOW:
				return "slow";
			case MODERATE:
				return "moderate";
			case INTENSE:
				return "intense";
			default:
				throw new IllegalStateException("Unexpected value: " + this);
		}
	}
}
