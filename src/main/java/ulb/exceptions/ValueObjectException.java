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
 * This abstract class represents a custom exception for value objects.
 * It extends the Exception class and can be used to throw exceptions with a custom message or cause.
 */
public abstract class ValueObjectException extends Exception {

	/**
	 * Constructor for ValueObjectException with a specified message.
	 * @param message The message for the exception.
	 */
	public ValueObjectException(String message) {
		super(message);
	}

	/**
	 * Constructor for ValueObjectException with a specified message and cause.
	 * @param message The message for the exception.
	 * @param cause The cause of the exception.
	 */
	public ValueObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for ValueObjectException with a specified cause.
	 * @param cause The cause of the exception.
	 */
	public ValueObjectException(Throwable cause) {
		super(cause);
	}
}
