package ulb.views;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.controllers.MealDetailsController;
import ulb.models.Food;
import ulb.models.Meal;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MealDetailsViewController implements ViewController {

    private static final Logger logger = LoggerFactory.getLogger(MealDetailsViewController.class);
    public Label mealName;
    public Label Calories;
    public ListView foodList;

    private MealDetailsViewController.Listener listener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(listener);
    }

    public void setListener(Object listener) {
        if (listener == null) {
            logger.error("Listener is null");
            System.exit(1);
        }
        this.listener = (MealDetailsViewController.Listener) listener;
    }
    public void setMeal(Meal meal){
        mealName.setText(meal.getName());
        double calorie = 0;
        for (Map.Entry<Food, Double> food : meal.getIngredients()){
            calorie += this.listener.getCaloriesConsumed(food.getKey(), food.getValue());
            addMealBox(food);
        }
        Calories.setText(Double.toString(calorie));

    }
    private void addMealBox(Map.Entry<Food, Double> food) {
        HBox mealHBox = createMealHBox(food);
        foodList.getItems().add(mealHBox);
    }

    private HBox createMealHBox(Map.Entry<Food, Double> food) {
        HBox hbox = createHBox();
        setTextInHBox(food, hbox);
        return hbox;
    }

    private static HBox createHBox() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);
        return hbox;
    }

    private void setTextInHBox(Map.Entry<Food, Double> food, HBox hbox) {
        Label LabelMealName = createLabel(food.getKey().getName(), 100);
        Label LabelMealQuantity = createLabel("quantite: " + food.getValue().toString(), 100);
        hbox.getChildren().add(0, LabelMealName);
        hbox.getChildren().add(1, LabelMealQuantity);
    }

    private Label createLabel(String text, int width) {
        Label label = new Label(text);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        return label;
    }
    @FXML
    public void returnHome(){
        this.listener.returnHome();
    }
    public interface Listener {

        void returnHome();
        double getCaloriesConsumed(Food food, double quantity);
    }
}
