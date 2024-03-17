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

import org.junit.Assert;
import org.junit.Test;

public class TestConsumable {

	@Test
	public void testGetCaloriesConsumedByGrams() {
		Food food = new Food("food", 100, "100g");
		Assert.assertEquals(100, food.getCaloriesConsumedByGrams(100), 0);
	}
}
