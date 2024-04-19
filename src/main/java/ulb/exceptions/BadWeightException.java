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
 * This class represents a custom exception for invalid weights.
 * It extends the ValueObjectException class and can be used to throw exceptions with a custom message.
 */
public class BadWeightException extends ValueObjectException {

	/**
	 * Constructor for BadWeightException with a specified message.
	 * @param message The message for the exception.
	 */
	public BadWeightException(String message) {
		super(message);
	}
}
