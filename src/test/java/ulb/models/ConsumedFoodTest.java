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

import org.junit.jupiter.api.Test;

class ConsumedFoodTest {

	@Test
	void equalsReturnsTrueForIdenticalObjects() {
		ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
		ConsumedFood food2 = food1;
		assertEquals(food1, food2);
	}

	@Test
	void equalsReturnsFalseForDifferentObjects() {
		ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
		ConsumedFood food2 = new ConsumedFood("Banana", 105, 100, "g");
		assertNotEquals(food1, food2);
	}

	@Test
	void equalsReturnsFalseForNull() {
		ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
		ConsumedFood food2 = null;
		assertNotEquals(food1, food2);
	}

	@Test
	void equalsReturnsFalseForDifferentClass() {
		ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
		String food2 = "Apple";
		assertNotEquals(food1, food2);
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
