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

import java.util.List;
import org.junit.Test;

public class TestFoodLoader {

	@Test
	public void testFoodLoader() {
		FoodLoader foodLoader = new FoodLoader("src/main/resources/food.json");
	}

	@Test
	public void testGetFoods() {
		FoodLoader foodLoader = new FoodLoader("src/main/resources/food.json");
		List<Food> foods = foodLoader.getFoodsSuggestion("apple");
		for (Food food : foods) {
			assert (food.getName().toLowerCase().startsWith("apple"));
		}
	}
}
