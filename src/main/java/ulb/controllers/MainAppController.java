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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ulb.models.Activity;
import ulb.models.Profile;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sex;
import ulb.models.enums.Sport;
import ulb.views.*;

public class MainAppController extends AppController implements MenuViewController.Listener {

	private final Stage primaryStage;

	public MainAppController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void loadView(String resourcePath, Supplier<Object> listenerSupplier) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
			Parent root = loader.load();
			ViewController viewController = loader.getController();
			viewController.setListener(listenerSupplier.get());
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadWelcomeView() {
		if (Profile.isCreated()) {
			loadMenuView();
		} else {
			loadCreateProfileView();
		}
	}

	@Override
	public void loadMenuView() {
		loadView("/ulb/views/Menu.fxml", () -> this);
	}

	@Override
	public void loadCreateProfileView() {
		loadView(
				"/ulb/views/ProfileCreate.fxml",
				() ->
						new ProfileCreateViewController.Listener() {
							@Override
							public void saveProfile(
									String firstName,
									String lastName,
									String sex,
									java.time.LocalDate birthDate,
									float height,
									float weight) {
								Profile profile =
										new Profile(
												firstName,
												lastName,
												Sex.fromString(sex),
												new ulb.models.Weight(weight),
												new ulb.models.Height(height),
												birthDate);
								profile.save();
							}

							@Override
							public void returnHome() {
								loadWelcomeView();
							}
						});
	}

	@Override
	public void loadConsultProfileView() {
		loadView(
				"/ulb/views/ProfileConsult.fxml",
				() ->
						new ProfileConsultViewController.Listener() {
							@Override
							public String getFirstName() {
								return Profile.load().getFirstName();
							}

							@Override
							public String getLastName() {
								return Profile.load().getLastName();
							}

							@Override
							public String getSex() {
								return Profile.load().getSex().toString();
							}

							@Override
							public java.time.LocalDate getBirthDate() {
								return Profile.load().getBirthDate();
							}

							@Override
							public float getHeight() {
								return Profile.load().getHeight();
							}

							@Override
							public float getWeight() {
								return Profile.load().getWeight();
							}

							@Override
							public void returnHome() {
								loadMenuView();
							}
						});
	}

	@Override
	public void loadModifyProfileView() {
		loadView(
				"/ulb/views/ProfileModify.fxml",
				() ->
						new ProfileModifyViewController.Listener() {
							Profile profile = Profile.load();

							@Override
							public void saveProfile(
									String firstName,
									String lastName,
									String sex,
									java.time.LocalDate birthDate,
									float height,
									float weight) {
								Profile profile =
										new Profile(
												firstName,
												lastName,
												Sex.fromString(sex),
												new ulb.models.Weight(weight),
												new ulb.models.Height(height),
												birthDate);
								profile.save();
							}

							@Override
							public void returnHome() {
								loadWelcomeView();
							}

							@Override
							public String getFirstName() {
								return profile.getFirstName();
							}

							@Override
							public String getLastName() {
								return profile.getLastName();
							}

							@Override
							public String getSex() {
								return profile.getSex().toString();
							}

							@Override
							public java.time.LocalDate getBirthDate() {
								return profile.getBirthDate();
							}

							@Override
							public float getHeight() {
								return profile.getHeight();
							}

							@Override
							public float getWeight() {
								return profile.getWeight();
							}
						});
	}

	@Override
	public void loadDeleteProfileView() {
		loadView(
				"/ulb/views/ProfileDeleteConfirm.fxml",
				() ->
						new ProfileDeleteConfirmViewController.Listener() {
							@Override
							public void deleteProfile() {
								Profile profile = Profile.load();
								profile.delete();
								Activity.clearAllActivities();
								loadCreateProfileView();
							}

							@Override
							public void returnHome() {
								loadMenuView();
							}
						});
	}

	@Override
	public void loadCreateActivityView() {
		loadView(
				"/ulb/views/ActivityCreate.fxml",
				() ->
						new ActivityCreateViewController.Listener() {
							@Override
							public void saveActivity(
									Sport selectedSport,
									String selectedIntensity,
									float selectedDuration) {
								Activity activity =
										new Activity(
												selectedSport,
												Intensity.fromString(selectedIntensity),
												Duration.ofMinutes((long) selectedDuration),
												LocalDateTime.now());
								activity.save();
								showAlert(activity.getCaloriesBurned(Profile.load().getWeight()));
							}

							@Override
							public void returnHome() {
								loadMenuView();
							}
						});
	}

	@Override
	public void loadActivityHistoryView() {
		loadView(
				"/ulb/views/ActivityHistory.fxml",
				() ->
						new ActivityHistoryViewController.Listener() {
							@Override
							public void returnHome() {
								loadWelcomeView();
							}

							@Override
							public Activity loadActivity(String filename) {
								return Activity.load(filename);
							}
						});
	}

	// Method to show an alert with the calculated calories
	public void showAlert(double calories) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Calcul du nombre de calories");
		alert.setHeaderText(null);
		String text = "Vous avez dépensé " + calories + " calories durant cette activité";
		alert.setContentText(text);
		alert.showAndWait();
	}
}
