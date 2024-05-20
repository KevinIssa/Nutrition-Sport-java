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
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.views.ViewController;

/**
 * This is an abstract class that serves as a base for all application controllers.
 * It provides common functionality for loading views and handling errors.
 */
public abstract class AppController {
	// Logger instance for logging events, info, errors etc.
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	// ViewController instance that will be used by subclasses to control the view.
	protected ViewController viewController;

	/**
	 * Abstract method that needs to be implemented by subclasses.
	 * It should contain the logic for showing the view on the provided stage.
	 *
	 * @param stage The stage on which the view should be shown.
	 */
	public abstract void show(Stage stage);

	/**
	 * Loads the view from the provided resource path and sets it on the provided stage.
	 * If an error occurs during loading, it shows an alert with the error message.
	 *
	 * @param resourcePath The path to the FXML file of the view.
	 * @param stage The stage on which the view should be shown.
	 */
	protected void loadView(String resourcePath, Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
			Parent root = loader.load();
			this.viewController = loader.getController();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			logger.error("Failed to load view resources : {}", e.getMessage());
			this.showLoadingAlert(resourcePath);
		}
	}

	/**
	 * Shows an alert with an error message indicating that the file at the provided path could not be loaded.
	 *
	 * @param filePath The path to the file that could not be loaded.
	 */
	protected void showLoadingAlert(String filePath) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Erreur lors du chargement de la fenetre !");
		alert.setHeaderText("Le fichier " + filePath + " n'a pas pu etre chargé.");
		alert.setContentText("Veuillez contacter l'équipe de développement.");
		alert.showAndWait();
	}
}
