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

import java.io.File;

public class TestMeal {
	@Test
	public void testAddMeal() {
		Meal customMeal = new Meal("Omelette aux poivrons");
		Food egg = new Food("Egg", 97, "1 egg (60 g)");
		Food pepper = new Food("Pepper", 27, "1 pepper (75 g)");
		customMeal.addIngredient(egg, 3);
		customMeal.addIngredient(pepper, 1);
		customMeal.save();
		Meal loadedMeal = Meal.load("Custom Meals.json");
		Assert.assertEquals(customMeal, loadedMeal);

		// Clean up
		File file = new File("Custom Meals.json");
		file.delete();
	}
}
