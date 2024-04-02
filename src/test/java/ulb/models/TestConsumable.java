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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestConsumable {

	@Test
	public void testGetCaloriesConsumedByGrams() {
		assertEquals(100, new Food("food", 100, 100, "100g").getCaloriesConsumedByGrams(100), 0);
	}
}
