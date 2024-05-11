package ulb.controllers;

import javafx.stage.Stage;
import ulb.models.FoodLoader;
import ulb.views.FoodPopupViewController;

public class FoodPopupController extends AppController implements FoodPopupViewController.Listener {
    private final FoodLoader foodLoader = new FoodLoader();
    private final Listener listener;

    public FoodPopupController(Stage stage, FoodPopupController.Listener listener) {
        this.stage = stage;
        this.listener = listener;
        this.loadView("/ulb/views/Food_popup.fxml");
        this.stage.setTitle("Quantité de nourriture");
    }

    public void setFood(String food) {
        ((FoodPopupViewController) this.viewController).setFood(food);
    }

    public void setFoodServing(String serving) {
        ((FoodPopupViewController) this.viewController).setFoodServing(serving);
    }

    public void setFoodUnit(String unit) {
        ((FoodPopupViewController) this.viewController).setFoodUnit(unit);
    }

    @Override
    public int getServingQuantityValue(String food) {
        return this.foodLoader.getFoodByName(food).extractServingQuantityValue();
    }

    @Override
    public void onBack() {
        this.stage.hide();
    }

    @Override
    public void onEntry(double value) {
        this.stage.hide();
        this.listener.onEntry(((FoodPopupViewController) this.viewController).getFood(), value);
    }

    public interface Listener {
        void onEntry(String food, double value);
    }
}
