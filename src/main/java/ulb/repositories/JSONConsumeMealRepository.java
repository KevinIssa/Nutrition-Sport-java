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
package ulb.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;

public class JSONConsumeMealRepository extends JSONRepository<ConsumedMeal>
		implements ConsumeMealRepository {
	private static final String FOLDER_NAME = "consumed_meals";

	@Override
	public void save(ConsumedMeal consumedMeal) {
		// TODO: Change this
		consumedMeal.save();
	}

	@Override
	public List<ConsumedMeal> loadAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		List<ConsumedMeal> consumedMeals = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				try {
					consumedMeals.add(this.load(file.getPath()));
				} catch (IOException _) {
					//
					// logger.error("Error loading consumed meal from file: " + file.getPath());
				}
			}
		}
		return consumedMeals;
	}

	@Override
	public void delete(ConsumedMeal consumedMeal) {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				try {
					ConsumedMeal loadedConsumedMeal = this.load(file.getPath());
					if (consumedMeal.equals(loadedConsumedMeal)) {
						file.delete();
						break;
					}
				} catch (IOException _) {
					// logger.error("Error loading consumed meal from file: " + file.getPath());
				}
			}
		}
	}

	@Override
	public void delete(ConsumedFood consumedFood) {
		// TODO: Implement this
	}

	@Override
	public void deleteAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
	}

	@Override
	protected Class<ConsumedMeal> getObjectType() {
		return ConsumedMeal.class;
	}
}
