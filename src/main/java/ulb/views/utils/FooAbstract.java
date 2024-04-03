package ulb.views.utils;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class FooAbstract {
	protected Image pen;
	protected Image check;

	protected Label label;
	protected Button button;

	protected boolean mode = false; // folse is done, true is edit
	protected FooAbstract(Label label, Button button) {
		this.label = label;
		this.button = button;
	}

	public void setImages(Image pen, Image check){
		this.pen = pen;
		this.check = check;
	}
	protected void setDefault(Control field){ // common parents of TextField and DatePicker need checking of possible issues
		label.setVisible(true);
		field.setVisible(false);
		if (pen == null || check == null) throw new NullPointerException("Images not set");
		button.setGraphic(new ImageView(pen));
	}

	public abstract void setDefault();
	public void setLabelText(String text){ // TODO : change to protected if possible
		label.setText(text);
	}
	public void toggleMode(){
		if (this.mode) {
			this.setEditMode(); // from done to edit
		} else {
			this.setDoneMode(); // from edit to done
		}
	}

    protected void setEditMode() {
        button.setText("Done");
		button.setGraphic(new ImageView(check));
		label.setVisible(false);
		mode = true;
    }

    protected void setDoneMode() {
		button.setText("Edit");
		button.setGraphic(new ImageView(pen));
		label.setVisible(true);
		mode = false;
	}

	public String getText(){
		return label.getText();
	}
}
