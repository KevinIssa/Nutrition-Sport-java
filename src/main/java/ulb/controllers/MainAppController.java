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
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ulb.controllers.dtos.ActivityDTO;
import ulb.models.*;
import ulb.models.Meal;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sex;
import ulb.models.enums.Sport;
import ulb.views.*;

/**
 * MainAppController is the main controller for the application.
 * It extends AppController and implements the MenuViewController.Listener interface.
 * This controller is responsible for managing the primary stage of the application and loading different views.
 */
public class MainAppController extends AppController implements MenuViewController.Listener {

	// ViewLoader instance used to load different views in the application.
	private final ViewLoader viewLoader = new ViewLoader();

	private final Stage primaryStage;

	/**
	 * Constructor for the MainAppController.
	 * Initializes the primary stage of the application.
	 *
	 * @param primaryStage The primary stage of the application.
	 */
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

	/**
	 * Loads the menu view.
	 */
	@Override
	public void loadMenuView() {
		viewLoader.loadMenu(this.primaryStage, () -> this);
	}

	/**
	 * This method is used to load the profile creation view.
	 * It uses the viewLoader to load the CreateProfile view and sets up the necessary listeners.
	 * The listeners include saving the profile, returning to the home view, and saving the profile image.
	 */
	@Override
	public void loadCreateProfileView() {
		viewLoader.loadCreateProfile(
				this.primaryStage,
				() ->
						new ProfileCreateViewController.Listener() {

							/**
							 * This method is used to save the profile.
							 * It creates a new Profile object with the provided parameters and saves it.
							 *
							 * @param firstName The first name of the user.
							 * @param lastName The last name of the user.
							 * @param sex The sex of the user.
							 * @param birthDate The birth date of the user.
							 * @param height The height of the user.
							 * @param weight The weight of the user.
							 */
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

							/**
							 * This method is used to return to the home view.
							 * It calls the loadWelcomeView method to load the welcome view.
							 */
							@Override
							public void returnHome() {
								loadWelcomeView();
							}

							@Override
							public void saveProfileImage(String imagepath) {
								try {
									URL imageurl = new URL(imagepath);

									// Create a URI object from a File object. The File object
									// represents the file "profile.png" in the local file system.
									URI destinationuri = new File("profile.png").toURI();

									// Convert the URI object to a Path object. The Path object
									// represents the path in the file system.
									Path destinationpath = Paths.get(destinationuri);

									// Use the Files.copy method to copy the content of the image
									// file from the URL to the local file.
									// The method takes three parameters:
									// 1. The InputStream object which is obtained by calling the
									// openStream method on the URL object.
									// 2. The destination path where the file will be copied to.
									// 3. The copy option which determines how the copying should be
									// done. In this case, if the file already exists, it will be
									// replaced.

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

	/**
	 * This method is used to load the profile view.
	 * It uses the viewLoader to load the Profile view and sets up the necessary listeners.
	 * The listeners include saving the profile, deleting the profile view, returning to the home view, saving the profile image, and getting profile details.
	 */
	@Override
	public void loadOpenProfileView() {

		viewLoader.loadProfile(
				this.primaryStage,
				() ->
						new ProfileViewController.Listener() {
							final Profile profile = Profile.load();

							/**
							 * This method is used to save the profile.
							 * It creates a new Profile object with the provided parameters and saves it.
							 *
							 * @param firstName The first name of the user.
							 * @param lastName The last name of the user.
							 * @param sex The sex of the user.
							 * @param birthDate The birth date of the user.
							 * @param height The height of the user.
							 * @param weight The weight of the user.
							 */
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
							public void deleteProfileView() {
								loadDeleteProfileView();
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

							// Uses the same method as in loadCreateProfileView, the doc is already
							// there
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

									// Create a new Image object with the specified width and
									// height.
									// The last two parameters are set to true to preserve the
									// aspect ratio and improve the quality of the image.
									return new Image(path.toString(), width, height, true, true);

								}
								// If the URL is not formatted correctly
								catch (MalformedURLException e) {
									throw new RuntimeException(e);
								}
							}
						});
	}

	/**
	 * This method is used to load the delete profile view.
	 * It uses the viewLoader to load the DeleteProfile view and sets up the necessary listeners.
	 * The listeners include deleting the profile and returning to the home view.
	 */
	public void loadDeleteProfileView() {
		Stage popupStage = new Stage();
		viewLoader.loadDeleteProfile(
				popupStage,
				() ->
						new ProfileDeleteConfirmViewController.Listener() {
							@Override
							public void deleteProfile() {
								// clears all data related to the profile
								Profile profile = Profile.load();
								profile.delete();
								Activity.clearAllActivities();
								ConsumedMeal.clearAllConsumedMeals();
								loadCreateProfileView();
								try {
									// Delete the profile image file
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
								popupStage.close();
							}
						});

		// Set the modality of the popup stage to APPLICATION_MODAL.
		// This means that while the popup is showing, it blocks user interaction with all other
		// windows in the application.
		popupStage.initModality(Modality.APPLICATION_MODAL);

		// Show the popup stage and wait for it to be hidden (closed) before returning to the
		// caller.
		// This means that the execution of the current thread (which is the JavaFX Application
		// thread) will be halted until the popup stage is hidden.
		popupStage.showAndWait();
	}

	/**
	 * This method is used to load the activity creation view.
	 * It uses the viewLoader to load the CreateActivity view and sets up the necessary listeners.
	 * The listeners include saving the activity and returning to the home view.
	 */
	@Override
	public void loadCreateActivityView() {
		Stage popupStage = new Stage();
		ActivityCreateViewController viewController =
				(ActivityCreateViewController) viewLoader.loadCreateActivity(popupStage);
		viewController.setListener(
				new ActivityCreateViewController.Listener() {

					/**
					 * This method is used to save the activity.
					 * It creates a new Activity object with the provided parameters and saves it.
					 * It also shows an alert with the calories burned during the activity.
					 *
					 * @param selectedSport The sport of the activity.
					 * @param selectedIntensity The intensity of the activity.
					 * @param selectedDuration The duration of the activity.
					 * @param activityDateTime The date and time of the activity.
					 */
					@Override
					public void saveActivity(
							Sport selectedSport,
							int selectedIntensity,
							String selectedDuration,
							LocalDateTime activityDateTime) {

						// create a new activity object with the provided parameters
						Activity activity =
								new Activity(
										selectedSport,
										Intensity.fromInt(selectedIntensity),
										Duration.ofMinutes(Long.parseLong(selectedDuration)),
										activityDateTime);
						activity.save();
						// create a pop up with the calories burned during the activity
						viewController.showAlert(
								activity.getCaloriesBurned(Profile.load().getWeight()));
					}

					@Override
					public void returnHome() {
						popupStage.close();
					}
				});

		// Set the modality of the popup stage to APPLICATION_MODAL
		// This means that while the popup is showing, it blocks user interaction with all other
		// windows in the application.
		popupStage.initModality(Modality.APPLICATION_MODAL);

		// Show the popup stage and wait for it to be hidden (closed) before returning to the caller
		// This means that the execution of the current thread (which is the JavaFX Application
		// thread) will be halted until the popup stage is hidden.
		popupStage.showAndWait();
	}

	@Override
	public void loadActivityHistoryView() {
		viewLoader.loadActivityHistory(
				this.primaryStage,
				() ->
						new ActivityHistoryViewController.Listener() {
							@Override
							public void returnHome() {
								loadWelcomeView();
							}

							/**
							 * This method is used to get activities based on a filter.
							 * It loads all activities from the "activities" directory and filters them based on the provided Sport filter.
							 * If the filter is null, all activities are returned.
							 * Each activity is converted to an ActivityDTO object before being added to the list.
							 *
							 * @param filter The Sport filter. If null, all activities are returned.
							 * @return A list of ActivityDTO objects representing the filtered activities.
							 */
							@Override
							public List<ActivityDTO> getActivities(Sport filter) {
								File folder = new File("activities");
								List<ActivityDTO> list = new ArrayList<>();
								File[] files = folder.listFiles();
								try {
									Objects.requireNonNull(files);
								} catch (NullPointerException e) {
									return list;
								}
								for (File file : files) {
									Activity activity = Activity.load(file.getPath());
									if (filter == null || activity.getSport() == filter) {
										list.add(new ActivityDTO(activity));
									}
								}
								return list;
							}
						});
	}

	@Override
	public void loadMealHistoryView() {
		viewLoader.loadMealHistory(
				this.primaryStage,
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

	/**
	 * This method is used to load the food search page.
	 * It uses the viewLoader to load the AddMeal view and sets up the necessary listeners.
	 * The listeners include returning to the home view, loading foods based on a search text, reloading the food loader,
	 * converting a meal to a food, loading meals from a directory, converting a list of foods to a list of food names,
	 * getting the calories consumed by a certain quantity of a food, saving consumed foods, getting the corresponding food object from a food name,
	 * saving a meal, getting the serving quantity of a food, extracting the serving quantity value from a food, getting the serving type of a food,
	 * and sending the user's search text to load foods.
	 */
	@Override
	public void loadFoodSearchPage() {
		Stage popupStage = new Stage();
		FoodViewController foodViewController =
				(FoodViewController) viewLoader.loadAddMeal(popupStage);

		foodViewController.setListener(
				new FoodViewController.Listener() {

					FoodLoader foodLoader = new FoodLoader().extend(loadMeals());

					@Override
					public void returnHome() {
						popupStage.close();
					}

					/**
					 * This method is used to load foods based on a search text.
					 * It creates a new FoodLoader, extends it with the loaded meals, and returns the foods suggested by the FoodLoader.
					 *
					 * @param searchText The search text.
					 * @return A list of Food objects suggested by the FoodLoader.
					 */
					private List<Food> loadFoods(String searchText) {

						FoodLoader foodLoader = new FoodLoader();
						foodLoader.extend(loadMeals());
						return foodLoader.getFoodsSuggestion(searchText);
					}

					/**
					 * This method is used to reload the food loader.
					 * It creates a new FoodLoader and extends it with the loaded meals.
					 */
					public void reload() {
						this.foodLoader = new FoodLoader().extend(loadMeals());
					}

					/**
					 * This method is used to convert a Meal object to a Food object.
					 * It creates a new Food object with the name, calories consumed by 100 grams, total calories consumed, and serving quantity of the meal.
					 *
					 * @param meal The Meal object.
					 * @return The converted Food object.
					 */
					private Food convertMealToFood(Meal meal) {
						return new Food(
								meal.getName(),
								meal.getCaloriesConsumedByServing(1),
								meal.getCaloriesConsumed(),
								String.format("1 serving (%d g)", meal.getGramsForServing(1)));
					}

					/**
					 * This method is used to load meals from a directory.
					 * It creates a new File object for the "meals" directory, gets the list of files in the directory, and adds each meal to a list.
					 *
					 * @return A list of Food objects representing the loaded meals.
					 */
					private List<Food> loadMeals() {
						File directory = new File("meals"); // Specify the directory path
						File[] files = directory.listFiles();
						// Add Meals to the list
						List<Food> result = new java.util.ArrayList<>();
						if (files != null) {
							for (File file : files) {
								Meal meal = Meal.load(file.getPath());
								Food food = convertMealToFood(meal);
								result.add(food);
							}
						}
						return result;
					}

					/**
					 * This method is used to convert a list of Food objects to a list of food names.
					 * It creates a new list and adds the name of each food to the list.
					 *
					 * @param foods The list of Food objects.
					 * @return A list of food names.
					 */
					private List<String> foodToString(List<Food> foods) {

						List<String> foodNames = new java.util.ArrayList<>();
						for (Food food : foods) {
							foodNames.add(food.getName());
						}

						return foodNames;
					}

					/**
					 * This method is used to get the calories consumed by a certain quantity of a food.
					 * It gets the Food object from the food loader by the food name and returns the calories consumed by the quantity.
					 *
					 * @param food The food name.
					 * @param quantity The quantity.
					 * @return The calories consumed by the quantity of the food.
					 */
					@Override
					public int getCaloriesConsumedByGrams(String food, int quantity) {
						Food foodObject = foodLoader.getFoodByName(food);
						return foodObject.getCaloriesConsumedByGrams(quantity);
					}

					/**
					 * This method is used to save consumed foods.
					 * It creates a new ConsumedMeal object, adds each consumed food to the consumed meal, sets the date of the consumed meal, and saves the consumed meal.
					 *
					 * @param consumedFoodsList The list of consumed foods. Each consumed food is represented by a list of strings containing the food name, quantity, calories, and type.
					 * @param mealdate The date of the meal.
					 */
					@Override
					public void saveConsumedFoods(
							ArrayList<ArrayList<String>> consumedFoodsList,
							LocalDateTime mealdate) {
						ConsumedMeal consumedMeal = new ConsumedMeal();
						for (List<String> consumedFood : consumedFoodsList) {
							String food = consumedFood.get(0);
							int quantity = Integer.parseInt(consumedFood.get(1));
							int calories = Integer.parseInt(consumedFood.get(2));
							String type = consumedFood.get(3);
							consumedMeal.addConsumedFood(food, quantity, calories, type);
						}

						consumedMeal.setDate(mealdate);
						consumedMeal.save();
					}

					/**
					 * This method is used to get the corresponding Food object from a food name.
					 * It loads foods based on the food name stored in the database and returns the first food in the list.
					 * If the list is empty, it throws an exception.
					 *
					 * @param food The food name.
					 * @return The corresponding Food object.
					 */
					@Override
					public Food getCorrespondingFood(String food) {
						List<Food> foods = loadFoods(food);
						if (!foods.isEmpty()) {
							return foods.get(0);
						} else {
							throw new RuntimeException("food selected not in database");
						}
					}

					/**
					 * This method is used to save a meal.
					 * It creates a new Meal object with the meal name, adds each ingredient to the meal, and saves the meal.
					 *
					 * @param mealname The meal name.
					 * @param consumedFoodsList The list of consumed foods. Each consumed food is represented by a list of strings containing the food name and quantity.
					 */
					@Override
					public void saveMeal(
							String mealname, ArrayList<ArrayList<String>> consumedFoodsList) {
						Meal newmeal = new Meal(mealname);
						for (List<String> consumedFood : consumedFoodsList) {
							String food = consumedFood.get(0);
							int quantity = Integer.parseInt(consumedFood.get(1));
							newmeal.addIngredient(getCorrespondingFood(food), quantity);
						}
						newmeal.save();
					}

					/**
					 * This method is used to get the serving quantity of a food.
					 * It gets the Food object from the food loader by the food name and returns the serving quantity of the food.
					 *
					 * @param food The food name.
					 * @return The serving quantity of the food.
					 */
					@Override
					public String getFoodServingQuantity(String food) {
						Food selectedfood = foodLoader.getFoodByName(food);
						return selectedfood.getServingQuantity();
					}

					/**
					 * This method is used to extract the serving quantity value from a food.
					 * It gets the Food object from the food loader by the food name and returns the extracted serving quantity value of the food.
					 *
					 * @param food The food name.
					 * @return The extracted serving quantity value of the food.
					 */
					@Override
					public int extractServingQuantityValue(String food) {
						return foodLoader.getFoodByName(food).extractServingQuantityValue();
					}

					/**
					 * This method is used to get the serving type of a food.
					 * It gets the Food object from the food loader by the food name and returns the serving type of the food.
					 *
					 * @param food The food name.
					 * @return The serving type of the food.
					 */
					@Override
					public String getFoodServingType(String food) {
						return foodLoader.getFoodByName(food).getServingType();
					}

					/**
					 * This method is used to send the user's search text to load foods.
					 * It loads foods based on the search of the user, converts the list of foods to a list of food names, and sets the suggestions of the food view controller.
					 *
					 * @param searchText The search text.
					 */
					@Override
					public void sendUserSearch(String searchText) {

						List<Food> foods = loadFoods(searchText);
						List<String> foodNames = foodToString(foods);
						foodViewController.setSuggestions(foodNames);
					}
				});

		// Set the modality of the popup stage to APPLICATION_MODAL
		// This means that while the popup is showing, it blocks user interaction with all other
		// windows in the application.
		popupStage.initModality(Modality.APPLICATION_MODAL);

		popupStage.showAndWait();
	}
}
