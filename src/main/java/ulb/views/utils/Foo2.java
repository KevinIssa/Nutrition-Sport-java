package ulb.views.utils;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Foo2 extends FooAbstract {

	protected DatePicker birthdateFieldInfo;

	public Foo2(DatePicker birthdateFieldInfo, Label label, Button button) {
		super(label, button);
		this.birthdateFieldInfo = birthdateFieldInfo;
	}

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
