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

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodLoaderTest {

	private FoodLoader foodLoader;

	@BeforeEach
	void setup() {
		foodLoader = new FoodLoader();
	}

	@Test
	void loadFoodsShouldNotReturnNull() {
		assertNotNull(foodLoader.getFoods());
	}

	@Test
	void getFoodsSuggestionShouldReturnCorrectSuggestions() {
		List<Food> foods = foodLoader.getFoodsSuggestion("Ban");
		assertFalse(foods.isEmpty());
		assertTrue(foods.stream().allMatch(food -> food.getName().startsWith("Ban")));
	}

	@Test
	void getFoodsSuggestionShouldReturnEmptyListForUnknownFood() {
		List<Food> foods = foodLoader.getFoodsSuggestion("UnknownFood");
		assertTrue(foods.isEmpty());
	}

	@Test
	void getFoodByNameShouldReturnCorrectFood() {
		Food food = foodLoader.getFoodByName("7up");
		assertNotNull(food);
		assertEquals("7up", food.getName());
	}

	@Test
	void getFoodByNameShouldReturnNullForUnknownFood() {
		Food food = foodLoader.getFoodByName("UnknownFood");
		assertNull(food);
	}
}
