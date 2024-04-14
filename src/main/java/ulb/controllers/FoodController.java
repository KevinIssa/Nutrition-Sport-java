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

public class FoodController implements AppController, FoodViewController.Listener {

	private final FoodController.Listener listener;
	private final FoodViewController viewController;
	private FoodLoader foodLoader;

	public FoodController(FoodController.Listener listener, FoodViewController viewController) {
		this.listener = listener;
		this.viewController = viewController;
		this.foodLoader = loadFoods();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

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
