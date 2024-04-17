package ulb.views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ulb.widgets.FoodBox;
import ulb.widgets.Search;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateMealViewController implements ViewController {
    @FXML private Search search;
    @FXML private ListView<FoodBox> chosenFoodView;
    @FXML private TextField mealName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setListener(Object listener) {

    }
}
