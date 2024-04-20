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

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
	public void clearAllConsumedMealsShouldDeleteAllFiles() {
		consumedMeal.save();
		ConsumedMeal.clearAll();
		File folder = new File(ConsumedMeal.FOLDER_NAME);
		assertEquals(0, Objects.requireNonNull(folder.listFiles()).length);
	}

	@Test
	public void getCaloriesConsumedShouldReturnTotalCalories() {
		consumedMeal.addConsumedFood("Banana", 100, 100, "g");
		consumedMeal.addConsumedFood("Apple", 100, 200, "g");
		assertEquals(300, consumedMeal.getCaloriesConsumed());
	}

	@Test
	public void saveAndLoadShouldPreserveData() {
		consumedMeal.addConsumedFood("Banana", 100, 100, "g");
		consumedMeal.save();
		String filename =
				ConsumedMeal.FOLDER_NAME
						+ "/"
						+ consumedMeal
								.getDate()
								.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
						+ ".json";
		ConsumedMeal loadedMeal = ConsumedMeal.load(filename);
		assertEquals(consumedMeal, loadedMeal);
	}

	@Test
	public void changeDateFormatShouldReturnFormattedDate() {
		LocalDateTime date = LocalDateTime.of(2024, 12, 31, 23, 59);
		consumedMeal.setDate(date);
		String formattedDate = consumedMeal.changeDateFormat(date);
		assertEquals("31/12/2024 à 23:59", formattedDate);
	}
}
