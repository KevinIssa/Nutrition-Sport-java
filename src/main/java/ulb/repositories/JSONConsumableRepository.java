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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.enums.Unit;
import ulb.models.Consumable;
import ulb.models.Food;
import ulb.models.Recipe;

public class JSONConsumableRepository implements ConsumableRepository {
	private static final Logger logger = LoggerFactory.getLogger(JSONConsumableRepository.class);

	private static final String FOOD_FILE = "/ulb/jsons/food.json";
	private static final String RECIPES_FOLDER = "recipes";
	private static final String CONSUMED_MEALS_FOLDER = "consumed_meals";

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

	@Override
	public void save(Recipe recipe) {
		File mealFolder = new File(RECIPES_FOLDER);
		if (!mealFolder.exists()) {
			mealFolder.mkdirs();
		}

		logger.debug("Saving meal data to file: {}", recipe.getName());
		ObjectMapper mapper = new ObjectMapper();
		class ConsumableEntryList extends ArrayList<Map.Entry<Consumable, Double>> {}
		mapper.registerModule(
				new SimpleModule()
						.addSerializer(
								(Class<List<Map.Entry<Consumable, Double>>>) (Class<?>) List.class,
								new ConsumableListSerializer()));
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			mapper.writeValue(
					new File(
							RECIPES_FOLDER
									+ "/"
									+ LocalDateTime.now()
											.format(
													DateTimeFormatter.ofPattern(
															"yyyy-MM-dd-HH-mm-ss"))
									+ ".json"),
					recipe);
		} catch (IOException e) {
			logger.error("Error saving meal data to file: {}", recipe.getName());
		}
	}

	private List<Food> loadAllFood() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(
					getClass().getResourceAsStream(FOOD_FILE), new TypeReference<>() {});
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error loading food data from file: {}", FOOD_FILE);
			System.exit(1);
		}
		return Collections.emptyList();
	}

	public List<Recipe> loadAllMeals() {
		File folder = new File(RECIPES_FOLDER);
		File[] files = folder.listFiles();
		List<Recipe> recipes = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(
						new SimpleModule().addDeserializer(Recipe.class, new MealDeserializer()));
				try {
					recipes.add(mapper.readValue(file, Recipe.class));
				} catch (IOException e) {
					logger.error("Error loading meal data from file: {}", file.getName());
				}
			}
		}
		return recipes;
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

	@Override
	public void deleteAll() {
		File folder = new File(CONSUMED_MEALS_FOLDER);
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

/**
 * Serializer for Food list.
 */
class ConsumableListSerializer extends JsonSerializer<List<Map.Entry<Consumable, Double>>> {
	private static final Logger logger = LoggerFactory.getLogger(ConsumableListSerializer.class);

	@Override
	public void serialize(
			List<Map.Entry<Consumable, Double>> value,
			JsonGenerator gen,
			SerializerProvider serializers)
			throws IOException {
		logger.debug("Serializing consumable list");
		gen.writeStartArray();
		for (Map.Entry<Consumable, Double> entry : value) {
			gen.writeStartObject();
			gen.writeObjectField("food", entry.getKey());
			gen.writeObjectField("quantity", entry.getValue());
			gen.writeEndObject();
		}
		gen.writeEndArray();
	}
}

/**
 * Deserializer for Recipe.
 * This class is responsible for converting JSON data into a Recipe object.
 */
class MealDeserializer extends StdDeserializer<Recipe> {
	private static final Logger logger = LoggerFactory.getLogger(MealDeserializer.class);

	/**
	 * Default constructor specifying the type to be deserialized.
	 */
	public MealDeserializer() {
		super(Recipe.class);
	}

	/**
	 * Deserializes the JSON content into a Recipe object.
	 * @param jp JsonParser object used for reading JSON content
	 * @param ctxt DeserializationContext object used for accessing contextual information during deserialization
	 * @return Recipe object containing the deserialized data
	 */
	@Override
	public Recipe deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		JsonNode mealNode = jp.getCodec().readTree(jp);
		String name = mealNode.get("name").asText();
		List<Map.Entry<Consumable, Double>> ingredients = this.getIngredients(mealNode);

		Recipe meal = new Recipe(name);
		meal.setIngredients(ingredients);
		return meal;
	}

	/**
	 * Extracts the ingredients from the JSON node and returns them as a list of Map.Entry objects.
	 * @param mealNode JsonNode object containing the meal data
	 * @return List of Map.Entry objects where each entry represents a food and its quantity
	 */
	private List<Map.Entry<Consumable, Double>> getIngredients(JsonNode mealNode) {
		List<Map.Entry<Consumable, Double>> ingredients = new ArrayList<>();
		JsonNode ingredientsNode = mealNode.get("ingredients");
		Iterator<JsonNode> iterator = ingredientsNode.elements();
		while (iterator.hasNext()) {
			ingredients.add(this.getIngredient(iterator.next()));
		}
		return ingredients;
	}

	/**
	 * Extracts a single ingredient from the JSON node and returns it as a Map.Entry object.
	 * @param ingredientNode JsonNode object containing the ingredient data
	 * @return Map.Entry object where the key is a Food object and the value is the quantity
	 */
	private Map.Entry<Consumable, Double> getIngredient(JsonNode ingredientNode) {
		Food food = getFood(ingredientNode.get("food"));
		double quantity = ingredientNode.get("quantity").asDouble();
		return Map.entry(food, quantity);
	}

	/**
	 * Extracts the food data from the JSON node and returns it as a Food object.
	 * @param foodNode JsonNode object containing the food data
	 * @return Food object containing the extracted data
	 */
	private Food getFood(JsonNode foodNode) {
		String foodName = foodNode.get("name").asText();
		int caloriesPer100 = foodNode.get("caloriesPer100Unit").asInt();
		int caloriesPerServing = foodNode.get("caloriesPerServing").asInt();
		String servingQuantity = foodNode.get("servingQuantity").asText();
		Unit unit = Unit.valueOf(foodNode.get("unit").asText());
		return new Food(foodName, caloriesPer100, caloriesPerServing, servingQuantity, unit);
	}
}
