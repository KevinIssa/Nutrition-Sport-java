package ulb.views.utils;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Foo2 extends FooAbstract {

	protected DatePicker datePicker;

	public Foo2(DatePicker datePicker, Label label, Button button) {
		super(label, button);
		this.datePicker = datePicker;
	}

	@Override
	public void setDefault() {
		super.setDefault(datePicker);
	}

	@Override
	protected void setEditMode() {
		super.setEditMode();
		datePicker.setVisible(true);
		datePicker.setValue(getFormattedDate());
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		datePicker.setVisible(false);
		label.setText(datePicker.getValue().toString());
	}

	private LocalDate getFormattedDate() {
		return LocalDate.parse(label.getText(), DateTimeFormatter.ofPattern("yyyy-MM-d"));
	}
	@Override
	public String getText(){
		return getFormattedDate().toString();
	}
}
