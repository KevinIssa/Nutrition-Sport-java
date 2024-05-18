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
import com.fasterxml.jackson.databind.ObjectMapper;
import ulb.dtos.RecipeDTO;
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

	@Override
	public void deleteRecipe(RecipeDTO recipe) {
		File folder = new File(RECIPES_FOLDER);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!file.isDirectory()) {
					ObjectMapper objectMapper = new ObjectMapper();
					try {
						Recipe recipeFromFile = objectMapper.readValue(file, Recipe.class);
						if (recipeFromFile.getName().equals(recipe.name())) {
							file.delete();
						}
					} catch (IOException e) {
						// Handle the exception
						e.printStackTrace();
					}
				}
			}
		}
	}
}
