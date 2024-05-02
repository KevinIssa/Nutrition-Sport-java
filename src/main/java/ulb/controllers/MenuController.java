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

import java.util.List;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ActivityDTO;
import ulb.dtos.DateCalorieDTO;
import ulb.models.*;
import ulb.repositories.ActivityRepository;
import ulb.repositories.ConsumeMealRepository;
import ulb.repositories.JSONActivityRepository;
import ulb.repositories.JSONConsumeMealRepository;
import ulb.services.ActivityService;
import ulb.services.CaloriesTrackingService;
import ulb.services.ConsumeMealService;
import ulb.services.ProfileService;
import ulb.views.*;

/**
 * The MenuController class is responsible for managing the application's menu.
 * It implements the AppController interface and the MenuViewController.Listener interface.
 * This class is responsible for loading different views of the application based on user interaction.
 * It uses a ViewLoader instance to load the views and manages the primary stage of the application.
 */
public class MenuController extends AppController implements MenuViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	private Stage primaryStage;
	private ProfileService profileService;
	private CaloriesTrackingService caloriesTrackingService;

	public void setProfileService(ProfileService profileService) {
		logger.info("Setting ProfileService: {}", profileService);
		this.profileService = profileService;
	}

	public void setCaloriesTrackingService(CaloriesTrackingService caloriesTrackingService) {
		logger.info("Setting CaloriesTrackingService: {}", caloriesTrackingService);
		this.caloriesTrackingService = caloriesTrackingService;
	}

	@Override
	public void show(Stage stage) {
		logger.info("Showing MenuView");
		this.primaryStage = stage;
		this.loadWelcomeView();
	}

	/**
	 * This method is used to load the Welcome view of the application.
	 * It first checks if a Profile has been created. If a Profile exists, it loads the Menu view.
	 * If a Profile does not exist, it loads the Create Profile view.
	 * This method is an implementation of the loadWelcomeView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadWelcomeView() {
		if (this.profileService.isProfileCreated()) {
			this.loadMenuView();
		} else {
			this.loadCreateProfileView();
		}
	}

	/**
	 * This method is used to load the Menu view of the application.
	 * It uses the ViewLoader instance to load the view, passing the primary stage and the current instance of MenuController as arguments.
	 * This method is an implementation of the loadMenuView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadMenuView() {
		this.loadView("/ulb/views/Menu.fxml", this.primaryStage);
		this.viewController.setListener(this);
	}

	/**
	 * This method is used to load the Create Profile view of the application.
	 * It uses the helper method loadViewWithController to load the view, passing a new instance of ProfileCreateController as an argument.
	 * The ProfileCreateController instance is created with a lambda expression that calls the loadMenuView method when invoked.
	 * This method is an implementation of the loadCreateProfileView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadCreateProfileView() {
		AppController controller =
				new ProfileCreateController(this.profileService, this::loadMenuView);
		controller.show(this.primaryStage);
	}

	/**
	 * This method is used to load the Open Profile view of the application.
	 * It uses the helper method loadViewWithController to load the view, passing a new instance of ProfileController as an argument.
	 * The ProfileController instance is created with a listener that defines the behavior for deleting a profile and returning home.
	 * The listener's deleteProfile method calls the loadDeleteProfileView method, which loads the Delete Profile view.
	 * The listener's returnHome method calls the loadWelcomeView method, which loads the Welcome view.
	 * This method is an implementation of the loadOpenProfileView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadOpenProfileView() {
		AppController controller =
				new ProfileController(
						this.profileService,
						new ProfileController.Listener() {
							@Override
							public void deleteProfile() {
								loadDeleteProfileView();
							}

							@Override
							public void returnHome() {
								loadWelcomeView();
							}
						});
		controller.show(this.primaryStage);
	}

	/**
	 * This method is used to load the Delete Profile view.
	 * It creates a new Stage for the popup window and loads the view using the ViewLoader instance.
	 * The view is associated with a new instance of ProfileDeleteController, which has a listener that defines the behavior for returning home and creating a profile.
	 * The listener's returnHome method closes the popup stage, and the createProfile method loads the Create Profile view and then closes the popup stage.
	 * After the view is loaded, the method sets the modality of the popup stage to APPLICATION_MODAL, which means that while the popup stage is showing, it blocks user interaction with all other stages of the application.
	 * Finally, the method shows the popup stage and waits for it to be hidden (closed) before returning.
	 */
	public void loadDeleteProfileView() {
		Stage popupStage = new Stage();
		AppController controller =
				new ProfileDeleteController(
						this.profileService,
						new ProfileDeleteController.Listener() {
							@Override
							public void returnHome() {
								popupStage.close();
							}

							@Override
							public void createProfile() {
								loadCreateProfileView();
								popupStage.close();
							}
						});
		controller.show(popupStage);
		// This line sets the modality of the popup stage to APPLICATION_MODAL.
		// This means that while the popup stage is showing, it blocks user interaction with all
		// other stages of the application.
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	/**
	 * This method is used to load the Create Activity view of the application.
	 * It first creates a new Stage for the popup window and loads the view using the ViewLoader instance.
	 * The view is associated with a new instance of ActivityCreateController, which has a listener that defines the behavior for returning to the menu and closing the popup stage.
	 * The listener's method is a lambda expression that calls the loadMenuView method and closes the popup stage when invoked.
	 * After the view is loaded, the method sets the modality of the popup stage to APPLICATION_MODAL, which means that while the popup stage is showing, it blocks user interaction with all other stages of the application.
	 * Finally, the method shows the popup stage and waits for it to be hidden (closed) before returning.
	 * This method is an implementation of the loadCreateActivityView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadCreateActivityView() {
		ActivityDTO activityDTO = new ActivityDTO();
		loadCreateActivityView(activityDTO);
	}

	public void loadCreateActivityView(ActivityDTO activityDTO) {
		Stage popupStage = new Stage();
		ActivityRepository activityRepository = new JSONActivityRepository();
		ActivityService activityService = new ActivityService(activityRepository);
		AppController controller =
				new ActivityCreateController(
						activityService,
						this.profileService,
						new ActivityCreateController.Listener() {
							@Override
							public void goToActivityHistory() {
								loadActivityHistoryView();
								popupStage.close();
							}

							@Override
							public void returnHome() {
								popupStage.close();
							}
						});
		if (activityDTO.sport() != null) {
			controller.show(popupStage);
			((ActivityCreateController) controller).setDefaultActivity(activityDTO);
		} else {
			controller.show(popupStage);
		}
		// This line sets the modality of the popup stage to APPLICATION_MODAL.
		// This means that while the popup stage is showing, it blocks user interaction with all
		// other stages of the application.
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	/**
	 * This method is used to load the Activity History view of the application.
	 * It uses the helper method loadViewWithController to load the view, passing a new instance of ActivityHistoryController as an argument.
	 * The ActivityHistoryController instance is created with a lambda expression that calls the loadMenuView method when invoked.
	 * This method is an implementation of the loadActivityHistoryView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadActivityHistoryView() {
		ActivityRepository activityRepository = new JSONActivityRepository();
		ActivityService activityService = new ActivityService(activityRepository);
		AppController controller =
				new ActivityHistoryController(
						activityService,
						new ActivityHistoryController.Listener() {
							@Override
							public void returnHome() {
								loadMenuView();
							}

							@Override
							public void addActivity() {
								loadCreateActivityView();
							}

							@Override
							public void editActivity(ActivityDTO activityDTO) {
								loadCreateActivityView(activityDTO);
							}
						});

		controller.show(this.primaryStage);
	}

	/**
	 * This method is used to load the Meal History view of the application.
	 * It uses the helper method loadViewWithController to load the view, passing a new instance of MealHistoryController as an argument.
	 * The MealHistoryController instance is created with a lambda expression that calls the loadMenuView method when invoked.
	 * This method is an implementation of the loadMealHistoryView method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadMealHistoryView() {
		ConsumeMealRepository consumeMealRepository = new JSONConsumeMealRepository();
		ConsumeMealService consumeMealService = new ConsumeMealService(consumeMealRepository);
		AppController controller =
				new MealHistoryController(
						consumeMealService,
						new MealHistoryController.Listener() {

							@Override
							public void returnHome() {
								loadMenuView();
							}

							@Override
							public void addMeal() {
								loadFoodSearchPage();
							}
						});
		controller.show(this.primaryStage);
	}

	/**
	 * This method is used to load the Food Search Page of the application.
	 * It first creates a new Stage for the popup window and loads the view using the ViewLoader instance.
	 * The view is associated with a new instance of FoodController, which has a listener that defines the behavior for returning to the menu and closing the popup stage.
	 * The listener's method is a lambda expression that calls the loadMenuView method and closes the popup stage when invoked.
	 * After the view is loaded, the method sets the modality of the popup stage to APPLICATION_MODAL, which means that while the popup stage is showing, it blocks user interaction with all other stages of the application.
	 * Finally, the method shows the popup stage and waits for it to be hidden (closed) before returning.
	 * This method is an implementation of the loadFoodSearchPage method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public void loadFoodSearchPage() {
		Stage popupStage = new Stage();
		AppController controller =
				new AddFoodController(
						() -> {
							loadMenuView();
							popupStage.close();
						},
						popupStage);
		controller.show(popupStage);
		// This line sets the modality of the popup stage to APPLICATION_MODAL.
		// This means that while the popup stage is showing, it blocks user interaction with all
		// other stages of the application.
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	/**
	 * This method is used to get the path of the profile image.
	 * It returns the constant IMAGE_PATH defined in the Profile class.
	 * This method is an implementation of the getProfileImagePath method defined in the MenuViewController.Listener interface.
	 */
	@Override
	public String getProfileImagePath() {
		return this.profileService.getProfileImagePath();
	}

	@Override
	public List<DateCalorieDTO> getGraphData() {
		return this.caloriesTrackingService.getCaloriesTracking(31);
	}

	@Override
	public String getProfileWeight() {
		float profileWeight = this.profileService.getProfileWeight();
		if (!(profileWeight % 1 > 0)) {
			return String.valueOf((int) profileWeight);
		}
		return String.valueOf(profileWeight);
	}
}
