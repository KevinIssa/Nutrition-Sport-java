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
package ulb.models;

import java.util.ArrayList;
import java.util.List;

public class Meal implements Consumable {

	private List<Food> foods = new ArrayList<Food>();

	Meal() {}

	Meal(List<Food> foods) {
		this.foods = foods;
	}

	public void addFood(Food food) {
		foods.add(food);
	}

	@Override
	public double getCaloriesConsumedByGrams(int grams) {
		double totalCalories = 0;
		for (Food food : foods) {
			totalCalories += food.getCaloriesConsumedByGrams(grams);
		}
		return totalCalories;
	}
}
