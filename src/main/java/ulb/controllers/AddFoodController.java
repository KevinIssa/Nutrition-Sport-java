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
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.models.Meal;
import ulb.views.AddFoodViewController;

/**
 * The FoodController class is responsible for managing the interactions between the FoodViewController and the model classes related to food and meals.
 * It implements the AppController interface and the Listener interface from the FoodViewController.
 * This class handles the loading of foods from the database, the calculation of calories consumed by a certain quantity of food, the saving of consumed foods and meals, and the retrieval of food details.
 * It also handles the user's search for foods and the return to the home screen of the application.
 */
public class AddFoodController implements AppController, AddFoodViewController.Listener {

	private static final Logger logger = LoggerFactory.getLogger(AddFoodController.class);
	// Listener for the FoodController
	private final AddFoodController.Listener listener;
	// ViewController for the FoodController
	private final AddFoodViewController viewController;
	// FoodLoader for the FoodController
	private FoodLoader foodLoader;

	/**
	 * Constructor for the FoodController class.
	 * @param listener Listener for the FoodController
	 * @param viewController ViewController for the FoodController
	 */
	public AddFoodController(AddFoodController.Listener listener, AddFoodViewController viewController) {
		this.listener = listener;
		this.viewController = viewController;
		this.foodLoader = loadFoods();
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

	private List<Food> loadMeals() {
		return Meal.loadAll().stream().map(Meal::toFood).collect(Collectors.toList());
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}


	@Override
	public int getCaloriesConsumedByGrams(String food, int quantity) {
		return this.foodLoader.getFoodByName(food).getCaloriesConsumedByGrams(quantity);
	}

	@Override
	public void saveConsumedFoods(
			List<List<String>> consumedFoodsList, LocalDateTime mealDate) {
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
	public String getFoodServingQuantity(String food) {
		return this.foodLoader.getFoodByName(food).getServingQuantity();
	}

	@Override
	public int extractServingQuantityValue(String food) {
		return this.foodLoader.getFoodByName(food).extractServingQuantityValue();
	}

	@Override
	public String getFoodQuantityUnit(String food) {
		return this.foodLoader.getFoodByName(food).getFoodQuantityUnit();
	}

	@Override
	public void sendUserSearch(String searchText) {
		this.viewController.setSuggestions(this.foodLoader.getFoodsSuggestion(searchText).stream().map(Food::getName).collect(Collectors.toList()));
	}

	/**
	 * This is an interface for the Listener within the FoodController class.
	 * It is used to define the contract for the Listener, which is expected to be implemented by any class that wants to listen to events from the FoodController.
	 * <p>
	 * Currently, it has a single method, returnHome, which is expected to be called when the user wants to return to the home screen of the application.
	 */
	public interface Listener {

		/**
		 * This method is called when the user wants to return to the home screen of the application.
		 * The implementing class should define the behavior that occurs when this event happens.
		 */
		void returnHome();
	}
}
