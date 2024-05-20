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
package ulb.widgets;

import javafx.scene.control.TextField;

public class DoubleField {
	private final TextField textField;

	public DoubleField(TextField textField) {
		this.textField = textField;
		this.textField
				.textProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							if (!newValue.matches("\\d*[.,]?\\d{1,2}")) {
								textField.setText(newValue.replaceAll("[^\\d.,]", ""));
							}
						});
	}

	public void setValue(double number) {
		String text = String.valueOf(number);
		this.textField.setText(text);
	}

	public double getValue() throws NumberFormatException {
		if (this.textField.getText().isEmpty()) {
			throw new NumberFormatException("Valeur invalide");
		}
		String text = this.textField.getText().replace(",", ".");
		return Double.parseDouble(text);
	}
}
