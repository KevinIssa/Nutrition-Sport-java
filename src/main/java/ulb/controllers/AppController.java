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

/**
 * The AppController interface is a part of the application's controller layer.
 * It is an empty interface used as a marker for all controllers in the application.
 * This interface can be implemented by any class that is intended to act as a controller.
 * The purpose of this interface is to ensure a consistent structure across all controller classes.
 * <p>
 * This interface does not define any methods. Any class implementing this interface should define its own methods based on its specific needs.
 * <p>
 * This interface is a part of the application's architecture and is crucial for maintaining a clean and organized codebase.
 */
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
			//			logger.error("Failed to load view resources", e);
			System.exit(1);
		}
	}
}
