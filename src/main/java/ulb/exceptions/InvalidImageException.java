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
 * The InvalidImageException class is a custom exception class that extends the ImageException class.
 * This exception is thrown when an image does not meet the expected requirements or constraints.
 */
public class InvalidImageException extends ImageException {

	/**
	 * This constructor initializes the InvalidImageException object with a specific error message.
	 *
	 * @param message The specific error message for this exception.
	 */
	public InvalidImageException(String message) {
		super(message);
	}
}
