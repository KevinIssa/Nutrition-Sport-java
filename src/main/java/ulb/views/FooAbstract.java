package ulb.views;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class FooAbstract {
	static final Image pen = new Image("/ulb/images/pen.png", 15, 15, true, true);
	static final Image check = new Image("/ulb/images/check.png", 15, 15, true, true);

	protected Label label;
	protected Button button;

	protected boolean mode = false; // folse is done, true is edit
	protected FooAbstract(Label label, Button button) {
		this.label = label;
		this.button = button;
	}
	protected void setDefault(Control field){ // common parents of TextField and DatePicker need checking of possible issues
		label.setVisible(true);
		field.setVisible(false);
		button.setGraphic(new ImageView(pen));
	}

	abstract void setDefault();
	void setLabelText(String text){
		label.setText(text);
	}
	void toggleMode(){
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
		datePicker.setValue(LocalDate.parse(label.getText(), DateTimeFormatter.ofPattern("yyyy-MM-d")));
	}

	@Override
	protected void setDoneMode() {
		super.setDoneMode();
		datePicker.setVisible(false);
		label.setText(datePicker.getValue().toString());
	}
}