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
		viewLoader.loadMenu(this.primaryStage, this);
	}

	@Override
	public void loadCreateProfileView() {
		viewLoader.loadCreateProfile(
				this.primaryStage, new ProfileCreateController(this::loadWelcomeView));
	}

	@Override
	public void loadOpenProfileView() {

		viewLoader.loadProfile(
				this.primaryStage,
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

	public void loadDeleteProfileView() {
		Stage popupStage = new Stage();
		viewLoader.loadDeleteProfile(
				popupStage,
				() ->
						new ProfileDeleteConfirmViewController.Listener() {
							@Override
							public void deleteProfile() {
								Profile profile = Profile.load();
								profile.delete();
								Activity.clearAllActivities();
								ConsumedMeal.clearAllConsumedMeals();
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
								popupStage.close();
							}
						});
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}

	@Override
	public void loadCreateActivityView() {
		Stage popupStage = new Stage();
		ActivityCreateViewController viewController =
				(ActivityCreateViewController) viewLoader.loadCreateActivity(popupStage);
		viewController.setListener(
				new ActivityCreateViewController.Listener() {
					@Override
					public void saveActivity(
							Sport selectedSport,
							int selectedIntensity,
							String selectedDuration,
							LocalDateTime activityDateTime) {
						Activity activity =
								new Activity(
										selectedSport,
										Intensity.fromInt(selectedIntensity),
										Duration.ofMinutes(Long.parseLong(selectedDuration)),
										activityDateTime);
						activity.save();
						viewController.showAlert(
								activity.getCaloriesBurned(Profile.load().getWeight()));
					}

					@Override
					public void returnHome() {
						popupStage.close();
					}
				});
		popupStage.initModality(Modality.APPLICATION_MODAL);
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

					private List<Food> loadFoods(String searchText) {

						FoodLoader foodLoader = new FoodLoader();
						foodLoader.extend(loadMeals());
						return foodLoader.getFoodsSuggestion(searchText);
					}

					public void reload() {
						this.foodLoader = new FoodLoader().extend(loadMeals());
					}

					private Food convertMealToFood(Meal meal) {
						return new Food(
								meal.getName(),
								meal.getCaloriesConsumedByServing(1),
								meal.getCaloriesConsumed(),
								String.format("1 serving (%d g)", meal.getGramsForServing(1)));
					}

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

					private List<String> foodToString(List<Food> foods) {

						List<String> foodNames = new java.util.ArrayList<>();
						for (Food food : foods) {
							foodNames.add(food.getName());
						}

						return foodNames;
					}

					@Override
					public int getCaloriesConsumedByGrams(String food, int quantity) {
						Food foodObject = foodLoader.getFoodByName(food);
						return foodObject.getCaloriesConsumedByGrams(quantity);
					}

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

					@Override
					public Food getCorrespondingFood(String food) {
						List<Food> foods = loadFoods(food);
						if (!foods.isEmpty()) {
							return foods.get(0);
						} else {
							throw new RuntimeException("food selected not in database");
						}
					}

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

					@Override
					public String getFoodServingQuantity(String food) {
						Food selectedfood = foodLoader.getFoodByName(food);
						return selectedfood.getServingQuantity();
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
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.showAndWait();
	}
}
