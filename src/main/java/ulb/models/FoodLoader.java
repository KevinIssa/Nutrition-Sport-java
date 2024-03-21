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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class to load food data from a JSON file and provide methods to access and search food items.
 */
public class FoodLoader {

	private List<Food> foods;

	/**
	 * Constructs a FoodLoader and loads food data from the specified JSON file.
	 *
	 * @param filename The filename of the JSON file containing food data.
	 */
	public FoodLoader(String filename) {
		loadFoods(filename);
	}

	/**
	 * Loads food data from the specified JSON file.
	 *
	 * @param filename The filename of the JSON file containing food data.
	 */
	private void loadFoods(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			foods = objectMapper.readValue(new File(filename), new TypeReference<List<Food>>() {});
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Retrieves the list of loaded food items.
	 *
	 * @return The list of loaded food items.
	 */
	public List<Food> getFoods() {
		return foods;
	}

	/**
	 * Retrieves a list of food items that match the given input string.
	 *
	 * @param input The input string to match against food names.
	 * @return A list of food items that match the input string.
	 */
	public List<Food> getFoodsSuggestion(String input) {
		List<Food> result = new java.util.ArrayList<>();
		for (Food food : foods) {
			if (food.getName().toLowerCase().startsWith(input.toLowerCase())) {
				result.add(food);
			}
		}
		return result;
	}
}
