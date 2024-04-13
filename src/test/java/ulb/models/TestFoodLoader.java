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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestFoodLoader {
	private FoodLoader foodLoader;

	@BeforeEach
	public void setup() {
		this.foodLoader = new FoodLoader();
	}

	@Test
	public void testFoodLoader() {
		assertNotNull(foodLoader);
	}

	@ParameterizedTest
	@ValueSource(strings = {"pomme", "la", "r"})
	public void testGetFoods(String foodName) {
		List<Food> foods = foodLoader.getFoodsSuggestion(foodName);
		foods.forEach(food -> assertTrue(food.getName().toLowerCase().startsWith(foodName)));
	}

	@ParameterizedTest
	@ValueSource(strings = {"pomme", "Macaronis au fromage", "Soupe aux nouilles"})
	public void testGetFoodByName(String foodName) {
		Food food = foodLoader.getFoodByName(foodName);
		assertNotNull(food);
		assertTrue(food.getName().equalsIgnoreCase(foodName));
	}
}
