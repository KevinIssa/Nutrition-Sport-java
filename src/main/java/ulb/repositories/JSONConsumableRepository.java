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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.models.Consumable;
import ulb.models.Food;
import ulb.models.Meal;

public class JSONConsumableRepository implements ConsumableRepository {
	private static final Logger logger = LoggerFactory.getLogger(JSONConsumableRepository.class);

	private static final String FOOD_FILE = "/ulb/jsons/food.json";
	private static final String MEAL_FOLDER = "meals";

	public Consumable loadByName(String name) {
		List<Consumable> consumables = this.loadAll();
		return binarySearch(consumables, name, 0, consumables.size() - 1);
	}

	@Override
	public List<Consumable> loadAll() {
		List<Consumable> consumables = new ArrayList<>();
		consumables.addAll(this.loadAllFood());
		consumables.addAll(this.loadAllMeals());
		Collections.sort(consumables);
		return consumables;
	}

	@Override
	public Consumable load(String name) {
		return this.loadAll().stream()
				.filter(consumable -> consumable.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Consumable> loadAllBeginningWith(String prefix) {
		List<Consumable> consumables = new ArrayList<>();
		for (Consumable consumable : this.loadAll()) {
			if (consumable.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
				consumables.add(consumable);
			}
		}
		return consumables;
	}

	private static class FoodList {
		private List<Food> food;

		public List<Food> getFood() {
			return food;
		}

		public void setFood(List<Food> food) {
			this.food = food;
		}
	}

	private List<Food> loadAllFood() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			FoodList foods =
					objectMapper.readValue(
							getClass().getResourceAsStream(FOOD_FILE), FoodList.class);
			return foods.getFood();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error loading food data from file: {}", FOOD_FILE);
			System.exit(1);
		}
		return Collections.emptyList();
	}

	public List<Meal> loadAllMeals() {
		File folder = new File(MEAL_FOLDER);
		File[] files = folder.listFiles();
		List<Meal> meals = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					meals.add(mapper.readValue(file, Meal.class));
				} catch (IOException e) {
					logger.error("Error loading meal data from file: {}", file.getName());
				}
			}
		}
		return meals;
	}

	private Consumable binarySearch(
			List<Consumable> consumables, String target, int start, int end) {
		if (start > end) {
			return null;
		}

		int mid = start + (end - start) / 2;
		int comparison = consumables.get(mid).getName().compareToIgnoreCase(target);

		if (comparison == 0) {
			return consumables.get(mid);
		} else if (comparison < 0) {
			return binarySearch(consumables, target, mid + 1, end);
		} else {
			return binarySearch(consumables, target, start, mid - 1);
		}
	}
}
