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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FoodBox extends HBox {
	private final String food;
	private final String calories;
	private final double caloriesValue;
	private final String quantity;
	private final double quantityValue;
	private final String unit;

	public FoodBox(
			Button button, String food, double calories, double quantity, String quantityUnit) {
		super();
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		this.food = food;
		this.calories = "Calories : " + calories + " kcal";
		this.quantity = "Quantité : " + quantity + quantityUnit;
		this.getChildren()
				.addAll(new Label(food), new Label(this.calories + "\n" + this.quantity), button);
		this.caloriesValue = calories;
		this.quantityValue = quantity;
		this.unit = quantityUnit;
	}

	public List<String> getItems() {
		List<String> list = new ArrayList<>() {};
		list.add(this.food);
		list.add(String.valueOf(this.caloriesValue));
		list.add(String.valueOf(this.quantityValue));
		list.add(this.unit);
		return Collections.unmodifiableList(list);
	}
}
