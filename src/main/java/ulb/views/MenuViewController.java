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
package ulb.views;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.DateCalorieDTO;

public class MenuViewController implements ViewController, GraphComponentViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MenuViewController.class);
	@FXML private ImageView profileImage;
	@FXML private Label weightLabel;
	@FXML private Label calorieLabel;
	@FXML private Button addActivityButton;
	@FXML private Button addFoodButton;
	@FXML private GraphComponentViewController graphComponentController;

	private MenuViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (this.listener == null) {
			return;
		}
		Image image =
				new Image(
						String.valueOf(new File(this.listener.getProfileImagePath()).toURI()),
						30,
						30,
						false,
						false);
		this.profileImage.setImage(image);
		graphComponentController.setListener(this);
		this.weightLabel.setText(this.listener.getProfileWeight() + " kg");
		this.calorieLabel.setText(
				BigDecimal.valueOf(this.graphComponentController.getLastCalorieDifference())
								.setScale(2, RoundingMode.DOWN)
								.doubleValue()
						+ " kcal");
	}

	/**
	 * Toggles the visibility of the "Add Activity" and "Add Food" buttons.
	 * If the buttons are currently visible, they will be hidden, and vice versa.
	 */
	public void showAddButtons() {
		this.addActivityButton.setVisible(!this.addActivityButton.isVisible());
		this.addFoodButton.setVisible(!this.addFoodButton.isVisible());
	}

	/**
	 * Triggers the loading of the Open Profile view.
	 * This method should be called when the user wants to open their profile.
	 */
	public void openProfile() {
		listener.loadOpenProfileView();
	}

	/**
	 * Triggers the loading of the Create Activity view.
	 * This method should be called when the user wants to create a new activity.
	 */
	public void createActivity() {
		listener.loadCreateActivityView();
	}

	/**
	 * Triggers the loading of the Food Search page.
	 * This method should be called when the user wants to create a new consumed food entry.
	 */
	public void createConsumedFood() {
		listener.loadFoodSearchPage();
	}

	/**
	 * Triggers the loading of the Activity History view.
	 * This method should be called when the user wants to view their activity history.
	 */
	public void activityHistory() {
		listener.loadActivityHistoryView();
	}

	/**
	 * Triggers the loading of the Consumed Food History view.
	 * This method should be called when the user wants to view their consumed food history.
	 */
	public void consumedFoodHistory() {
		listener.loadMealHistoryView();
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (Listener) listener;
		this.initialize(null, null);
	}

	@Override
	public List<DateCalorieDTO> getGraphData() {
		return listener.getGraphData();
	}

	/**
	 * Listener interface for the MenuViewController.
	 * This interface should be implemented by any class that needs to respond to actions from the MenuViewController.
	 */
	public interface Listener {
		/**
		 * Triggers the loading of the Create Activity view.
		 * This method should be called when the user wants to create a new activity.
		 */
		void loadCreateActivityView();

		/**
		 * Triggers the loading of the Open Profile view.
		 * This method should be called when the user wants to open their profile.
		 */
		void loadOpenProfileView();

		/**
		 * Triggers the loading of the Create Profile view.
		 * This method should be called when the user wants to create a new profile.
		 */
		void loadCreateProfileView();

		/**
		 * Triggers the loading of the Menu view.
		 * This method should be called when the user wants to return to the menu.
		 */
		void loadMenuView();

		/**
		 * Triggers the loading of the Welcome view.
		 * This method should be called when the user wants to return to the welcome screen.
		 */
		void loadWelcomeView();

		/**
		 * Triggers the loading of the Activity History view.
		 * This method should be called when the user wants to view their activity history.
		 */
		void loadActivityHistoryView();

		/**
		 * Triggers the loading of the Consumed Food History view.
		 * This method should be called when the user wants to view their consumed food history.
		 */
		void loadMealHistoryView();

		/**
		 * Triggers the loading of the Meal Recipe view.
		 * This method should be called when the user wants to view a meal recipe.
		 */
		void loadMealRecipe();

		/**
		 * Triggers the loading of the Food Search page.
		 * This method should be called when the user wants to search for a food item.
		 */
		void loadFoodSearchPage();

		/**
		 * Retrieves the path of the profile image.
		 * This method should be implemented to return the path of the profile image.
		 *
		 * @return The path of the profile image.
		 */
		String getProfileImagePath();

		/**
		 * Retrieves the data for the graph.
		 * This method should be implemented to return a list of DateCalorieDTO objects for the graph.
		 *
		 * @return A list of DateCalorieDTO objects for the graph.
		 */
		List<DateCalorieDTO> getGraphData();

		/**
		 * Retrieves the weight of the profile.
		 * This method should be implemented to return the weight of the profile.
		 *
		 * @return The weight of the profile.
		 */
		String getProfileWeight();
	}
}
