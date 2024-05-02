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
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberField extends TextField {
	private static final Logger logger = LoggerFactory.getLogger(NumberField.class);

	public NumberField() {
		super();
		this.addEventFilter(
				KeyEvent.KEY_TYPED,
				event -> {
					if (!isInteger(event.getCharacter())) {
						event.consume();
					}
				});
	}

	public void setText(int number) {
		String text = String.valueOf(number);
		this.setText(text);
	}

	private boolean isInteger(String text) {
		if (text == null) {
			logger.info("text is null");
			return false;
		}
		// if the whole text is a number without any other characters nor empty
		return text.matches("\\d+");
	}

	public int getValue() throws NumberFormatException {
		return Integer.parseInt(this.getText());
	}

	public void setValue(String text) {
		logger.info("Setting value");
		if (this.isInteger(text)) {
			super.setText(text);
		}
	}

	public void setValue(int number) {
		this.setText(number);
	}
}
