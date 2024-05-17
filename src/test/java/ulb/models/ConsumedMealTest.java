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

class ConsumedMealTest {

	private ConsumedMeal consumedMeal;

	@BeforeEach
	void setup() {
		consumedMeal = new ConsumedMeal();
	}

	@Test
	void addConsumedFoodShouldAddFoodToList() {
		consumedMeal.addConsumedFood("Banana", 100, 100, "g");
		List<ConsumedFood> consumedFoods = consumedMeal.getConsumedFoods();
		assertEquals(1, consumedFoods.size());
		assertEquals("Banana", consumedFoods.get(0).getName());
	}

	@Test
	void addConsumedFoodAddsFoodToMeal() {
		ConsumedMeal meal = new ConsumedMeal();
		ConsumedFood food = new ConsumedFood("Apple", 52, 100, "g");
		meal.addConsumedFood(food);
		assertEquals(1, meal.getConsumedFoods().size());
		assertEquals(food, meal.getConsumedFoods().get(0));
	}

	@Test
	void addConsumedFoodHandlesNull() {
		ConsumedMeal meal = new ConsumedMeal();
		meal.addConsumedFood(null);
		assertEquals(1, meal.getConsumedFoods().size());
		assertNull(meal.getConsumedFoods().get(0));
	}

	@Test
	void getCaloriesConsumedShouldReturnTotalCalories() {
		consumedMeal.addConsumedFood("Banana", 100, 100, "g");
		consumedMeal.addConsumedFood("Apple", 200, 100, "g");
		assertEquals(300, consumedMeal.getCaloriesConsumed());
	}

	@Test
	void getConsumedFoodsReturnsEmptyListWhenNoFoodsAdded() {
		ConsumedMeal meal = new ConsumedMeal();
		assertTrue(meal.getConsumedFoods().isEmpty());
	}

	@Test
	void getConsumedFoodsReturnsListOfFoodsWhenFoodsAdded() {
		ConsumedMeal meal = new ConsumedMeal();
		ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
		ConsumedFood food2 = new ConsumedFood("Banana", 105, 100, "g");
		meal.addConsumedFood(food1);
		meal.addConsumedFood(food2);
		List<ConsumedFood> foods = meal.getConsumedFoods();
		assertEquals(2, foods.size());
		assertTrue(foods.contains(food1));
		assertTrue(foods.contains(food2));
	}

	@Test
	void toStringReturnsCorrectFormatForValidConsumedFood() {
		ConsumedFood food = new ConsumedFood("Apple", 52, 100, "g");
		String expected = "ConsumedFood{name='Apple', quantity=100.0, calories=52.0, unit='g'}";
		assertEquals(expected, food.toString());
	}

	@Test
	void toStringHandlesNullValues() {
		ConsumedFood food = new ConsumedFood(null, 0, 0, null);
		String expected = "ConsumedFood{name='null', quantity=0.0, calories=0.0, unit='null'}";
		assertEquals(expected, food.toString());
	}
}
