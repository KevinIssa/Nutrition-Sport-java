/* (C)2024 */
package ulb.controllers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ulb.models.Activity;
import ulb.models.Height;
import ulb.models.Profile;
import ulb.models.Weight;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sex;
import ulb.models.enums.Sport;
import ulb.views.*;

public class MainAppController extends AppController implements MenuViewController.Listener {

	Stage primaryStage;

	public MainAppController(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ulb/views/Menu.fxml"));
			Parent root = loader.load();
			ViewController viewController = loader.getController();
			viewController.setListener(this);
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadCreateProfileView() {
		try {
			FXMLLoader loader =
					new FXMLLoader(getClass().getResource("/ulb/views/ProfileCreate.fxml"));
			Parent root = loader.load();
			ProfileCreateViewController viewController = loader.getController();
			viewController.setListener(
					new ProfileCreateViewController.Listener() {

						@Override
						public void saveProfile(
								String firstName,
								String lastName,
								String sex,
								LocalDate birthDate,
								float height,
								float weight) {
							Profile profile =
									new Profile(
											firstName,
											lastName,
											Sex.fromString(sex),
											new Weight(weight),
											new Height(height),
											birthDate);
							profile.save();
						}

						@Override
						public void returnHome() {
							loadWelcomeView();
						}
					});
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadConsultProfileView() {
		try {
			FXMLLoader loader =
					new FXMLLoader(getClass().getResource("/ulb/views/ProfileConsult.fxml"));
			Parent root = loader.load();
			ProfileConsultViewController viewController = loader.getController();
			Profile profile = Profile.load();
			viewController.setListener(
					new ProfileConsultViewController.Listener() {

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
						public LocalDate getBirthDate() {
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

						@Override
						public void returnHome() {
							loadMenuView();
						}
					});
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadModifyProfileView() {
		try {
			FXMLLoader loader =
					new FXMLLoader(getClass().getResource("/ulb/views/ProfileModify.fxml"));
			Parent root = loader.load();
			ViewController viewController = loader.getController();
			Profile profile = Profile.load();
			viewController.setListener(
					new ProfileModifyViewController.Listener() {
						@Override
						public void saveProfile(
								String firstName,
								String lastName,
								String sex,
								LocalDate birthDate,
								float height,
								float weight) {
							Profile profile =
									new Profile(
											firstName,
											lastName,
											Sex.fromString(sex),
											new Weight(weight),
											new Height(height),
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
						public LocalDate getBirthDate() {
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
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadDeleteProfileView() {
		try {
			FXMLLoader loader =
					new FXMLLoader(getClass().getResource("/ulb/views/ProfileDeleteConfirm.fxml"));
			Parent root = loader.load();
			ProfileDeleteConfirmViewController viewController = loader.getController();
			Profile profile = Profile.load();
			viewController.setListener(
					new ProfileDeleteConfirmViewController.Listener() {
						@Override
						public void deleteProfile() {
							profile.delete();
							Activity.clearAllActivities();
							loadCreateProfileView();
						}



						@Override
						public void returnHome() {
							loadMenuView();
						}
					});
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadCreateActivityView() {
		try {
			FXMLLoader loader =
					new FXMLLoader(getClass().getResource("/ulb/views/ActivityCreate.fxml"));
			Parent root = loader.load();
			ActivityCreateViewController viewController = loader.getController();
			viewController.setListener(
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
							viewController.showAlert(
									activity.getCaloriesBurned(Profile.load().getWeight()));
						}

						@Override
						public void returnHome() {
							loadMenuView();
						}
					});
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadActivityHistoryView() {
		try {
			FXMLLoader loader =
					new FXMLLoader(getClass().getResource("/ulb/views/ActivityHistory.fxml"));
			Parent root = loader.load();
			ViewController viewController = loader.getController();
			Profile profile = Profile.load();
			viewController.setListener(
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
			primaryStage.setScene(new Scene(root, 300, 200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
