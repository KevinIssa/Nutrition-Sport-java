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

public abstract class AppController {
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	protected ViewController viewController;

	public abstract void show(Stage stage);

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

	protected void showLoadingAlert(String filePath) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Erreur lors du chargement de la fenetre !");
		alert.setHeaderText("Le fichier " + filePath + " n'a pas pu etre chargé.");
		alert.setContentText("Veuillez contacter l'équipe de développement.");
		alert.showAndWait();
	}
}
