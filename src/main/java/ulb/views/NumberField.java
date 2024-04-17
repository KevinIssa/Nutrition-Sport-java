package ulb.views;

import javafx.scene.control.TextField;

public class NumberField {
    private final TextField textField;

    public NumberField(TextField textField) {
        this.textField = textField;
        this.textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setValue(int number){
        String text = String.valueOf(number);
        this.textField.setText(text);
    }

    public int getValue() {
        return Integer.parseInt(this.textField.getText());
    }
}
