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
 * This is an abstract class named ViewException that extends the Exception class.
 * This class is used as a base class for other exception classes in the application.
 */
public abstract class ViewException extends Exception {

	/**
	 * This is a protected constructor for the ViewException class.
	 * It takes two parameters: message and cause.
	 *
	 * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
	 *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	protected ViewException(String message, Throwable cause) {
		super(message, cause);
	}
}
