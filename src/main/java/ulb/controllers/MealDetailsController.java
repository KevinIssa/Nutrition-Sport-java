package ulb.controllers;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.Food;
import ulb.models.Meal;
import ulb.views.MealDetailsViewController;

public class MealDetailsController extends AppController
        implements MealDetailsViewController.Listener{
    private static final Logger logger = LoggerFactory.getLogger(MealDetailsController.class);
    private final MealDetailsController.Listener listener;
    public MealDetailsController(MealDetailsController.Listener listener) {
        logger.info("Initializing MealDetailsController");
        this.listener = listener;
    }

    @Override
    public void show(Stage stage) {
        logger.info("Showing MealDetailsView");
        this.loadView("/ulb/views/MealDetails.fxml", stage);
        this.viewController.setListener(this);
    }
    public void setMeal(Meal meal){
        ((MealDetailsViewController)this.viewController).setMeal(meal);
    }
    @Override
    public double getCaloriesConsumed(Food food, double quantity) {
        return food.getCaloriesConsumedByUnit(quantity);
    }
    @Override
    public void returnHome() {
        this.listener.returnHome();
    }

    public interface Listener {

        void returnHome();
    }
}
