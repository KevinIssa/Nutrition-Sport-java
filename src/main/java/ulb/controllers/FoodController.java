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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.models.Meal;
import ulb.views.FoodViewController;

/**
 * The FoodController class is responsible for handling the food-related operations.
 */
public class FoodController implements AppController, FoodViewController.Listener {

	// Listener for the FoodController
	private final FoodController.Listener listener;
	// ViewController for the FoodController
	private final FoodViewController viewController;
	// FoodLoader for the FoodController
	private FoodLoader foodLoader;

	/**
	 * Constructor for the FoodController class.
	 * @param listener Listener for the FoodController
	 * @param viewController ViewController for the FoodController
	 */
	public FoodController(FoodController.Listener listener, FoodViewController viewController) {
		this.listener = listener;
		this.viewController = viewController;
		this.foodLoader = loadFoods();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	/**
	 * This method is used to load foods from the database.
	 * @return A FoodLoader object
	 */
	private FoodLoader loadFoods() {
		FoodLoader foodLoader = new FoodLoader();
		foodLoader.extend(loadMeals());
		return foodLoader;
	}

	public void reload() {
		this.foodLoader = loadFoods();
	}

	private List<Food> loadMeals() {
		return Meal.loadAll().stream().map(Meal::toFood).collect(Collectors.toList());
	}

	@Override
	public int getCaloriesConsumedByGrams(String food, int quantity) {
		return this.foodLoader.getFoodByName(food).getCaloriesConsumedByGrams(quantity);
	}

	/**
	 * Save the foods consumed by the user.
	 * It creates a new FoodLoader object, extends it with meals and returns it.
	 * @return A FoodLoader object
	 */
	@Override
	public void saveConsumedFoods(
			ArrayList<ArrayList<String>> consumedFoodsList, LocalDateTime mealDate) {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		for (List<String> consumedFood : consumedFoodsList) {
			consumedMeal.addConsumedFood(
					consumedFood.get(0),
					Integer.parseInt(consumedFood.get(1)),
					Integer.parseInt(consumedFood.get(2)),
					consumedFood.get(3));
		}
		consumedMeal.setDate(mealDate);
		consumedMeal.save();
	}

	/**
	 * This method is used to get the corresponding food in the database from a given name.
	 * It retrieves the foods suggestion for the given name, gets the first one and returns it.
	 * @param food The name of the food
	 * @return The corresponding Food object
	 */
	@Override
	public Food getCorrespondingFood(String food) {
		return foodLoader.getFoodsSuggestion(food).stream()
				.findFirst()
				.orElseThrow(() -> new RuntimeException("food selected not in database"));
	}

	@Override
	public void saveMeal(String mealName, ArrayList<ArrayList<String>> consumedFoodsList) {
		Meal newmeal = new Meal(mealName);
		for (List<String> consumedFood : consumedFoodsList) {
			newmeal.addIngredient(
					getCorrespondingFood(consumedFood.get(0)),
					Integer.parseInt(consumedFood.get(1)));
		}
		newmeal.save();
	}

	@Override
	public String getFoodServingQuantity(String food) {
		return this.foodLoader.getFoodByName(food).getServingQuantity();
	}

	@Override
	public int extractServingQuantityValue(String food) {
		return this.foodLoader.getFoodByName(food).extractServingQuantityValue();
	}

	@Override
	public String getFoodServingType(String food) {
		return this.foodLoader.getFoodByName(food).getServingType();
	}

	/**
	 * This method is used to send the user's search to the viewController.
	 * It retrieves the foods suggestion for the search text from the foodLoader.
	 * Then, it extracts the name of each food and collects them into a list.
	 * Finally, it sets the suggestions in the viewController with this list.
	 *
	 * @param searchText The search text entered by the user.
	 */
	@Override
	public void sendUserSearch(String searchText) {
		this.viewController.setSuggestions(
				this.foodLoader.getFoodsSuggestion(searchText).stream()
						.map(Food::getName)
						.collect(Collectors.toList()));
	}

	public interface Listener {
		void returnHome();
	}
}
