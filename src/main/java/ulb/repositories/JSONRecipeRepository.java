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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.RecipeDTO;
import ulb.models.Recipe;

public class JSONRecipeRepository extends JSONRepository<Recipe> implements RecipeRepository {
	private static final Logger logger = LoggerFactory.getLogger(JSONRecipeRepository.class);

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
	public void deleteRecipe(RecipeDTO recipeDTO) {
		File folder = new File(RECIPES_FOLDER);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(
						new SimpleModule().addDeserializer(Recipe.class, new MealDeserializer()));
				try {
					Recipe recipe = mapper.readValue(file, Recipe.class);
					if (recipe.getName().equals(recipeDTO.name())) {
						file.delete();
					}
				} catch (IOException e) {
					logger.error("Error loading meal data from file: {}", file.getName());
				}
			}
		}
	}
}
