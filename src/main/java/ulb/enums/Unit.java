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

public enum Unit {
	GRAMS,
	MILLILITERS,
	ALL;

	public static Unit fromString(String unit) {
		return switch (unit) {
			case "g" -> GRAMS;
			case "ml" -> MILLILITERS;
			case "g/ml" -> ALL;
			default -> throw new IllegalArgumentException("Invalid unit: " + unit);
		};
	}

	@Override
	public String toString() {
		return switch (this) {
			case GRAMS -> "g";
			case MILLILITERS -> "ml";
			case ALL -> "g/ml";
			default -> throw new IllegalArgumentException("Invalid unit: " + this);
		};
	}
}
