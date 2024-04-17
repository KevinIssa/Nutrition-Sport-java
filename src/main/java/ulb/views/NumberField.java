/*
 * Ce projet est une application de santé et de bien-être développée dans le cadre du cours INFO-F-307 à l'ULB.
 *
 * Groupe : 06
 * Étudiants :
 * - Kevin ISSA
 * - Hamza CAEYMAN
 * - Alexandru MELNIC
 * - Ze-Xuan XU
 * - Bao TRAN
 * - Hà Uyên TRAN
 * - Hugo CHARELS
 * - Hodo SOULEIMAN AHMED
 * - Kevin VANDERVAEREN
 * - Arthur INSTALLÉ
 *
 * Date : 2024
 */
package ulb.views;

import javafx.scene.control.TextField;

public class NumberField {
	private final TextField textField;

	public NumberField(TextField textField) {
		this.textField = textField;
		this.textField
				.textProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							if (!newValue.matches("\\d*")) {
								textField.setText(newValue.replaceAll("[^\\d]", ""));
							}
						});
	}

	public void setValue(int number) {
		String text = String.valueOf(number);
		this.textField.setText(text);
	}

	public int getValue() {
		return Integer.parseInt(this.textField.getText());
	}
}
