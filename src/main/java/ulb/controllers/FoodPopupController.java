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

import javafx.stage.Stage;
import ulb.services.ConsumableService;
import ulb.views.FoodPopupViewController;

public class FoodPopupController extends AppController implements FoodPopupViewController.Listener {
	private final ConsumableService consumableService;
	private final Listener listener;
	private Stage stage;

	public FoodPopupController(
			ConsumableService consumableService, FoodPopupController.Listener listener) {
		this.consumableService = consumableService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		this.loadView("/ulb/widgets/Food_popup.fxml", stage);
		stage.setTitle("Quantité de nourriture");
		this.stage = stage;
		this.viewController.setListener(this);
	}

	public void setFood(String food) {
		((FoodPopupViewController) this.viewController).setFood(food);
	}

	public void setFoodServing(String serving) {
		((FoodPopupViewController) this.viewController).setFoodServing(serving);
	}

	public void setFoodUnit(String unit) {
		((FoodPopupViewController) this.viewController).setFoodUnit(unit);
	}

	@Override
	public double getServingQuantityValue(String food) {
		return this.consumableService.loadConsumable(food).extractServingQuantityValue();
	}

	@Override
	public void onBack() {
		this.stage.hide();
	}

	@Override
	public void onEntry(double value) {
		this.stage.hide();
		this.listener.onEntry(((FoodPopupViewController) this.viewController).getFood(), value);
	}

	public interface Listener {
		void onEntry(String food, double value);
	}
}
