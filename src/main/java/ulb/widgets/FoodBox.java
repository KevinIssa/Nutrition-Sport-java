package ulb.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodBox extends HBox {
    private final String food;
    private final String calories;
    private final String quantity;

    public FoodBox(String food, int calories, int quantity, String quantityUnit) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
        this.food = food;
        this.calories = "Calories : " + calories + " kcal";
        this.quantity = "Quantité : " + quantity + quantityUnit;
        this.getChildren().addAll(new Label(food),
                new Label(this.calories),
                new Label(this.quantity));
    }

    public List<String> getItems() {
        List<String> list = new ArrayList<>() {};
        list.add(this.food);
        list.add(this.calories);
        list.add(this.quantity);
        list = Collections.unmodifiableList(list);
        return list;
    }

}
