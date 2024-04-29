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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.ConsumedMeal;
import ulb.models.Food;
import ulb.models.FoodLoader;
import ulb.models.Meal;
import ulb.repositories.JSONConsumeMealRepository;
import ulb.views.AddFoodViewController;
import ulb.views.MakeMealViewController;
import ulb.widgets.FoodPopupController;

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
	private final AddFoodController.Listener listener;
	private final FoodLoader foodLoader;
	private final Stage popup;
	private FoodPopupController popupController;
	private Stage stage;
	private boolean isAddFood = true;

	/**
	 * Constructor for the FoodController class.
	 * @param listener Listener for the FoodController
	 */
	public AddFoodController(AddFoodController.Listener listener, Stage stage) {
		this.listener = listener;
		this.foodLoader = loadFoods();
		this.popup = new Stage();
		this.loadPopup();
		this.stage = stage;
	}

	private void loadPopup() {
		String resourceFile = "/ulb/widgets/Food_popup.fxml";
		try {
			FXMLLoader loader = new FXMLLoader(FoodPopupController.class.getResource(resourceFile));
			Parent root = loader.load();
			this.popupController = loader.getController();
			this.popupController.setListener(this);
			this.popup.setScene(new Scene(root));
			this.popup.setTitle("Quantité de nourriture");
			this.popup.hide();
		} catch (IOException e) {
			this.showLoadingAlert(resourceFile);
		}
	}

	@Override
	public void show(Stage stage) {
		this.stage = stage;
		this.loadView("/ulb/views/AddFood.fxml", stage);
		this.viewController.setListener(this);
	}

	/**
	 * This method is used to load foods from the database.
	 * @return A FoodLoader object
	 */
	private FoodLoader loadFoods() {
		FoodLoader foodLoader = new FoodLoader();
		foodLoader.extend(this.loadMeals());
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
	public void saveMeal(String mealName, double mealCalories, double mealGrams) {
		// Meal meal = new Meal(mealName, mealCalories, mealGrams);
		// TODO add to db
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
		return this.foodLoader.getFoodByName(food).getCaloriesConsumedByGrams(quantity);
	}

	@Override
	public void saveConsumedFoods(List<List<String>> consumedFoodsList, LocalDateTime mealDate) {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		for (List<String> consumedFood : consumedFoodsList) {
			consumedMeal.addConsumedFood(
					consumedFood.get(0),
					Double.parseDouble(consumedFood.get(1)),
					Double.parseDouble(consumedFood.get(2)),
					consumedFood.get(3));
		}
		consumedMeal.setDate(mealDate);
		new JSONConsumeMealRepository().save(consumedMeal);
	}

	@Override
	public String getFoodServingQuantity(String food) {
		return this.foodLoader.getFoodByName(food).getServingQuantity();
	}

	@Override
	public int getServingQuantityValue(String food) {
		return this.foodLoader.getFoodByName(food).extractServingQuantityValue();
	}

	@Override
	public String getFoodUnit(String food) {
		return this.foodLoader.getFoodByName(food).getQuantityUnit();
	}

	@Override
	public List<String> getUserSearch(String searchText) {
		return this.foodLoader.getFoodsSuggestion(searchText).stream()
				.map(Food::getName)
				.collect(Collectors.toList());
	}

	@Override
	public void askUserFoodQuantity(String food) {
		this.popupController.setFood(food);
		this.popupController.setFoodUnit(this.getFoodServingQuantity(food));
		this.popup.show();
	}

	@Override
	public void onBack() {
		this.popup.hide();
	}

	@Override
	public void onEntry(double value) {
		this.popup.hide();
		if (this.isAddFood) {
			((AddFoodViewController) this.viewController)
					.addChosenFood(this.popupController.getFood(), value);
		} else {
			((MakeMealViewController) this.viewController)
					.addChosenFood(this.popupController.getFood(), value);
		}
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
