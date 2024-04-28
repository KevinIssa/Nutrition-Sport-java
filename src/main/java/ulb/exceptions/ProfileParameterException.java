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
package ulb.exceptions;

public class ProfileParameterException extends ViewException {
	public ProfileParameterException(String message) {
		super(message);
	}

	public ProfileParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProfileParameterException(Throwable cause) {
		super(cause);
	}
}
