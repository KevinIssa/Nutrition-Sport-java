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

import java.io.IOException;
import java.net.URL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import ulb.dtos.RecipeDTO;

public class MealBox extends HBox {
	private static final String TRASH_IMAGE = "/ulb/images/trash.png";
	private static final String PEN_IMAGE = "/ulb/images/pen.png";
	private static final String SEARCH_IMAGE = "/ulb/images/search_icon.png";

	private RecipeDTO food;

	public MealBox(RecipeDTO meal, Button searchButton, Button deleteButton, Button editButton)
			throws IOException {
		this.food = meal;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.getChildren().addFirst(new Label(this.food.name()));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		this.setUpButton(searchButton, SEARCH_IMAGE);
		this.setUpButton(editButton, PEN_IMAGE);
		this.setUpButton(deleteButton, TRASH_IMAGE);
		this.getChildren().addAll(spacer, searchButton, editButton, deleteButton);
	}

	private void setUpButton(Button button, String imagePath) throws IOException {
		URL path = getClass().getResource(imagePath);
		if (path == null) {
			throw new IOException("Image not found: " + imagePath);
		}
		button.setGraphic(new ImageView(new Image(path.toString(), 30, 30, false, false)));
	}

	public RecipeDTO getMealDTO() {
		return this.food;
	}
}
