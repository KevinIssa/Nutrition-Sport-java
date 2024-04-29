package ulb.views;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ulb.widgets.NumberField;
import ulb.widgets.Search;
import ulb.widgets.FoodBox;
import java.util.List;
import java.util.ResourceBundle;

public class MakeMealViewController implements ViewController, Search.Listener {
    @FXML private TextField mealName;
    @FXML private TextField personAmount;
    @FXML private Search searchController;
    @FXML private ListView<FoodBox> chosenFoodList;
    @FXML private Label calorieLabel;
    private double totalCalories = 0;
    private double totalGrams = 0;
    private MakeMealViewController.Listener listener;
    private NumberField personAmountNumber;
    private static final Logger logger = LoggerFactory.getLogger(MakeMealViewController.class);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchController.setListener(this);
        this.personAmountNumber = new NumberField(this.personAmount);
    }

    /**
     * This method adds the chosen food to the list when the user clicks on it.
     */
    @Override
    public void onClick(String searchText) {
        if (searchText == null) {
            return;
        }
        this.listener.askUserFoodQuantity(searchText);
    }

    @Override
    public ObservableList<String> getContent(String searchText) {
        return FXCollections.observableArrayList(this.listener.getUserSearch(searchText));
    }

    @FXML
    public void consumeFoods(){
        this.listener.changeMode();
    }

    @FXML
    public void cleanFoodList() {
        this.chosenFoodList.getItems().clear();
        this.totalCalories = 0;
        this.calorieLabel.setText("0");
    }

    private void addFoodBox(String food, double quantity, double calories, String foodUnit) {
        Button deleteFoodButton = new Button("X");
        deleteFoodButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        FoodBox foodBox = new FoodBox(deleteFoodButton, food, calories, quantity, foodUnit);
        deleteFoodButton.setOnAction(e -> this.deleteChosenFood(foodBox, calories, quantity));
        chosenFoodList.getItems().add(foodBox);
    }

    /**
     * This method adds the chosen food to the list.
     * It gets the serving type of the food, and updates the food item box with the food, calories, quantity, serving type, and value.
     * @param food The chosen food.
     * @param quantity The quantity (in grams) of the food.
     */
    public void addChosenFood(String food, double quantity) {
        double calories = listener.getCaloriesConsumed(food, quantity);
        // Round to 2 decimals
        calories = Double.parseDouble(String.format("%.2f", calories));
        quantity = Double.parseDouble(String.format("%.2f", quantity));
        this.totalCalories += calories;
        this.totalGrams += quantity;
        this.calorieLabel.setText(String.format("%.2f", this.totalCalories));
        String foodUnit = listener.getFoodUnit(food);

        this.addFoodBox(food, quantity, calories, foodUnit);
    }

    private void deleteChosenFood(FoodBox foodBox, double calories, double quantity) {
        this.chosenFoodList.getItems().remove(foodBox);
        this.totalCalories -= calories;
        this.totalGrams -= quantity;
        this.calorieLabel.setText(Double.toString(this.totalCalories));
    }


    @Override
    public void setListener(Object listener) {
        if (listener == null) {
            logger.error("Listener is null");
            System.exit(1);
        }
        this.listener = (Listener) listener;
    }

    public void returnHome() {
        this.listener.returnHome();
    }

    public void saveMeal() {
        double mealCalories = this.totalCalories/this.personAmountNumber.getValue();
        double mealGrams = this.totalGrams/this.personAmountNumber.getValue();
        this.listener.saveMeal(this.mealName.getText(), mealCalories, mealGrams);
        this.cleanFoodList();
    }

    public interface Listener{
        void askUserFoodQuantity(String searchText);

        List<String> getUserSearch(String searchText);

        double getCaloriesConsumed(String food, double quantity);

        String getFoodUnit(String food);

        void returnHome();

        void saveMeal(String mealName, double mealCalories, double mealGrams);

        void changeMode();
    }
}
