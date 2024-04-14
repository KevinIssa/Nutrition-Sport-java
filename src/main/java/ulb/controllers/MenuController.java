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

import javafx.stage.Modality;
import javafx.stage.Stage;
import ulb.models.*;
import ulb.views.*;

public class MenuController implements AppController, MenuViewController.Listener {

	private final ViewLoader viewLoader = new ViewLoader();
	private final Stage primaryStage;

	public MenuController(Stage primaryStage) {
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
		viewLoader.loadView(this.primaryStage, this);
	}

	@Override
	public void loadCreateProfileView() {
		loadViewWithController(new ProfileCreateController(this::loadMenuView));
	}

	@Override
	public void loadOpenProfileView() {
		loadViewWithController(
				new ProfileController(
						new ProfileController.Listener() {
							@Override
							public void deleteProfile() {
								loadDeleteProfileView();
							}

							@Override
							public void returnHome() {
								loadWelcomeView();
							}
						}));
	}

	public void loadDeleteProfileView() {
		Stage popupStage = new Stage();
		viewLoader.loadView(
				popupStage,
				new ProfileDeleteController(
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
						}));
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	@Override
	public void loadCreateActivityView() {
		Stage popupStage = new Stage();
		ActivityCreateViewController viewController =
				(ActivityCreateViewController) viewLoader.loadActivityCreate(popupStage);
		viewController.setListener(
				new ActivityCreateController(
						() -> {
							loadMenuView();
							popupStage.close();
						},
						viewController));
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	@Override
	public void loadActivityHistoryView() {
		loadViewWithController(new ActivityHistoryController(this::loadMenuView));
	}

	@Override
	public void loadMealHistoryView() {
		loadViewWithController(new MealHistoryController(this::loadMenuView));
	}

	@Override
	public void loadFoodSearchPage() {
		Stage popupStage = new Stage();
		FoodViewController foodViewController =
				(FoodViewController) viewLoader.loadAddMeal(popupStage);
		foodViewController.setListener(
				new FoodController(
						() -> {
							loadMenuView();
							popupStage.close();
						},
						foodViewController));
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	@Override
	public String getProfileImagePath() {
		return Profile.IMAGE_PATH;
	}

	private void loadViewWithController(AppController controller) {
		viewLoader.loadView(this.primaryStage, controller);
	}
}
