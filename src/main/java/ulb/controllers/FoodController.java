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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.models.Meal;
import ulb.views.FoodViewController;

public class FoodController implements AppController, FoodViewController.Listener {

	private final FoodController.Listener listener;
	private final FoodViewController viewController;

	public FoodController(FoodController.Listener listener, FoodViewController viewController) {
		this.listener = listener;
		this.viewController = viewController;
	}

	FoodLoader foodLoader = new FoodLoader().extend(loadMeals());

	@Override
	public void returnHome() {
		this.listener.returnHome();
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
			ArrayList<ArrayList<String>> consumedFoodsList, LocalDateTime mealdate) {
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
	public void saveMeal(String mealname, ArrayList<ArrayList<String>> consumedFoodsList) {
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
		this.viewController.setSuggestions(foodNames);
	}

	public interface Listener {
		void returnHome();
	}
}
