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

public enum Sex {
	MALE,
	FEMALE;

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

	public String toString() {
		switch (this) {
			case MALE:
				return "♂";
			case FEMALE:
				return "♀";
		}
		return null;
	}
}
