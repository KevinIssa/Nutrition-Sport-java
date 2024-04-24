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

public class ConsumedMealTest {

	private ConsumedMeal consumedMeal;

	@BeforeEach
	public void setup() {
		consumedMeal = new ConsumedMeal();
	}

	@Test
	public void addConsumedFoodShouldAddFoodToList() {
		consumedMeal.addConsumedFood("Banana", 100, 100, "g");
		List<ConsumedFood> consumedFoods = consumedMeal.getConsumedFoods();
		assertEquals(1, consumedFoods.size());
		assertEquals("Banana", consumedFoods.get(0).getName());
	}

	@Test
	public void getCaloriesConsumedShouldReturnTotalCalories() {
		consumedMeal.addConsumedFood("Banana", 100, 100, "g");
		consumedMeal.addConsumedFood("Apple", 100, 200, "g");
		assertEquals(300, consumedMeal.getCaloriesConsumed());
	}
}
