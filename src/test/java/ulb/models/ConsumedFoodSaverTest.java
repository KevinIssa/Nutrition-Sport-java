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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class ConsumedFoodSaverTest {

	@Test
	public void testSave() {
		// Create a temporary directory for testing
		File tempDir = new File("tempDir");
		tempDir.mkdir();

		try {
			// String path = "src/main/resources";
			String path = tempDir.getAbsolutePath();

			// Create an instance of ConsumedFoodSaver
			// ConsumedFoodSaver saver = new ConsumedFoodSaver();

			ArrayList<Food> list = new ArrayList<>();

			// Create a sample list of consumed food
			list.add(new Food("Apple", 100, 100, null));
			list.add(new Food("Banana", 150, 100, null));

			ConsumedFoodSaver<Food> saver = new ConsumedFoodSaver<>(list);

			// Set the folder name to the temporary directory
			// saver.setFolderName(tempDir.getAbsolutePath());
			saver.setFolderName(path);

			// Call the save method
			saver.save();

			// Verify that the JSON file is created in the temporary directory
			File jsonFile = new File(path + "/consumedFood.json");
			assertTrue(jsonFile.exists());

			// TODO: Add additional assertions to verify the content of the JSON file if needed

		} finally {
			// Clean up the temporary directory
			deleteDirectory(tempDir);
		}
	}

	// Helper method to delete a directory and its contents recursively
	private void deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						deleteDirectory(file);
					} else {
						file.delete();
					}
				}
			}
			directory.delete();
		}
	}
}
