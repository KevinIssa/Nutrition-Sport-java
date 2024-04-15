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
 * The InvalidFieldException class is a custom exception class that extends the Exception class.
 * This exception is thrown when a field does not meet the expected requirements or constraints.
 */
public class InvalidFieldException extends Exception {

	/**
	 * This constructor initializes the InvalidFieldException object with a specific error message.
	 *
	 * @param message The specific error message for this exception.
	 */
	public InvalidFieldException(String message) {
		super(message);
	}
}
