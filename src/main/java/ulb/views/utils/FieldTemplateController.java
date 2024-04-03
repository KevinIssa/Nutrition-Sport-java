package ulb.views.utils;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FieldTemplateController extends AbstractFieldTemplate { //TODO : Rename this class "this class is a container for the textfield, label and button"
	@FXML public TextField infoFieldUser;


	@Override
	public void setDefault() {
		super.setDefault(infoFieldUser);
	}
	@Override
	protected void setEditMode() {
		super.setEditMode();
		infoFieldUser.setVisible(true);
		System.out.println("Edit mode");
		infoFieldUser.setText(infoUser.getText());
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		System.out.println("Done mode");
		infoFieldUser.setVisible(false);
		infoUser.setText(infoFieldUser.getText());
	}

}
