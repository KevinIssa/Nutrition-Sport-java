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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.ConsumedMealDTO;
import ulb.services.ConsumeMealService;
import ulb.views.MealHistoryViewController;

/**
 * This class is the controller for the meal history screen of the application.
 * It is responsible for handling the logic of the meal history screen, such as loading a meal from a file.
 * It also listens to events from the MealHistoryViewController and notifies the listener when the user wants to return to the home screen.
 */
public class MealHistoryController extends AppController
		implements MealHistoryViewController.Listener {

	private final ConsumeMealService consumeMealService;
	private final MealHistoryController.Listener listener;

	public MealHistoryController(
			ConsumeMealService consumeMealService, MealHistoryController.Listener listener) {
		this.consumeMealService = consumeMealService;
		this.listener = listener;
	}

	@Override
	public void show(Stage stage) {
		this.loadView("/ulb/views/MealHistory.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public List<ConsumedMealDTO> getAllMeals() {
		return this.consumeMealService.getConsumedMeals();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void addMeal() {
		this.listener.addMeal();
	}

	@Override
	public void deleteFood(HBox foodBox) {
		String dateString = ((Label) foodBox.getChildren().get(4)).getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");
		// Parse the string into a LocalDateTime object
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		this.consumeMealService.deleteConsumedFood(to_consumedFoodDTO(foodBox), dateTime);
	}

	private ConsumedFoodDTO to_consumedFoodDTO(HBox foodBox) {
		String name = ((Label) foodBox.getChildren().get(0)).getText();
		int quantity =
				Integer.parseInt(((Label) foodBox.getChildren().get(2)).getText().split(" ")[0]);
		int calories =
				Integer.parseInt(((Label) foodBox.getChildren().get(6)).getText().split(" ")[0]);
		String unit = ((Label) foodBox.getChildren().get(2)).getText().split(" ")[1];
		String dateString = ((Label) foodBox.getChildren().get(4)).getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");
		// Parse the string into a LocalDateTime object
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		return new ConsumedFoodDTO(name, quantity, calories, unit);
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

		void addMeal();
	}
}
