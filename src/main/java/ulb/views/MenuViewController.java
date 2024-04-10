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
	@FXML ImageView profileimage;
	private Listener listener;

	@FXML private Button addButton;
	@FXML private Button addActivityButton;
	@FXML private Button addFoodButton;

	public void handleButtonPress() {
		addActivityButton.setVisible(!addActivityButton.isVisible());
		addFoodButton.setVisible(!addFoodButton.isVisible());
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void openProfile() {
		listener.loadOpenProfileView();
	}

	public void createActivity() {
		listener.loadCreateActivityView();
	}

	public void activityHistory() {
		listener.loadActivityHistoryView();
	}

	public void mealHistory() {
		listener.loadMealHistoryView();
	}

	public void setListener(Object listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.listener = (Listener) listener;
		setdefault();
	}

	private void setdefault() {
		Image image = listener.getProfileImage(30, 30);
		if (image != null) {
			profileimage.setImage(image);
		}
	}

	public void foodSearchPage() {
		listener.loadFoodSearchPage();
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

		default Image getProfileImage(double width, double height) {
			try {
				File file = new File("profile.png");
				if (!file.exists()) {
					return null;
				}
				URL path = file.toURL();
				return new Image(path.toString(), width, height, true, true);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
