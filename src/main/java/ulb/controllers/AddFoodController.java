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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ConsumedFoodDTO;
import ulb.dtos.FoodDTO;
import ulb.dtos.MealDTO;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.models.Meal;
import ulb.repositories.JSONConsumeMealRepository;
import ulb.views.AddFoodViewController;
import ulb.views.MakeMealViewController;

/**
 * The FoodController class is responsible for managing the interactions between the FoodViewController and the model classes related to food and meals.
 * It implements the AppController interface and the Listener interface from the FoodViewController.
 * This class handles the loading of foods from the database, the calculation of calories consumed by a certain quantity of food, the saving of consumed foods and meals, and the retrieval of food details.
 * It also handles the user's search for foods and the return to the home screen of the application.
 */
public class AddFoodController extends AppController
		implements AddFoodViewController.Listener,
				FoodPopupController.Listener,
				MakeMealViewController.Listener {
	private static final Logger logger = LoggerFactory.getLogger(AddFoodController.class);
	public static final String ADD_FOOD_FXML = "/ulb/views/AddFood.fxml";
	private final AddFoodController.Listener listener;
	private final FoodLoader foodLoader = FoodLoader.getInstance();
	private final FoodPopupController foodPopupController;
	private boolean isAddFood = true;
	private Stage stage;

	/**
	 * Constructor for the FoodController class.
	 * @param listener Listener for the FoodController
	 */
	public AddFoodController(AddFoodController.Listener listener) {
		this.listener = listener;
		this.foodPopupController = new FoodPopupController(this);
		this.foodPopupController.show(new Stage());
	}

	@Override
	public void show(Stage stage) {
		this.loadView(ADD_FOOD_FXML, stage);
		this.stage = stage;
		this.viewController.setListener(this);
	}

	@Override
	public void returnHome() {
		this.listener.returnHome();
	}

	@Override
	public void saveMeal(String mealName, List<FoodDTO> foodList, int personAmount) {
		Meal meal = new Meal(mealName);
		for (FoodDTO food : foodList) {
			meal.addIngredient(
					this.foodLoader.getFoodByName(food.name()), food.quantity() / personAmount);
		}
		meal.save();
		foodLoader.extend(List.of(meal.toFood()));
	}

	@Override
	public void changeMode() {
		String resource = "/ulb/views/AddFood.fxml";
		if (this.isAddFood) {
			resource = "/ulb/views/MakeMeal.fxml";
		}
		this.loadView(resource, this.stage);
		this.viewController.setListener(this);
		this.isAddFood = !this.isAddFood;
	}

	@Override
	public double getCaloriesConsumed(String food, double quantity) {
		return this.foodLoader.getFoodByName(food).getCaloriesConsumedByUnit(quantity);
	}

	@Override
	public void saveConsumedFoods(List<ConsumedFoodDTO> consumedFoodsList, LocalDateTime mealDate) {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		for (ConsumedFoodDTO consumedFood : consumedFoodsList) {
			consumedMeal.addConsumedFood(
					consumedFood.name(),
					Double.parseDouble(String.valueOf(consumedFood.quantity())),
					Double.parseDouble(String.valueOf(consumedFood.calories())),
					consumedFood.unit());
		}
		consumedMeal.setDate(mealDate);
		new JSONConsumeMealRepository().save(consumedMeal);
	}

	@Override
	public String getFoodServingQuantity(String food) {
		return this.foodLoader.getFoodByName(food).getServingQuantity();
	}

	@Override
	public String getFoodUnit(String food) {
		return this.foodLoader.getFoodByName(food).getUnit().toString();
	}

	@Override
	public List<String> getUserSearch(String searchText) {
		return this.foodLoader.getFoodsSuggestion(searchText).stream()
				.map(Food::getName)
				.collect(Collectors.toList());
	}

	@Override
	public void askUserFoodQuantity(String food) {
		logger.info("Asking user for food quantity for food: {}", food);
		Stage popup = new Stage();
		this.foodPopupController.show(popup);
		this.foodPopupController.setFood(food);
		this.foodPopupController.setFoodServing(this.getFoodServingQuantity(food));
		this.foodPopupController.setFoodUnit(this.getFoodUnit(food));
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.showAndWait();
	}

	@Override
	public void onEntry(String food, double value) {
		if (isAddFood) {
			((AddFoodViewController) this.viewController).addChosenFood(food, value);
		} else {
			((MakeMealViewController) this.viewController).addChosenFood(food, value);
		}
	}

	public void setDefaultRecipe(MealDTO meal) {
		this.changeMode();
		((MakeMealViewController) this.viewController).setDefaultRecipe(meal);
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
