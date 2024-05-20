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
 * This is a class named SavingException that extends the Exception class.
 * This class is used to handle exceptions related to saving operations in the application.
 */
public class SavingException extends Exception {

	/**
	 * This is a constructor for the SavingException class.
	 * It takes one parameter: message.
	 *
	 * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 */
	public SavingException(String message) {
		super(message);
	}

	/**
	 * This is a constructor for the SavingException class.
	 * It takes two parameters: message and cause.
	 *
	 * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
	 *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public SavingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * This is a constructor for the SavingException class.
	 * It takes one parameter: cause.
	 *
	 * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
	 *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public SavingException(Throwable cause) {
		super(cause);
	}
}
