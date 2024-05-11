package ulb.controllers;

import ulb.dtos.FoodDTO;
import ulb.views.MakeMealViewController;

import java.util.List;

public class MakeMealController implements MakeMealViewController.Listener {


    @Override
    public void askUserFoodQuantity(String searchText) {

    }

    @Override
    public List<String> getUserSearch(String searchText) {
        return List.of();
    }

    @Override
    public double getCaloriesConsumed(String food, double quantity) {
        return 0;
    }

    @Override
    public String getFoodUnit(String food) {
        return "";
    }

    @Override
    public void returnHome() {

    }

    @Override
    public void saveMeal(String mealName, List<FoodDTO> foodsList, int personAmount) {

    }

    @Override
    public void changeMode() {

    }
}
