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
 * The Sport enum represents different types of sports.
 * It has five values: WALKING, RUNNING, BIKING, SWIMMING, and VOLLEYBALL.
 */
public enum Sport {
	WALKING,
	RUNNING,
	BIKING,
	SWIMMING,
	VOLLEYBALL;

	/**
	 * Converts a string to its corresponding Sport enum value.
	 *
	 * @param sport The string to be converted.
	 * @return The corresponding Sport enum value.
	 * @throws IllegalArgumentException If the string does not correspond to any Sport enum value.
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
	 * Converts the enum value to its corresponding string representation.
	 *
	 * @return The string representation of the sport.
	 * @throws IllegalStateException If the enum value is not recognized.
	 */
	@Override
	public String toString() {
		switch (this) {
			case WALKING:
				return "walking";
			case RUNNING:
				return "running";
			case BIKING:
				return "biking";
			case SWIMMING:
				return "swimming";
			case VOLLEYBALL:
				return "volleyball";
			default:
				throw new IllegalStateException("Unexpected value: " + this);
		}
	}
}
