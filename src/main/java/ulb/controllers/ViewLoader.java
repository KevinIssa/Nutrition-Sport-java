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

import java.io.IOException;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.views.ViewController;

/**
 * This class is used to load views from FXML files and set them as the scene of a stage.
 */
public class ViewLoader {
	private static final Logger logger = LoggerFactory.getLogger(ViewLoader.class);

	/**
	 * A map that associates each controller class with the path to its corresponding FXML file.
	 * This map is used to load the appropriate view for each controller.
	 * The keys are Class objects representing the controller classes, and the values are strings representing the paths to the FXML files.
	 */
	private static final Map<Class<? extends AppController>, String> controllerToFxmlPath =
			Map.of(
					MenuController.class, "/ulb/views/Menu.fxml",
					ProfileCreateController.class, "/ulb/views/ProfileCreate.fxml",
					ProfileController.class, "/ulb/views/Profile.fxml",
					ProfileDeleteController.class, "/ulb/views/ProfileDeleteConfirm.fxml",
					ActivityHistoryController.class, "/ulb/views/ActivityHistory.fxml",
					MealHistoryController.class, "/ulb/views/MealHistory.fxml");

	/**
	 * Loads a view from a given resource path and sets it as the scene of the given stage.
	 * @param resourcePath The path to the FXML file for the view.
	 * @param stage The stage to set the scene of.
	 * @return The controller for the loaded view.
	 */
	private ViewController load(String resourcePath, Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			return loader.getController();
		} catch (IOException e) {
			logger.error("Failed to load view resources", e);
			System.exit(1);
			return null; // Unreachable
		}
	}

	/**
	 * This method is used to load a view with a specified listener.
	 * It first calls the load method with the provided resource path and stage.
	 * The load method returns a ViewController instance, on which the setListener method is called with the provided controller.
	 * This effectively sets the controller as the listener for the loaded view.
	 *
	 * @param resourcePath The path to the FXML file for the view.
	 * @param stage The stage to set the scene of.
	 * @param controller The controller to set as the listener for the loaded view.
	 */
	private void loadWithListener(String resourcePath, Stage stage, AppController controller) {
		this.load(resourcePath, stage).setListener(controller);
	}

	/**
	 * This method is used to load a view associated with a given controller and set it as the scene of a given stage.
	 * It first retrieves the path to the FXML file for the view associated with the controller's class from the controllerToFxmlPath map.
	 * If such a path exists, it calls the loadWithListener method with the path, the stage, and the controller.
	 * This effectively loads the view from the FXML file, sets it as the scene of the stage, and sets the controller as the listener for the view.
	 *
	 * @param stage The stage to set the scene of.
	 * @param controller The controller associated with the view to load.
	 */
	public void loadView(Stage stage, AppController controller) {
		String fxmlPath = controllerToFxmlPath.get(controller.getClass());
		if (fxmlPath != null) {
			this.loadWithListener(fxmlPath, stage, controller);
		}
	}

	/**
	 * This method is used to load the 'AddMeal' view.
	 * It calls the load method with the path to the 'AddMeal' view FXML file and the provided stage.
	 * The load method returns a ViewController instance for the loaded view.
	 *
	 * @param stage The stage to set the scene of.
	 * @return The controller for the loaded 'AddMeal' view.
	 */
	public ViewController loadAddMeal(Stage stage) {
		return this.load("/ulb/views/AddMeal.fxml", stage);
	}

	/**
	 * This method is used to load the 'ActivityCreate' view.
	 * It calls the load method with the path to the 'ActivityCreate' view FXML file and the provided stage.
	 * The load method returns a ViewController instance for the loaded view.
	 *
	 * @param stage The stage to set the scene of.
	 * @return The controller for the loaded 'ActivityCreate' view.
	 */
	public ViewController loadActivityCreate(Stage stage) {
		return this.load("/ulb/views/ActivityCreate.fxml", stage);
	}
}
