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
package ulb.views;

import javafx.fxml.Initializable;

/**
 * A common interface for view controllers in the application.
 * Provides a method to set a listener object for communication with the controller.
 */
public interface ViewController extends Initializable {

	/**
	 * Sets the listener object for communication with the controller.
	 * This method should be implemented by all view controllers.
	 *
	 * @param listener The listener object to be set for communication with the controller.
	 */
	void setListener(Object listener);
}
