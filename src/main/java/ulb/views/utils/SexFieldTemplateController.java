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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class SexFieldTemplateController extends AbstractFieldTemplate {
	@FXML private RadioButton maleButton;
	@FXML private RadioButton femaleButton;

	@FXML private ToggleGroup sex;

	@Override
	public void setDefault() {
		super.setDefault(new RadioButton());
		maleButton.setVisible(false);
		femaleButton.setVisible(false);
	}

	@Override
	protected void setEditMode() {
		super.setEditMode();
		setButtonVisibility(true);
		setTargetButtonSelection();
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		setButtonVisibility(false);
		setInfoUserText();
	}

	private void setButtonVisibility(boolean visibility) {
		maleButton.setVisible(visibility);
		femaleButton.setVisible(visibility);
	}

	private void setTargetButtonSelection() {
		if (infoUser.getText().equals("♂")) {
			maleButton.setSelected(true);
			femaleButton.setSelected(false);
		} else {
			femaleButton.setSelected(true);
			maleButton.setSelected(false);
		}
	}

	private void setInfoUserText() {
		if (maleButton.isSelected()) {
			infoUser.setText("♂");
		} else {
			infoUser.setText("♀");
		}
	}
}
