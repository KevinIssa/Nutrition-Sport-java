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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.widgets.DoubleField;

public class FoodPopupViewController implements ViewController {
	@FXML TextField gramme;
	@FXML TextField serving;
	@FXML Label servinglabel;
	@FXML Label mainLabel;
	@FXML Label unitLabel;
	private DoubleField servingField;
	private DoubleField grammeField;
	private Listener listener;
	private static final Logger logger = LoggerFactory.getLogger(FoodPopupViewController.class);
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

	public void setFoodServing(String foodServing) {
		this.servinglabel.setText(foodServing.replace("serving", "portion"));
	}

	public void setFoodUnit(String foodUnit) {
		if (foodUnit.equals("g")) {
			this.unitLabel.setText("gramme");
		} else if (foodUnit.equals("ml")) {
			this.unitLabel.setText("milli-litre");
		} else {
			this.unitLabel.setText("gramme et millilitre");
		}
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
		value = BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN).doubleValue();
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

		double getServingQuantityValue(String food);
	}
}
