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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class TestConsumedMeal {

	@Test
	public void testConsumedFood() {
		ConsumedMeal consumedFood = createAndSaveConsumedFood();
		ConsumedMeal consumedFood2 = ConsumedMeal.load(getFilename(consumedFood.getDate()));
		// Remove the file
		new File(getFilename(consumedFood.getDate())).delete();
		// Test
		assertNotNull(consumedFood);
		assertNotNull(consumedFood2);
		assertEquals(consumedFood, consumedFood2);
	}

	private ConsumedMeal createAndSaveConsumedFood() {
		ConsumedMeal consumedMeal = new ConsumedMeal();
		consumedMeal.addConsumedFood("Banana", 100, 100);
		consumedMeal.addConsumedFood("Apple", 100, 200);
		consumedMeal.save();
		return consumedMeal;
	}

	private String getFilename(LocalDateTime date) {
		return ConsumedMeal.FOLDER_NAME
				+ "/"
				+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
				+ ".json";
	}
}
