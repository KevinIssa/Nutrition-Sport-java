package ulb.controllers;

import javafx.stage.Stage;
import ulb.dtos.FoodDTO;
import ulb.dtos.MealDTO;
import ulb.views.MakeMealViewController;

import java.util.List;

public class EditMealController implements MakeMealViewController.Listener {
    private final Stage stage;
    private final MakeMealViewController makeMealViewController;
    private final Listener listener;

    public EditMealController(Stage stage, EditMealController.Listener listener) {
        this.stage = stage;
        this.makeMealViewController = new MakeMealViewController();
        this.makeMealViewController.setListener(listener);
        this.listener = listener;
    }

    public void hide(){
        this.stage.hide();
    }

    public void show(MealDTO meal) {
        this.stage.show();
        this.makeMealViewController.setMeal(meal);
    }

    @Override
    public void askUserFoodQuantity(String searchText) {
        //TODO Implement

    }

    @Override
    public List<String> getUserSearch(String searchText) {
        //TODO Implement

        return List.of();
    }

    @Override
    public double getCaloriesConsumed(String food, double quantity) {
        //TODO Implement

        return 0;
    }

    @Override
    public String getFoodUnit(String food) {
        //TODO Implement
        return "";
    }

    @Override
    public void returnHome() {
        //TODO Implement

    }

    @Override
    public void saveMeal(String mealName, List<FoodDTO> foodsList, int personAmount) {
        //TODO Implement
        this.returnHome();
    }

    @Override
    public void changeMode() {
        //TODO Implement

    }

    public interface Listener {
        //TODO Implement

    }
}
