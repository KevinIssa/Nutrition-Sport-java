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

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

public class TestFoodLoader {

	@Test
	public void testFoodLoader() {
		new FoodLoader("src/main/resources/food.json");
	}

	@Test
	public void testGetFoods() {
		List<Food> foods =
				new FoodLoader("src/main/resources/food.json").getFoodsSuggestion("apple");
		foods.forEach(food -> assertTrue(food.getName().toLowerCase().startsWith("apple")));
	}
}
