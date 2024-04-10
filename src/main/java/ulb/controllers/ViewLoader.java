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
import java.util.function.Supplier;
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

	private void loadWithListener(
			String resourcePath, Stage stage, Supplier<Object> listenerSupplier) {
		this.load(resourcePath, stage).setListener(listenerSupplier.get());
	}

	public void load(int pathIndex, Stage stage, Supplier<Object> listenerSupplier) {
		this.loadWithListener(PATHS[pathIndex], stage, listenerSupplier);
	}

	public ViewController load(int pathIndex, Stage stage) {
		return this.load(PATHS[pathIndex], stage);
	}

	public void loadMenu(Stage stage, Supplier<Object> listenerSupplier) {
		this.load(4, stage, listenerSupplier);
	}

	public void loadCreateProfile(Stage stage, Supplier<Object> listenerSupplier) {
		this.load(6, stage, listenerSupplier);
	}

	public void loadProfile(Stage stage, Supplier<Object> listenerSupplier) {
		this.load(5, stage, listenerSupplier);
	}

	public void loadDeleteProfile(Stage stage, Supplier<Object> listenerSupplier) {
		this.load(7, stage, listenerSupplier);
	}

	public ViewController loadCreateActivity(Stage stage) {
		return this.load(0, stage);
	}

	public void loadActivityHistory(Stage stage, Supplier<Object> listenerSupplier) {
		this.load(1, stage, listenerSupplier);
	}

	public void loadMealHistory(Stage stage, Supplier<Object> listenerSupplier) {
		this.load(3, stage, listenerSupplier);
	}

	public ViewController loadAddMeal(Stage stage) {
		return this.load(2, stage);
	}
}
