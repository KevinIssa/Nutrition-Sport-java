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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ulb.models.*;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sex;
import ulb.models.enums.Sport;
import ulb.views.*;

public class MainAppController extends AppController implements MenuViewController.Listener {

	private final Stage primaryStage;

	public MainAppController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private ViewController loadView(String resourcePath) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
			Parent root = loader.load();
			ViewController viewController = loader.getController();
			primaryStage.setScene(new Scene(root, 300, 200));
			return viewController;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	private void loadView(String resourcePath, Supplier<Object> listenerSupplier) {
		ViewController viewController = this.loadView(resourcePath);
		assert viewController != null;
		viewController.setListener(listenerSupplier.get());
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
		this.primaryStage.setHeight(600);
		this.primaryStage.setWidth(800);
		loadView("/ulb/views/Menu.fxml", () -> this);
	}

	@Override
	public void loadCreateProfileView() {
		this.primaryStage.setHeight(700);
		this.primaryStage.setWidth(750);
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

							@Override
							public void saveProfileImage(String imagepath) {
								try {
									URL imageurl = new URL(imagepath);
									URI destinationuri = new File("profile.png").toURI();
									Path destinationpath = Paths.get(destinationuri);
									Files.copy(
											imageurl.openStream(),
											destinationpath,
											StandardCopyOption.REPLACE_EXISTING);
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							}
						});
	}

	@Override
	public void loadOpenProfileView() {
		this.primaryStage.setWidth(700);
		this.primaryStage.setHeight(770);
		loadView(
				"/ulb/views/Profile.fxml",
				() ->
						new ProfileViewController.Listener() {
							final Profile profile = Profile.load();

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

							@Override
							public void saveProfileImage(String imagepath) {
								try {
									URL imageurl = new URL(imagepath);
									URI destinationuri = new File("profile.png").toURI();
									Path destinationpath = Paths.get(destinationuri);
									Files.copy(
											imageurl.openStream(),
											destinationpath,
											StandardCopyOption.REPLACE_EXISTING);
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							}

							@Override
							public Image getProfileImage(double width, double height) {
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
								try {
									Path filetodelete = Paths.get(".").resolve("profile.png");
									if (Files.exists(filetodelete)) {
										Files.delete(filetodelete);
									}
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							}

							@Override
							public void returnHome() {
								loadMenuView();
							}
						});
	}

	@Override
	public void loadCreateActivityView() {
		ActivityCreateViewController viewController =
				(ActivityCreateViewController) this.loadView("/ulb/views/ActivityCreate.fxml");
		viewController.setListener(
				new ActivityCreateViewController.Listener() {
					@Override
					public void saveActivity(
							Sport selectedSport, String selectedIntensity, float selectedDuration) {
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


	@Override
	public void loadMealHistoryView() {
		loadView(
				"/ulb/views/MealHistory.fxml",
				() ->
						new MealHistoryViewController.Listener() {
							@Override
							public void returnHome() {
								loadWelcomeView();
							}

							@Override
							public ConsumedMeal loadMeal(String filename) {
								return ConsumedMeal.load(filename);
							}
						});
	}
	@Override
	public void loadFoodSearchPage() {

		this.primaryStage.setWidth(1080);
		this.primaryStage.setHeight(720);

		FoodViewController foodViewController =
				(FoodViewController) loadView("/ulb/views/AddMeal.fxml");

		foodViewController.setListener(
				new FoodViewController.Listener() {

					FoodLoader foodLoader = new FoodLoader("src/main/resources/food.json");

					@Override
					public void returnHome() {
						loadWelcomeView();
					}

					private List<Food> loadFoods(String searchText) {

						FoodLoader foodLoader = new FoodLoader("src/main/resources/food.json");
						return foodLoader.getFoodsSuggestion(searchText);
					}

					private List<String> foodToString(List<Food> foods) {

						List<String> foodNames = new java.util.ArrayList<>();
						for (Food food : foods) {
							foodNames.add(food.getName());
						}

						return foodNames;
					}

					@Override
					public int getCaloriesConsumedByGrams(String food, int quantity) {
						Food foodObject = new FoodLoader().getFoodByName(food);
						return foodObject.getCaloriesConsumedByGrams(quantity);
					}

					@Override
					public void saveConsumedFoods(ArrayList<ArrayList<String>> consumedFoodsList) {
						ConsumedMeal consumedMeal = new ConsumedMeal();
						for (List<String> consumedFood : consumedFoodsList) {
							String food = consumedFood.get(0);
							int quantity = Integer.parseInt(consumedFood.get(1));
							int calories = Integer.parseInt(consumedFood.get(2));
							consumedMeal.addConsumedFood(food, quantity, calories);
						}
						consumedMeal.save();
					}

					@Override
					public String getFoodServingQuantity(String food) {
						return foodLoader.getFoodByName(food).getServingQuantity();
					}

					@Override
					public int extractServingQuantityValue(String food) {
						return foodLoader.getFoodByName(food).extractServingQuantityValue();
					}

					@Override
					public String getFoodServingType(String food) {
						return foodLoader.getFoodByName(food).getServingType();
					}

					@Override
					public void sendUserSearch(String searchText) {

						List<Food> foods = loadFoods(searchText);
						List<String> foodNames = foodToString(foods);
						foodViewController.setSuggestions(foodNames);
					}
				});
	}
}
