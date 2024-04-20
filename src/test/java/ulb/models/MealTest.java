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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MealTest {

	@Test
	public void testMeal() {
		Meal customMeal = createCustomMeal();
		Meal loadedMeal = Meal.load("meals/Omelette aux poivrons.json");
		assertEquals(customMeal, loadedMeal);
	}

	private Meal createCustomMeal() {
		Meal customMeal = new Meal("Omelette aux poivrons");
		customMeal.addIngredient(new Food("Egg", 97, 58, "1 egg (60 g)"), 60 * 3);
		customMeal.addIngredient(new Food("Pepper", 27, 20, "1 pepper (75 g)"), 75 * 1);
		customMeal.save();
		return customMeal;
	}
}
