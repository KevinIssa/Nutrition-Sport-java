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

	public FoodLoader() {
		loadFoods("src/main/resources/food.json");
	}

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
			foods = objectMapper.readValue(new File(filename), new TypeReference<>() {});
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Return the corresponding food object from the user input
	 *
	 * @param userInput The user input given through the user interface
	 */
	public Food convertStringToFood(String userInput) {

		for (Food food : foods) {
			if (food.getName().equalsIgnoreCase(userInput)) {
				return food;
			}
		}
		return null;
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

	/**
	 * This method is used to find a Food object by its name using binary search.
	 * Binary search is an efficient algorithm for finding an item from a sorted list of items.
	 * It works by repeatedly dividing in half the portion of the list that could contain the item, until you've narrowed down the possible locations to just one.
	 *
	 * @param name The name of the food item to be searched.
	 * @return The Food object if it is found, null otherwise.
	 */
	public Food getFoodByName(String name) {
		return binarySearch(foods, name, 0, foods.size() - 1);
	}

	private Food binarySearch(List<Food> foods, String target, int start, int end) {
		if (start > end) {
			return null;
		}

		int mid = start + (end - start) / 2;
		int comparison = foods.get(mid).getName().compareToIgnoreCase(target);

		if (comparison == 0) {
			return foods.get(mid);
		} else if (comparison < 0) {
			return binarySearch(foods, target, mid + 1, end);
		} else {
			return binarySearch(foods, target, start, mid - 1);
		}
	}
}
