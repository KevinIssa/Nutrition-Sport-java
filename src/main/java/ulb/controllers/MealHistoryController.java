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

	public interface Listener {
		void returnHome();
	}
}
