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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.DateCalorieDTO;

public class MenuViewController implements ViewController, GraphComponentViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MenuViewController.class);
	@FXML ImageView profileImage;
	@FXML private Button addActivityButton;
	@FXML private Button addFoodButton;
	@FXML private GraphComponentViewController graphComponentController;

	private MenuViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if (this.listener == null) {
			return;
		}
		try {
			Image image =
					new Image(
							String.valueOf(new File(this.listener.getProfileImagePath()).toURL()),
							30,
							30,
							false,
							false);
			this.profileImage.setImage(image);
			graphComponentController.setListener(this);
		} catch (MalformedURLException e) {
			logger.error("Error loading profile image due to malformed URL {}", e.getMessage());
			System.exit(1);
		}
	}

	public void showAddButtons() {
		this.addActivityButton.setVisible(!this.addActivityButton.isVisible());
		this.addFoodButton.setVisible(!this.addFoodButton.isVisible());
	}

	public void openProfile() {
		listener.loadOpenProfileView();
	}

	public void createActivity() {
		listener.loadCreateActivityView();
	}

	public void createConsumedFood() {
		listener.loadFoodSearchPage();
	}

	public void activityHistory() {
		listener.loadActivityHistoryView();
	}

	public void consumedFoodHistory() {
		listener.loadMealHistoryView();
	}

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

	public interface Listener {
		void loadCreateActivityView();

		void loadOpenProfileView();

		void loadCreateProfileView();

		void loadMenuView();

		void loadWelcomeView();

		void loadActivityHistoryView();

		void loadMealHistoryView();

		void loadFoodSearchPage();

		String getProfileImagePath();

		List<DateCalorieDTO> getGraphData();
	}
}
