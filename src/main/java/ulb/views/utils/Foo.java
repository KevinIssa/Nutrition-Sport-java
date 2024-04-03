package ulb.views.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Foo extends FooAbstract { //TODO : Rename this class "this class is a container for the textfield, label and button"
	public TextField txtField;
	public Foo(TextField txtField, Label label, Button button) {
		super(label, button);
		this.txtField = txtField;

	}

	@Override
	public void setDefault() {
		super.setDefault(txtField);
	}
	@Override
	protected void setEditMode() {
		super.setEditMode();
		txtField.setVisible(true);
		txtField.setText(label.getText());
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		txtField.setVisible(false);
		label.setText(txtField.getText());
	}

}
