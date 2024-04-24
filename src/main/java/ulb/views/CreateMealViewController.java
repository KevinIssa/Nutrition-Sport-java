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
package ulb.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ulb.widgets.FoodBox;
import ulb.widgets.Search;

public class CreateMealViewController implements ViewController {
	@FXML private Search search;
	@FXML private ListView<FoodBox> chosenFoodView;
	@FXML private TextField mealName;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	@Override
	public void setListener(Object listener) {}
}
