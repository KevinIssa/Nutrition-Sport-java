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
	 * Converts the enum value to its corresponding string representation.
	 *
	 * @return The string representation of the sport.
	 * @throws IllegalStateException If the enum value is not recognized.
	 */
	@Override
	public String toString() {
		return switch (this) {
			case WALKING -> "walking";
			case RUNNING -> "running";
			case BIKING -> "biking";
			case SWIMMING -> "swimming";
			case VOLLEYBALL -> "volleyball";
		};
	}
}
