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

public class TestFood {

	private Food food;

	@BeforeEach
	public void setup() {
		food = new Food("Apple", 52, 52, "1 (182g)");
	}

	@Test
	public void nameIsSetCorrectly() {
		assertEquals("Apple", food.getName());
	}

	@Test
	public void caloriesPer100IsSetCorrectly() {
		assertEquals(52, food.getCaloriesPer100());
	}

	@Test
	public void caloriesPerServingIsSetCorrectly() {
		assertEquals(52, food.getCaloriesPerServing());
	}

	@Test
	public void servingQuantityIsSetCorrectly() {
		assertEquals("1 (182g)", food.getServingQuantity());
	}

	@Test
	public void nameCanBeChanged() {
		food.setName("Banana");
		assertEquals("Banana", food.getName());
	}

	@Test
	public void caloriesPer100CanBeChanged() {
		food.setCaloriesPer100(96);
		assertEquals(96, food.getCaloriesPer100());
	}

	@Test
	public void caloriesPerServingCanBeChanged() {
		food.setCaloriesPerServing(105);
		assertEquals(105, food.getCaloriesPerServing());
	}

	@Test
	public void servingQuantityCanBeChanged() {
		food.setServingQuantity("1 (118g)");
		assertEquals("1 (118g)", food.getServingQuantity());
	}

	@Test
	public void servingQuantityValueIsExtractedCorrectly() {
		assertEquals(182, food.extractServingQuantityValue());
	}

	@Test
	public void servingTypeIsExtractedCorrectly() {
		assertEquals("g", food.getFoodQuantityUnit());
	}

	@Test
	public void caloriesConsumedByGramsIsCalculatedCorrectly() {
		assertEquals(94, food.getCaloriesConsumedByGrams(182));
	}

	@Test
	public void caloriesConsumedByServingIsCalculatedCorrectly() {
		assertEquals(52, food.getCaloriesConsumedByServing(1));
	}

	@Test
	public void equalsReturnsFalseForDifferentFood() {
		Food differentFood = new Food("Banana", 96, 105, "1 (118g)");
		assertNotEquals(food, differentFood);
	}

	@Test
	public void equalsReturnsTrueForSameFood() {
		Food sameFood = new Food("Apple", 52, 52, "1 (182g)");
		assertEquals(food, sameFood);
	}
}
