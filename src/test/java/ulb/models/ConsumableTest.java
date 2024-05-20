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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ulb.enums.Unit;

class ConsumableTest {

	@Test
	void servingQuantityTest1() {
		Food food = new Food("Apple", 52, 52, "1 (182g)", Unit.GRAMS);
		assertEquals(182, food.extractServingQuantityValue());
	}


	@Test
	void servingQuantityTest2() {
		Food food = new Food("Apple", 52, 52, "2 (100g) and 1 (50g)", Unit.GRAMS);
		assertEquals(100, food.extractServingQuantityValue());
	}

	@Test
	void servingQuantityTest3() {
		Food food = new Food("Apple", 52, 52, "(g)", Unit.GRAMS);
		assertEquals(0, food.extractServingQuantityValue());
	}



	@Test
	void testGetCaloriesConsumedByGrams() {
		assertEquals(
				100,
				new Food("food", 100, 100, "100g", Unit.GRAMS).getCaloriesConsumedByUnit(100),
				0);
	}
}
