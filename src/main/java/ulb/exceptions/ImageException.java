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
 * The ImageException class is an abstract class that extends the Exception class.
 * This class serves as the base class for all custom exceptions related to image processing in the application.
 * Any class that extends ImageException must provide an implementation for the constructor that accepts a message parameter.
 */
public abstract class ImageException extends Exception {

	/**
	 * This constructor initializes the ImageException object with a specific error message.
	 *
	 * @param message The specific error message for this exception.
	 */
	public ImageException(String message) {
		super(message);
	}
}
