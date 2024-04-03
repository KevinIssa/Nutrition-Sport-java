package ulb.views.utils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class FooAbstract implements Initializable {
	protected Image pen;
	protected Image check;

	@FXML protected Label infoUser;
	@FXML protected Button editButton;
	protected boolean mode = false; // folse is done, true is edit

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDefault();
	}

	protected FooAbstract(Label infoUser, Button editButton) {
		this.infoUser = infoUser;
		this.editButton = editButton;
	}


	public void setImages(Image pen, Image check){
		this.pen = pen;
		this.check = check;
	}
	protected void setDefault(Control field){ // common parents of TextField and DatePicker need checking of possible issues
		infoUser.setVisible(true);
		field.setVisible(false);
		if (pen == null || check == null) throw new NullPointerException("Images not set");
		editButton.setGraphic(new ImageView(pen));
	}

	public abstract void setDefault();
	public void setLabelText(String text){ // TODO : change to protected if possible
		infoUser.setText(text);
	}
	@FXML
	public void toggleMode(){
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
		mode = true;
    }

    protected void setDoneMode() {
		editButton.setText("Edit");
		editButton.setGraphic(new ImageView(pen));
		infoUser.setVisible(true);
		mode = false;
	}

	public String getText(){
		return infoUser.getText();
	}
}
