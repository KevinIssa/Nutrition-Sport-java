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

public abstract class AppController {

	protected ViewController viewController;

	public abstract void show(Stage stage);

	protected void loadView(String resourcePath, Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
			Parent root = loader.load();
			this.viewController = loader.getController();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			// logger.error("Failed to load view resources", e);
			System.exit(1);
		}
	}
}
