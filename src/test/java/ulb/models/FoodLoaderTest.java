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

public class FoodLoaderTest {

	private FoodLoader foodLoader;

	@BeforeEach
	public void setup() {
		foodLoader = new FoodLoader();
	}

	@Test
	public void loadFoodsShouldNotReturnNull() {
		assertNotNull(foodLoader.getFoods());
	}

	@Test
	public void getFoodsSuggestionShouldReturnCorrectSuggestions() {
		List<Food> foods = foodLoader.getFoodsSuggestion("Ban");
		assertFalse(foods.isEmpty());
		assertTrue(foods.stream().allMatch(food -> food.getName().startsWith("Ban")));
	}

	@Test
	public void getFoodsSuggestionShouldReturnEmptyListForUnknownFood() {
		List<Food> foods = foodLoader.getFoodsSuggestion("UnknownFood");
		assertTrue(foods.isEmpty());
	}

	@Test
	public void getFoodByNameShouldReturnCorrectFood() {
		Food food = foodLoader.getFoodByName("7up");
		assertNotNull(food);
		assertEquals("7up", food.getName());
	}

	@Test
	public void getFoodByNameShouldReturnNullForUnknownFood() {
		Food food = foodLoader.getFoodByName("UnknownFood");
		assertNull(food);
	}
}
