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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.views.ViewController;

public class FoodPopupController implements ViewController {
	@FXML TextField gramme;
	@FXML TextField serving;
	@FXML Label servinglabel;
	@FXML Label mainLabel;
	private DoubleField servingField;
	private DoubleField grammeField;
	private Listener listener;
	private static final Logger logger = LoggerFactory.getLogger(FoodPopupController.class);
	private String food;

	private double getFieldValue(DoubleField inputField) throws IllegalArgumentException {
		double value = inputField.getValue();
		if (value == 0) {
			throw new IllegalArgumentException("Quantité trop petite.");
		}
		return value;
	}

	private double getValue(DoubleField inputField) {
		try {
			return this.getFieldValue(inputField);
		} catch (IllegalArgumentException e) {
			showAlert("Valeur invalide", e.getMessage());
			this.serving.clear();
			this.gramme.clear();
			return 0;
		}
	}

	public void setFood(String food) {
		this.food = food;
		this.mainLabel.setText("Donnez la quantité de " + food);
	}

	public String getFood() {
		return this.food;
	}

	public void setFoodUnit(String foodUnit) {
		this.servinglabel.setText(foodUnit);
	}

	void disableIfEmpty(TextField field) {
		boolean isEmpty = field.getText().isEmpty();
		field.setDisable(isEmpty);
	}

	@FXML
	void disabilityHandler() {
		if (serving.getText().isEmpty() && gramme.getText().isEmpty()) {
			serving.setDisable(false);
			gramme.setDisable(false);
			return;
		}
		this.disableIfEmpty(serving);
		this.disableIfEmpty(gramme);
	}

	private void resetTextFields() {
		this.serving.setText("");
		this.serving.setDisable(false);
		this.gramme.setText("");
		this.gramme.setDisable(false);
	}

	@FXML
	void onEntry() {
		double value;
		if (gramme.getText().isEmpty()) {
			value =
					this.getValue(this.servingField)
							* this.listener.getServingQuantityValue(this.food);
		} else {
			value = this.getValue(this.grammeField);
		}
		if (value == 0) {
			this.listener.onBack();
			return;
		}
		value = Double.parseDouble(String.format("%.2f", value));
		resetTextFields();
		this.listener.onEntry(value);
	}

	@FXML
	void onBack() {
		this.listener.onBack();
	}

	@Override
	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			return;
		}
		this.listener = (Listener) listener;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.servingField = new DoubleField(this.serving);
		this.grammeField = new DoubleField(this.gramme);
	}

	public interface Listener {
		void onEntry(double value);

		void onBack();

		int getServingQuantityValue(String food);
	}
}
