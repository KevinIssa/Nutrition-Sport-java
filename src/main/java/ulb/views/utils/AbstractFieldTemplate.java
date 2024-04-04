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
package ulb.views.utils;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
		editButton.setGraphic(new ImageView(pen));
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
		editButton.setText("Done");
		editButton.setGraphic(new ImageView(check));
		infoUser.setVisible(false);
		mode = false;
	}

	protected void setDoneMode() {
		editButton.setText("Edit");
		editButton.setGraphic(new ImageView(pen));
		infoUser.setVisible(true);
		mode = true;
	}

	public String getText() {
		return infoUser.getText();
	}
}
