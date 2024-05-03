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
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.Meal;

public class MealRecipeViewController implements ViewController {

	private static final Logger logger = LoggerFactory.getLogger(MealRecipeViewController.class);
	@FXML ListView mealList;

	private MealRecipeViewController.Listener listener;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println(listener);
	}

	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (MealRecipeViewController.Listener) listener;
		showRecipes();
	}

	private void showRecipes() {
		List<Meal> meals = this.loadRecipes();
		for (Meal meal : meals) {
			this.addMealBox(meal);
		}
	}

	private List<Meal> loadRecipes() {
		return this.listener.loadRecipes();
	}

	private void addMealBox(Meal meal) {
		HBox mealHBox = createMealHBox(meal);
		mealList.getItems().add(mealHBox);
	}

	private HBox createMealHBox(Meal meal) {
		HBox hbox = createHBox();
		setTextInHBox(meal, hbox);
		setButtonInHBox(meal, hbox);
		return hbox;
	}

	private static HBox createHBox() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		return hbox;
	}

	private void setTextInHBox(Meal meal, HBox hbox) {
		Label LabelMealName = createLabel(meal.getName(), 100);
		hbox.getChildren().add(0, LabelMealName);
	}

	private Label createLabel(String text, int width) {
		Label label = new Label(text);
		label.setMinWidth(width);
		label.setMaxWidth(width);
		return label;
	}

	private void setButtonInHBox(Meal meal, HBox hbox) {
		ImageView imageDelete = createImageView("/ulb/images/trash.png", 30, 30);
		ImageView imageEdit = createImageView("/ulb/images/pen.png", 30, 30);
		ImageView imageCheck = createImageView("/ulb/images/search_icon.png", 30, 30);
		Button deleteButton = new Button("");
		Button editButton = new Button("");
		Button checkButton = new Button("");
		deleteButton.setGraphic(imageDelete);
		editButton.setGraphic(imageEdit);
		checkButton.setGraphic(imageCheck);
		deleteButton.setOnAction(e -> this.deleteMeal(meal, hbox));
		editButton.setOnAction(e -> this.editMeal(meal, hbox));
		checkButton.setOnAction(e -> this.listener.checkMeal(meal));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		hbox.getChildren().addAll(spacer, checkButton, editButton, deleteButton);
	}

	private ImageView createImageView(String imagePath, int width, int height) {
		URL path = getClass().getResource(imagePath);
		assert path != null;
		Image image = new Image(path.toString(), width, height, false, false);
		return new ImageView(image);
	}

	private void deleteMeal(Meal meal, HBox hbox) {

		this.listener.deleteMeal(meal);
		this.mealList.getItems().remove(hbox);
	}

	private void editMeal(Meal meal, HBox hbox) {
		this.deleteMeal(meal, hbox);
		this.listener.editMeal(meal);
		this.mealList.getItems().clear();
		this.showRecipes();
	}

	@FXML
	private void returnHome() {
		this.listener.returnHome();
	}

	public interface Listener {
		List<Meal> loadRecipes();

		void editMeal(Meal meal);

		void deleteMeal(Meal meal);

		void checkMeal(Meal meal);

		void returnHome();
	}
}
