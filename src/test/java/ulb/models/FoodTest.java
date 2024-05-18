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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.enums.Unit;

class FoodTest {

	private Food food;

	@BeforeEach
	void setup() {
		food = new Food("Apple", 52, 52, "1 (182g)", Unit.GRAMS);
	}

	@Test
	void nameIsSetCorrectly() {
		assertEquals("Apple", food.getName());
	}

	@Test
	void caloriesPer100IsSetCorrectly() {
		assertEquals(52, food.getCaloriesPer100Unit());
	}

	@Test
	void caloriesPerServingIsSetCorrectly() {
		assertEquals(52, food.getCaloriesPerServing());
	}

	@Test
	void servingQuantityIsSetCorrectly() {
		assertEquals("1 (182g)", food.getServingQuantity());
	}

	@Test
	void nameCanBeChanged() {
		food.setName("Banana");
		assertEquals("Banana", food.getName());
	}

	@Test
	void caloriesPer100CanBeChanged() {
		food.setCaloriesPer100Unit(96);
		assertEquals(96, food.getCaloriesPer100Unit());
	}

	@Test
	void caloriesPerServingCanBeChanged() {
		food.setCaloriesPerServing(105);
		assertEquals(105, food.getCaloriesPerServing());
	}

	@Test
	void servingQuantityCanBeChanged() {
		food.setServingQuantity("1 (118g)");
		assertEquals("1 (118g)", food.getServingQuantity());
	}

	@Test
	void servingQuantityValueIsExtractedCorrectly() {
		assertEquals(182, food.extractServingQuantityValue());
	}

	@Test
	void caloriesConsumedByGramsIsCalculatedCorrectly() {
		assertEquals(94.64, food.getCaloriesConsumedByUnit(182));
	}

	@Test
	void caloriesConsumedByServingIsCalculatedCorrectly() {
		assertEquals(52, food.getCaloriesConsumedByServing(1));
	}

	@Test
	void equalsReturnsFalseForDifferentFood() {
		Food differentFood = new Food("Banana", 96, 105, "1 (118g)", Unit.GRAMS);
		assertNotEquals(food, differentFood);
	}

	@Test
	void equalsReturnsTrueForSameFood() {
		Food sameFood = new Food("Apple", 52, 52, "1 (182g)", Unit.GRAMS);
		assertEquals(food, sameFood);
	}

	@Test
	void defaultConstructorSetsDefaultValues() {
		Food defaultFood = new Food();
		assertNull(defaultFood.getName());
		assertEquals(0, defaultFood.getCaloriesPer100Unit());
		assertEquals(0, defaultFood.getCaloriesPerServing());
		assertNull(defaultFood.getServingQuantity());
		assertNull(defaultFood.getUnit());
	}
}
