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

public enum Intensity {
	SLOW,
	MODERATE,
	INTENSE;

	public static Intensity fromString(String selectedIntensity) {
		switch (selectedIntensity) {
			case "Slow":
				return SLOW;
			case "Moderate":
				return MODERATE;
			case "Intense":
				return INTENSE;
			default:
				throw new IllegalArgumentException("Invalid intensity: " + selectedIntensity);
		}
	}

	@Override
	public String toString() {
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
