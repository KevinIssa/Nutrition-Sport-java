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
import ulb.dtos.ActivityDTO;

public class HistoryBox extends HBox {
	private static final String IMG_PATH = "/ulb/images/";
	private static final String[] ICONS = {
		"history_img/calendar.png",
		"history_img/chronometer.png",
		"history_img/calories.png",
		"trash.png",
		"pen.png"
	};

	private final ActivityDTO activity;

	public HistoryBox(ActivityDTO activity, Button deleteButton, Button editButton) {
		this.activity = activity;
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(20);
		this.setIcons();
		this.setLabels();
		this.setButtonInHBox(deleteButton, editButton);
	}

	private void setIcons() {
		this.getChildren()
				.addAll(
						createImageView("sport_img/" + this.activity.sport() + ".png"),
						createImageView("intensity_img/" + this.activity.intensity() + ".png"));
	}

	private void setLabels() {
		this.getChildren().add(2, new Label(this.dateToString(this.activity.date())));
		this.getChildren().add(3, new Label(this.durationToString(this.activity.duration())));
		this.getChildren().add(4, new Label(this.activity.burnedCalories() + " kcal"));
	}

	private void setButtonInHBox(Button deleteButton, Button editButton) {
		deleteButton.setGraphic(createImageView(ICONS[3]));
		editButton.setGraphic(createImageView(ICONS[4]));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		this.getChildren().addAll(spacer, editButton, deleteButton);
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

	public String durationToString(int duration) {
		int hours = duration / 60;
		int minutes = duration % 60;
		if (hours == 0) {
			return minutes + "m";
		}
		if (minutes == 0) {
			return hours + "h";
		}
		return hours + "h" + minutes + "m";
	}

	public String dateToString(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
		return date.format(formatter);
	}

	public ActivityDTO getActivity() {
		return this.activity;
	}
}
