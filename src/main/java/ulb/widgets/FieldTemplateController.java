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

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FieldTemplateController
		extends AbstractFieldTemplate { // TODO : Rename this class "this class is a container for
	// the textfield, label and button"
	@FXML public TextField infoFieldUser;

	@Override
	public void setDefault() {
		super.setDefault(infoFieldUser);
	}

	@Override
	protected void setEditMode() {
		super.setEditMode();
		infoFieldUser.setVisible(true);
		infoFieldUser.setText(infoUser.getText());
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		infoFieldUser.setVisible(false);
		infoUser.setText(infoFieldUser.getText());
	}
}
