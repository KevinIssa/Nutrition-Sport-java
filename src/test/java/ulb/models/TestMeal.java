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

import org.junit.Assert;
import org.junit.Test;

public class TestMeal {
	@Test
	public void testMeal() {
		Meal customMeal = new Meal("Omelette aux poivrons");
		Food egg = new Food("Egg", 97, 58, "1 egg (60 g)");
		Food pepper = new Food("Pepper", 27, 20, "1 pepper (75 g)");
		customMeal.addIngredient(egg, 3);
		customMeal.addIngredient(pepper, 1);
		customMeal.save();
		Meal loadedMeal = Meal.load("meals/Omelette aux poivrons.json");
		Assert.assertEquals(customMeal, loadedMeal);
	}
}
