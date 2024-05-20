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
package ulb.widgets;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import ulb.dtos.ConsumedFoodDTO;

public class FoodHistoryBox extends HBox {

	private final ConsumedFoodDTO food;
	private final LocalDateTime date;
	private final Button deleteFoodButton;

	public FoodHistoryBox(ConsumedFoodDTO food, LocalDateTime date) {
		this.food = food;
		this.date = date;
		this.deleteFoodButton = new Button("");
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		setTextInHBox();
		setButtonInHBox();
	}

	private void setButtonInHBox() {
		ImageView imageDelete = createImageView("/ulb/images/trash.png", 30, 30);
		this.deleteFoodButton.setGraphic(imageDelete);
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		this.getChildren().addAll(spacer, deleteFoodButton);
	}

	private ImageView createImageView(String imagePath, int width, int height) {
		URL path = getClass().getResource(imagePath);
		assert path != null;
		Image image = new Image(path.toString(), width, height, false, false);
		return new ImageView(image);
	}

	private void setTextInHBox() {
		Label LabelMealName = createLabel(this.food.name(), 100);
		Label LabelQuantity = createLabel(this.food.quantity() + " " + this.food.unit(), 40);
		Label LabelDate =
				createLabel(
						this.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")), 120);
		Label LabelCalorie = createLabel(this.food.calories() + " kcal", 60);
		this.getChildren().add(0, LabelMealName);
		this.getChildren().add(1, LabelDate);
		this.getChildren().add(2, LabelQuantity);
		this.getChildren().add(3, LabelCalorie);
	}

	private Label createLabel(String text, int width) {
		Label label = new Label(text);
		label.setMinWidth(width);
		label.setMaxWidth(width);
		return label;
	}

	public Button getDeleteFoodButton() {
		return deleteFoodButton;
	}

	public ConsumedFoodDTO getFoodDTO() {
		return food;
	}

	public LocalDateTime getDate() {
		return date;
	}
}
