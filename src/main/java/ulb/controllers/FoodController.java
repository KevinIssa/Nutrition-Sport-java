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
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.models.Meal;
import ulb.repositories.JSONConsumeMealRepository;
import ulb.views.AddFoodViewController;

/**
 * The FoodController class is responsible for managing the interactions between the FoodViewController and the model classes related to food and meals.
 * It implements the AppController interface and the Listener interface from the FoodViewController.
 * This class handles the loading of foods from the database, the calculation of calories consumed by a certain quantity of food, the saving of consumed foods and meals, and the retrieval of food details.
 * It also handles the user's search for foods and the return to the home screen of the application.
 */
public class FoodController extends AppController implements AddFoodViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	private final FoodController.Listener listener;
	private FoodLoader foodLoader;

	/**
	 * Constructor for the FoodController class.
	 *
	 * @param listener Listener for the FoodController
	 */
	public FoodController(Listener listener) {
		this.listener = listener;
		this.foodLoader = loadFoods();
	}

	@Override
	public void show(Stage stage) {
		this.loadView("/ulb/views/AddFood.fxml", stage);
		this.viewController.setListener(this);
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
		return Meal.loadAll().stream().map(Meal::toFood).toList();
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void reload() {
		this.foodLoader = loadFoods();
	}

	@Override
	public int getCaloriesConsumedByGrams(String food, int quantity) {
		return this.foodLoader.getFoodByName(food).getCaloriesConsumedByGrams(quantity);
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
		new JSONConsumeMealRepository().save(consumedMeal);
		// consumedMeal.save();
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
		((AddFoodViewController) this.viewController)
				.setSuggestions(
						FXCollections.observableArrayList(
								this.foodLoader.getFoodsSuggestion(searchText).stream()
										.map(Food::getName)
										.toList()));
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
