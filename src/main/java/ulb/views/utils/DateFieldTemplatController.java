package ulb.views.utils;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	public String getText(){
		return getFormattedDate().toString();
	}
}
