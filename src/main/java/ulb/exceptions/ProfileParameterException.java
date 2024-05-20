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

/**
 * This is a class named ProfileParameterException that extends the ViewException class.
 * This class is used to handle exceptions related to profile parameters in the application.
 */
public class ProfileParameterException extends ViewException {

	/**
	 * This is a constructor for the ProfileParameterException class.
	 * It takes two parameters: message and cause.
	 *
	 * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
	 *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ProfileParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
