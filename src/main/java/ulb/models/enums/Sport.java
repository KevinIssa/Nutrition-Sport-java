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
 * Enum representing various sports.
 */
public enum Sport {
	WALKING,
	RUNNING,
	BIKING,
	SWIMMING,
	VOLLEYBALL;

	/**
	 * Converts a string representation of sport to the corresponding enum value.
	 *
	 * @param sport The string representation of the sport.
	 * @return The enum value corresponding to the given string.
	 * @throws IllegalArgumentException If the provided string does not match any known sport.
	 */
	public static Sport fromString(String sport) {
		switch (sport.toLowerCase()) {
			case "walking":
				return WALKING;
			case "running":
				return RUNNING;
			case "biking":
				return BIKING;
			case "swimming":
				return SWIMMING;
			case "volleyball":
				return VOLLEYBALL;
			default:
				throw new IllegalArgumentException("Invalid sport: " + sport);
		}
	}

	/**
	 * Converts the enum value to its corresponding localized string representation.
	 *
	 * @return The localized string representation of the sport.
	 */
	@Override
	public String toString() {
		switch (this) {
			case WALKING:
				return "Marche";
			case RUNNING:
				return "Course à pied";
			case BIKING:
				return "Vélo";
			case SWIMMING:
				return "Natation";
			case VOLLEYBALL:
				return "Volleyball";
			default:
				throw new IllegalStateException("Unexpected value: " + this);
		}
	}
}
