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

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import ulb.controllers.dtos.ActivityDTO;

public class HistoryBox extends HBox {
	private static final String IMG_PATH = "/ulb/images/";
	private static final String[] ICONS = {
		"history_img/calendar.png",
		"history_img/chronometer.png",
		"history_img/calories.png",
		"trash.png"
	};

	public HistoryBox(ActivityDTO activity, Button button) {
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		this.fill(activity, button);
	}

	private void fill(ActivityDTO activity, Button button) {
		this.setIcons(activity);
		this.setLabels(activity);
		this.setButtonInHBox(button);
	}

	private void setIcons(ActivityDTO activity) {
		this.getChildren()
				.addAll(
						createImageView("sport_img/" + activity.sport + ".png"),
						createImageView("intensity_img/" + activity.intensity + ".png"),
						createImageView(ICONS[0]),
						createImageView(ICONS[1]),
						createImageView(ICONS[2]));
	}

	private void setLabels(ActivityDTO activity) {
		this.getChildren().add(3, new Label(activity.date));
		this.getChildren().add(5, new Label(activity.duration));
		this.getChildren().add(7, new Label(activity.burnedCalories + " kcal"));
	}

	private void setButtonInHBox(Button button) {
		button.setGraphic(createImageView(ICONS[3]));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		this.getChildren().addAll(spacer, button);
	}

	private ImageView createImageView(String imagePath) {
		Image image =
				new Image(
						getClass().getResource(IMG_PATH + imagePath).toString(),
						30,
						30,
						false,
						false);
		return new ImageView(image);
	}
}
