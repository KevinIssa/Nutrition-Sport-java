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

import java.io.File;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;
import ulb.views.MealHistoryViewController;

/**
 * This class is the controller for the meal history screen of the application.
 * It is responsible for handling the logic of the meal history screen, such as loading a meal from a file.
 * It also listens to events from the MealHistoryViewController and notifies the listener when the user wants to return to the home screen.
 */
public class MealHistoryController extends AppController
		implements MealHistoryViewController.Listener {

	private final MealHistoryController.Listener listener;

	public MealHistoryController(MealHistoryController.Listener listener) {
		this.listener = listener;
	}

	private static final String FOLDERNAME = "consumed_meals";

	@Override
	public void show(Stage stage) {
		this.loadView("/ulb/views/MealHistory.fxml", stage);
		this.viewController.setListener(this);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void deleteFood(HBox foodBox) {
		String date_in_string = ((Label) foodBox.getChildren().get(4)).getText();
		File directory = new File(FOLDERNAME); // Specify the directory path
		File[] files = directory.listFiles();
		boolean isDeleted = false;
		if (files == null) {
			return;
		}
		for (File file : files) {
			ConsumedMeal meal = this.loadMeal(file.getPath());
			if (meal.changeDateFormat(meal.getDate()).equals(date_in_string)) {
				for (ConsumedFood food : meal.getConsumedFoods()) {
					if (isSameFood(food, foodBox)) {
						meal.getConsumedFoods().remove(food);
						isDeleted = true;
						break;
					}
				}
			}
			if (meal.getConsumedFoods().isEmpty() && isDeleted) {
				file.delete();
				break;
			} else if (isDeleted) {
				file.delete();
				meal.save();
				break;
			}
		}
	}

	private boolean isSameFood(ConsumedFood food, HBox foodBox) {
		return food.getName().equals(((Label) foodBox.getChildren().get(0)).getText())
				&& food.getQuantity()
						== Integer.parseInt(
								((Label) foodBox.getChildren().get(2)).getText().split(" ")[0])
				&& food.getCalories()
						== Integer.parseInt(
								((Label) foodBox.getChildren().get(6)).getText().split(" ")[0])
				&& food.getType()
						.equals(((Label) foodBox.getChildren().get(2)).getText().split(" ")[1]);
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
