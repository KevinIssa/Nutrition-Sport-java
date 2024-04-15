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
package ulb.controllers;

import ulb.models.ConsumedMeal;
import ulb.views.MealHistoryViewController;

/**
 * This class is the controller for the meal history screen of the application.
 * It is responsible for handling the logic of the meal history screen, such as loading a meal from a file.
 * It also listens to events from the MealHistoryViewController and notifies the listener when the user wants to return to the home screen.
 */
public class MealHistoryController implements AppController, MealHistoryViewController.Listener {

	private final MealHistoryController.Listener listener;

	public MealHistoryController(MealHistoryController.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public ConsumedMeal loadMeal(String filename) {
		return ConsumedMeal.load(filename);
	}

	/**
	 * This is an interface for the Listener within the MealHistoryController class.
	 * It is used to define the contract for the Listener, which is expected to be implemented by any class that wants to listen to events from the MealHistoryController.
	 * <p>
	 * Currently, it has a single method, returnHome, which is expected to be called when the user wants to return to the home screen of the application.
	 */
	public interface Listener {

		/**
		 * This method is called when the user wants to return to the home screen of the application.
		 * The implementing class should define the behavior that occurs when this event happens.
		 */
		void returnHome();
	}
}
