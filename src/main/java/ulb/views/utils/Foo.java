package ulb.views.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Foo extends FooAbstract { //TODO : Rename this class "this class is a container for the textfield, label and button"
	public TextField infoFieldUser;
	public Foo(TextField infoFieldUser, Label label, Button button) {
		super(label, button);
		this.infoFieldUser = infoFieldUser;

	}

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
