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
package ulb.views.templates;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Provides a template for fields in the application's user interface.
 * It contains methods for setting images, default settings, label text, and toggling modes.
 */
public abstract class AbstractFieldTemplate extends AnchorPane {
	protected Image pen;
	protected Image check;

	@FXML protected Label infoUser;
	@FXML protected Button editButton;
	protected boolean mode = true;

	public void setImages(Image pen, Image check) {
		this.pen = pen;
		this.check = check;
	}

	protected void setDefault(
			Control field) { // common parents of TextField and DatePicker need checking of possible
		// issues
		infoUser.setVisible(true);
		field.setVisible(false);
		if (pen == null || check == null) throw new NullPointerException("Images not set");
		ImageView penView = new ImageView(pen);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(1);
		penView.setEffect(colorAdjust);
		editButton.setGraphic(penView);
	}

	public abstract void setDefault();

	public void setLabelText(String text) { // TODO : change to protected if possible
		infoUser.setText(text);
	}

	@FXML
	public void toggleMode() {
		if (this.mode) {
			this.setEditMode(); // from done to edit
		} else {
			this.setDoneMode(); // from edit to done
		}
	}

	protected void setEditMode() {
		editButton.setGraphic(new ImageView(check));
		infoUser.setVisible(false);
		mode = false;
	}

	protected void setDoneMode() {
		ImageView penView = new ImageView(pen);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(1);
		penView.setEffect(colorAdjust);
		editButton.setGraphic(penView);
		infoUser.setVisible(true);
		mode = true;
	}

	public String getText() {
		return infoUser.getText();
	}
}
