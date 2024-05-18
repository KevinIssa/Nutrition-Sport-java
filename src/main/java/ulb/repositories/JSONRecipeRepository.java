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
import ulb.models.Recipe;

public class JSONRecipeRepository extends JSONRepository<Recipe> implements RecipeRepository {

	private static final String RECIPES_FOLDER = "recipes";

	@Override
	protected Class<Recipe> getObjectType() {
		return Recipe.class;
	}

	@Override
	public void deleteAll() {
		File folder = new File(RECIPES_FOLDER);
		File[] files = folder.listFiles();
		if (files != null) {
			// logger.info("Deleting all consumables");
			for (File file : files) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}
		}
	}
}
