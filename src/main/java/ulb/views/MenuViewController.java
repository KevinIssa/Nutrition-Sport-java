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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuViewController implements ViewController {
	@FXML ImageView profileImage;
	@FXML private Button addActivityButton;
	@FXML private Button addFoodButton;

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
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
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
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
		this.initialize(null, null);
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
	}
}
