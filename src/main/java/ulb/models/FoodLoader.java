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
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to load food data from a JSON file and provide methods to access and search food items.
 */
public class FoodLoader {

	private static final Logger logger = LoggerFactory.getLogger(FoodLoader.class);
	private static final String FOOD_FILE = "/ulb/jsons/food.json";
	private List<Food> foods;

	private static FoodLoader instance = null;

	/**
	 * Constructs a FoodLoader and loads food data from a JSON file.
	 */
	private FoodLoader() {
		this.loadFoods();
		this.loadMeals();
	}

	public static FoodLoader getInstance() {
		if (instance == null) {
			instance = new FoodLoader();
		}
		return instance;
	}

	/**
	 * Loads food data from the specified JSON file.
	 */
	private void loadFoods() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			foods =
					objectMapper.readValue(
							getClass().getResourceAsStream(FOOD_FILE), new TypeReference<>() {});
		} catch (IOException e) {
			logger.error("Error loading food data from file: {}", FOOD_FILE);
			System.exit(1);
		}
	}

	private void loadMeals() {
		Meal.loadAll().forEach(meal -> foods.add(meal.toFood()));
		Collections.sort(foods);
	}

	/**
	 * Retrieves the list of loaded food items.
	 *
	 * @return The list of loaded food items.
	 */
	public List<Food> getFoods() {
		return foods;
	}

	public void extend(List<Food> foods) {
		this.foods.addAll(foods);
		Collections.sort(this.foods);
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
