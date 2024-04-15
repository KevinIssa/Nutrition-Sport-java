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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.views.ViewController;

/**
 * The ViewLoader class is responsible for loading the views.
 */
public class ViewLoader {
	private static final Logger logger = LoggerFactory.getLogger(ViewLoader.class);

	/**
	 * An array of paths to the FXML files for the views.
	 */
	private static final String[] PATHS = {
		"/ulb/views/ActivityCreate.fxml", // 0
		"/ulb/views/ActivityHistory.fxml", // 1
		"/ulb/views/AddMeal.fxml", // 2
		"/ulb/views/MealHistory.fxml", // 3
		"/ulb/views/Menu.fxml", // 4
		"/ulb/views/Profile.fxml", // 5
		"/ulb/views/ProfileCreate.fxml", // 6
		"/ulb/views/ProfileDeleteConfirm.fxml" // 7
	};

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
	 * This method is used to load a view associated with a given controller.
	 * It first retrieves the index of the path to the FXML file for the view associated with the controller by calling the getPathIndex method.
	 * If the index is not null, it calls the loadWithListener method with the path at the retrieved index, the provided stage, and the provided controller.
	 * This effectively loads the view and sets the controller as the listener for the loaded view.
	 *
	 * @param stage The stage to set the scene of.
	 * @param controller The controller to set as the listener for the loaded view and to get the path index for.
	 */
	public void loadView(Stage stage, AppController controller) {
		Integer pathIndex = this.getPathIndex(controller);
		if (pathIndex != null) {
			this.loadWithListener(PATHS[pathIndex], stage, controller);
		}
	}

	/**
	 * Returns the index in the PATHS array corresponding to the given controller.
	 * @param controller The controller to get the path index for.
	 * @return The index in the PATHS array corresponding to the given controller, or null if no such index exists.
	 */
	private Integer getPathIndex(AppController controller) {
		if (controller instanceof MenuController) {
			return 4;
		} else if (controller instanceof ProfileCreateController) {
			return 6;
		} else if (controller instanceof ProfileController) {
			return 5;
		} else if (controller instanceof ProfileDeleteController) {
			return 7;
		} else if (controller instanceof ActivityHistoryController) {
			return 1;
		} else if (controller instanceof MealHistoryController) {
			return 3;
		}
		return null;
	}

	public ViewController loadAddMeal(Stage stage) {
		return this.load(PATHS[2], stage);
	}

	public ViewController loadActivityCreate(Stage stage) {
		return this.load(PATHS[0], stage);
	}
}
