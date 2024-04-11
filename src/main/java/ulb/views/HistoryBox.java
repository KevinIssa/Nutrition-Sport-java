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
	private static final String CALENDAR_IMG_PATH = "/ulb/images/history_img/calendar.png";
	public static final String CHRONOMETER_IMG_PATH = "/ulb/images/history_img/chronometer.png";
	public static final String CALORIES_IMG_PATH = "/ulb/images/history_img/calories.png";
	public static final String SPORT_IMGS_PATH = "/ulb/images/sport_img/";
	public static final String INTENSITY_IMGS_PATH = "/ulb/images/intensity_img/";
	public static final String TRASH_IMG_PATH = "/ulb/images/trash.png";

	public HistoryBox(ActivityDTO activity, Button button) {
		this.settings();
		this.fill(activity, button);
	}

	private void settings() {
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
	}

	private void fill(ActivityDTO activity, Button button) {
		this.setIcons(activity);
		this.setLabels(activity);
		this.setButtonInHBox(button);
	}

	private void setIcons(ActivityDTO activity) {
		this.getChildren()
				.addAll(
						this.createIntensityImageView(activity),
						this.createSportImageView(activity),
						this.createImageView(CALENDAR_IMG_PATH),
						this.createImageView(CHRONOMETER_IMG_PATH),
						this.createImageView(CALORIES_IMG_PATH));
	}

	private void setLabels(ActivityDTO activity) {
		this.getChildren().add(3, new Label(activity.date));
		this.getChildren().add(5, new Label(activity.duration));
		this.getChildren().add(7, new Label(activity.burnedCalories + " kcal"));
	}

	private void setButtonInHBox(Button button) {
		ImageView imageDelete = createImageView(TRASH_IMG_PATH);
		button.setGraphic(imageDelete);
		Region spacer = new Region();
		setHgrow(spacer, Priority.ALWAYS);
		this.getChildren().addAll(spacer, button);
	}

	private String getImagePathForSport(String sport) {
		return SPORT_IMGS_PATH + sport + ".png";
	}

	private String getIntensityPathForSport(String intensity) {
		return INTENSITY_IMGS_PATH + intensity + ".png";
	}

	private ImageView createIntensityImageView(ActivityDTO activity) {
		String intensityStringPath = getIntensityPathForSport(activity.intensity);
		return createImageView(intensityStringPath);
	}

	private ImageView createSportImageView(ActivityDTO activity) {
		String imagePath = getImagePathForSport(activity.sport);
		return createImageView(imagePath);
	}

	private ImageView createImageView(String imagePath) {
		URL path = getClass().getResource(imagePath);
		Image image = new Image(path.toString(), 30, 30, false, false);
		return new ImageView(image);
	}
}
