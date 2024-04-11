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
 * Enum representing the intensity of an activity.
 */
public enum Intensity {
	SLOW,
	MODERATE,
	INTENSE;

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
	 * Converts a string representation of intensity to the corresponding enum value.
	 *
	 * @param selectedIntensity The string representation of intensity.
	 * @return The enum value corresponding to the given string.
	 * @throws IllegalArgumentException If the provided string does not match any known intensity.
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
	 * Converts the enum value to its corresponding localized string representation.
	 *
	 * @return The localized string representation of the intensity.
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

	public String toFrench() {
		switch (this) {
			case SLOW:
				return "Lent";
			case MODERATE:
				return "Modéré";
			case INTENSE:
				return "Intense";
			default:
				throw new IllegalStateException("Unexpected value: " + this);
		}
	}
}
