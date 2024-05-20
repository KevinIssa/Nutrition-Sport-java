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

import java.util.List;
import java.util.stream.Collectors;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.ConsumableDTO;
import ulb.dtos.FoodDTO;
import ulb.dtos.RecipeDTO;
import ulb.enums.Unit;
import ulb.models.ConsumedMeal;
import ulb.services.ConsumableService;
import ulb.services.ConsumeMealService;
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
	private final ConsumableService consumableService;
	private final ConsumeMealService consumeMealService;
	private final AddFoodController.Listener listener;
	private final FoodPopupController foodPopupController;
	private boolean isAddFood = true;
	private Stage stage;

	/**
	 * Constructor for the FoodController class.
	 * @param listener Listener for the FoodController
	 */
	public AddFoodController(
			ConsumableService consumableService, ConsumeMealService consumeMealService, AddFoodController.Listener listener) {
		this.consumableService = consumableService;
		this.consumeMealService = consumeMealService;
		this.listener = listener;
		this.foodPopupController = new FoodPopupController(this.consumableService, this);
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
	public void saveMeal(RecipeDTO recipeDTO) {
		this.consumableService.saveMeal(recipeDTO);
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
	public double getCaloriesConsumed(FoodDTO food) {
		return this.consumableService.getConsumableByName(food.name()).getCaloriesConsumedByUnit(food.quantity());
	}

	@Override
	public void saveConsumedFoods(ConsumedMeal consumedMeal) {
		this.consumeMealService.saveConsumedMeal(consumedMeal);
	}

	@Override
	public String getFoodServingQuantity(String food) {
		return this.consumableService.loadConsumable(food).servingQuantity();
	}

	@Override
	public Unit getFoodUnit(String food) {
		return this.consumableService.loadConsumable(food).unit();
	}

	@Override
	public List<String> getUserSearch(String searchText) {
		return this.consumableService.loadConsumablesBeginningWith(searchText).stream()
				.map(ConsumableDTO::name)
				.collect(Collectors.toList());
	}

	@Override
	public void askUserFoodQuantity(String food) {
		logger.info("Asking user for food quantity for food: {}", food);
		Stage popup = new Stage();
		this.foodPopupController.show(popup);
		this.foodPopupController.setFood(food);
		this.foodPopupController.setFoodServing(this.getFoodServingQuantity(food));
		this.foodPopupController.setFoodUnit(this.getFoodUnit(food).toString());
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.showAndWait();
	}

	@Override
	public void onEntry(String food, double value) {
		FoodDTO foodDTO = new FoodDTO(food, value, this.getFoodUnit(food));
		if (isAddFood) {
			((AddFoodViewController) this.viewController).addChosenFood(foodDTO);
		} else {
			((MakeMealViewController) this.viewController).addChosenFood(foodDTO);
		}
	}

	public void setDefaultRecipe(RecipeDTO meal) {
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
