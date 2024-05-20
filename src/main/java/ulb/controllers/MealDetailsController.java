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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.RecipeDTO;
import ulb.services.ConsumableService;
import ulb.views.MealDetailsViewController;

/**
 * This class is a controller for the MealDetails view.
 * It extends the AppController class and implements the Listener interface from the MealDetailsViewController.
 */
public class MealDetailsController extends AppController
		implements MealDetailsViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MealDetailsController.class);
	private final ConsumableService consumableService;
	private final MealDetailsController.Listener listener;

	/**
	 * Constructor for the MealDetailsController class.
	 * It initializes the ConsumableService and Listener instances.
	 *
	 * @param consumableService The ConsumableService instance to be used for consumable related operations.
	 * @param listener The Listener instance to be used for handling events triggered in the MealDetails view.
	 */
	public MealDetailsController(
			ConsumableService consumableService, MealDetailsController.Listener listener) {
		logger.info("Initializing MealDetailsController");
		this.consumableService = consumableService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing MealDetailsView");
		this.loadView("/ulb/views/MealDetails.fxml", stage);
		this.viewController.setListener(this);
	}

	public void setMeal(RecipeDTO recipeDTO) {
		((MealDetailsViewController) this.viewController).setMeal(recipeDTO);
	}

	@Override
	public double getCaloriesConsumed(RecipeDTO recipeDTO) {
		return this.consumableService.getCaloriesConsumed(recipeDTO);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * This is an interface for a listener that reacts to a return home event.
	 * The event is triggered when the user decides to return to the home screen.
	 */
	public interface Listener {

		/**
		 * This method is called when a return home event occurs.
		 */
		void returnHome();
	}
}
