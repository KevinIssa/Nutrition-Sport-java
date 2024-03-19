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
import java.util.Iterator;
import java.util.List;

public class FoodLoader implements Iterable<Food> {

	private List<Food> foods;

	public FoodLoader(String filename) {
		loadFoods(filename);
	}

	private void loadFoods(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			foods = objectMapper.readValue(new File(filename), new TypeReference<List<Food>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Food> getFoods() {
		return foods;
	}

	public List<Food> getFoodsSuggestion(String input) {
		List<Food> result = new java.util.ArrayList<>();
		for (Food food : foods) {
			if (food.getName().toLowerCase().startsWith(input.toLowerCase())) {
				result.add(food);
			}
		}
		return result;
	}

	@Override
	public Iterator<Food> iterator() {
		return new FoodIterator();
	}

	private class FoodIterator implements Iterator<Food> {
		private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return currentIndex < foods.size();
		}

		@Override
		public Food next() {
			return foods.get(currentIndex++);
		}
	}
}
