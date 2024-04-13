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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

/**
 * The DateFieldTemplatController class extends the AbstractFieldTemplate class.
 * It is a controller for a date field in the application's user interface.
 * It overrides the setDefault, setEditMode, setDoneMode, and getText methods from the AbstractFieldTemplate class.
 */
public class DateFieldTemplatController extends AbstractFieldTemplate {

	@FXML protected DatePicker birthdateFieldInfo;

	@Override
	public void setDefault() {
		super.setDefault(birthdateFieldInfo);
	}

	@Override
	protected void setEditMode() {
		super.setEditMode();
		birthdateFieldInfo.setVisible(true);
		birthdateFieldInfo.setValue(getFormattedDate());
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		birthdateFieldInfo.setVisible(false);
		infoUser.setText(birthdateFieldInfo.getValue().toString());
	}

	private LocalDate getFormattedDate() {
		return LocalDate.parse(infoUser.getText(), DateTimeFormatter.ofPattern("yyyy-MM-d"));
	}

	@Override
	public String getText() {
		return getFormattedDate().toString();
	}
}
