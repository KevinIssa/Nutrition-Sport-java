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
import ulb.views.ViewController;

public class ViewLoader {

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

	private ViewController load(String resourcePath, Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			return loader.getController();
		} catch (IOException e) {
			throw new RuntimeException("Failed to load resource: " + resourcePath, e);
		}
	}

	private void loadWithListener(String resourcePath, Stage stage, AppController controller) {
		this.load(resourcePath, stage).setListener(controller);
	}

	public void loadView(Stage stage, AppController controller) {
		Integer pathIndex = this.getPathIndex(controller);
		if (pathIndex != null) {
			this.loadWithListener(PATHS[pathIndex], stage, controller);
		}
	}

	private Integer getPathIndex(AppController controller) {
		if (controller instanceof MenuController) {
			return 4;
		} else if (controller instanceof ProfileCreateController) {
			return 6;
		} else if (controller instanceof ProfileController) {
			return 5;
		} else if (controller instanceof ProfileDeleteController) {
			return 7;
		} else if (controller instanceof ActivityCreateController) {
			return 0;
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
}
