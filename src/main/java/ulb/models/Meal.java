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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a Meal.
 */
@JsonDeserialize(using = MealDeserializer.class)
public class Meal implements Consumable, JsonSerializable {
	private static final Logger logger = LoggerFactory.getLogger(Meal.class);
	public static final String FOLDER_NAME = "meals";

	private String name;

	@JsonSerialize(using = FoodListSerializer.class)
	private List<Map.Entry<Food, Double>> ingredients = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	Meal() {}

	/**
	 * Constructor with name parameter.
	 *
	 * @param name The name of the meal.
	 */
	public Meal(String name) {
		this.name = name;
	}

	/**
	 * Checks if this meal is equal to another object.
	 *
	 * @param obj The object to compare.
	 * @return True if the objects are equal, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Meal meal = (Meal) obj;
		return Objects.equals(name, meal.name) && Objects.equals(ingredients, meal.ingredients);
	}

	/**
	 * Adds an ingredient to the meal.
	 *
	 * @param food     The food to add.
	 * @param quantity The quantity in gramme of the food.
	 */
	public void addIngredient(Food food, double quantity) {
		Map.Entry<Food, Double> entry = Map.entry(food, quantity);
		this.ingredients.add(entry);
		logger.trace("Added ingredient: {}", entry);
	}

	/**
	 * Gets the total calories consumed by the meal.
	 *
	 * @return The total calories consumed.
	 */
	@Override
	public double getCaloriesConsumed() {
		return getCaloriesConsumedByServing(1);
	}

	/**
	 * Gets the calories consumed by the meal for a given amount of grams.
	 *
	 * @param grams The grams of the meal.
	 * @return The calories consumed.
	 */
	@Override
	public double getCaloriesConsumedByGrams(double grams) {
		double totalGrams = getGramsForServing(1);
		return getCaloriesConsumed() * grams / totalGrams;
	}

	/**
	 * Gets the total grams for a given serving of the meal.
	 *
	 * @param servings The number of servings.
	 * @return The total grams.
	 */
	public double getGramsForServing(double servings) {
		int totalGrams = 0;
		for (Map.Entry<Food, Double> ingredient : ingredients) {
			totalGrams += ingredient.getValue();
		}
		return totalGrams * servings;
	}

	/**
	 * Gets the total calories consumed by the meal for a given number of servings.
	 *
	 * @param servings The number of servings.
	 * @return The total calories consumed.
	 */
	@Override
	public double getCaloriesConsumedByServing(double servings) {
		double totalCalories = 0;
		for (Map.Entry<Food, Double> ingredient : ingredients) {
			totalCalories += ingredient.getKey().getCaloriesConsumedByGrams(ingredient.getValue());
		}
		return totalCalories * servings;
	}

	/**
	 * Saves the meal to a file.
	 */
	public void save() {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			folder.mkdir();
			logger.info("Created folder: {}", FOLDER_NAME);
		}
		String filename = FOLDER_NAME + "/" + name + ".json";
		saveToFile(filename);
	}

	/**
	 * Loads a meal from a file.
	 *
	 * @param filename The name of the file.
	 * @return The loaded meal.
	 */
	public static Meal load(String filename) {
		return (Meal) new Meal().loadFromFile(filename);
	}

	public static List<Meal> loadAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		List<Meal> meals = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				meals.add(load(file.getPath()));
			}
		}
		return meals;
	}

	/**
	 * Saves the meal to a file.
	 *
	 * @param filename The name of the file.
	 */
	public void saveToFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(new File(filename), this);
		} catch (IOException e) {
			logger.error("Error saving meal to file: {} meal: {}", filename, this);
		}
	}

	/**
	 * Loads a meal from a file.
	 *
	 * @param filename The name of the file.
	 * @return The loaded meal.
	 */
	public JsonSerializable loadFromFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new File(filename), Meal.class);
		} catch (IOException e) {
			logger.error("Error loading meal from file: {} meal {}", filename, this);
		}
		return null;
	}

	// Getters and setters for class attributes.
	// These are used by Jackson to serialize and deserialize JSON data.

	/**
	 * Gets the name of the meal.
	 *
	 * @return The name of the meal.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the meal.
	 *
	 * @param name The name of the meal.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the ingredients of the meal.
	 *
	 * @return The list of ingredients.
	 */
	public List<Map.Entry<Food, Double>> getIngredients() {
		return ingredients;
	}

	/**
	 * Sets the ingredients of the meal.
	 *
	 * @param ingredients The list of ingredients.
	 */
	public void setIngredients(List<Map.Entry<Food, Double>> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * Returns a string representation of the meal.
	 *
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		return "Meal{" + "name='" + name + '\'' + ", ingredients=" + ingredients + '}';
	}

	public Food toFood() {
		logger.trace(
				"Converting meal to food: {}, {}, {}",
				name,
				getCaloriesConsumed(),
				getGramsForServing(1));
		return new Food(
				this.name,
				this.getCaloriesConsumedByGrams(100),
				this.getCaloriesConsumed(),
				String.format("1 serving (%f g)", this.getGramsForServing(1)));
	}
}

/**
 * Serializer for Food list.
 */
class FoodListSerializer
		extends com.fasterxml.jackson.databind.JsonSerializer<List<Map.Entry<Food, Integer>>> {
	@Override
	public void serialize(
			List<Map.Entry<Food, Integer>> value,
			com.fasterxml.jackson.core.JsonGenerator gen,
			com.fasterxml.jackson.databind.SerializerProvider serializers)
			throws IOException {
		gen.writeStartArray();
		for (Map.Entry<Food, Integer> entry : value) {
			gen.writeStartObject();
			gen.writeObjectField("food", entry.getKey());
			gen.writeObjectField("quantity", entry.getValue());
			gen.writeEndObject();
		}
		gen.writeEndArray();
	}
}

/**
 * Deserializer for Meal.
 * This class is responsible for converting JSON data into a Meal object.
 */
class MealDeserializer extends StdDeserializer<Meal> {

	/**
	 * Default constructor specifying the type to be deserialized.
	 */
	public MealDeserializer() {
		super(Meal.class);
	}

	/**
	 * Deserializes the JSON content into a Meal object.
	 * @param jp JsonParser object used for reading JSON content
	 * @param ctxt DeserializationContext object used for accessing contextual information during deserialization
	 * @return Meal object containing the deserialized data
	 */
	@Override
	public Meal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		JsonNode mealNode = jp.getCodec().readTree(jp);
		String name = mealNode.get("name").asText();
		List<Map.Entry<Food, Double>> ingredients = this.getIngredients(mealNode);

		Meal meal = new Meal(name);
		meal.setIngredients(ingredients);
		return meal;
	}

	/**
	 * Extracts the ingredients from the JSON node and returns them as a list of Map.Entry objects.
	 * @param mealNode JsonNode object containing the meal data
	 * @return List of Map.Entry objects where each entry represents a food and its quantity
	 */
	private List<Map.Entry<Food, Double>> getIngredients(JsonNode mealNode) {
		List<Map.Entry<Food, Double>> ingredients = new ArrayList<>();
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
	private Map.Entry<Food, Double> getIngredient(JsonNode ingredientNode) {
		Food food = getFood(ingredientNode.get("food"));
		double quantity = ingredientNode.get("quantity").asInt();
		return Map.entry(food, quantity);
	}

	/**
	 * Extracts the food data from the JSON node and returns it as a Food object.
	 * @param foodNode JsonNode object containing the food data
	 * @return Food object containing the extracted data
	 */
	private Food getFood(JsonNode foodNode) {
		String foodName = foodNode.get("name").asText();
		int caloriesPer100 = foodNode.get("caloriesPer100").asInt();
		int caloriesPerServing = foodNode.get("caloriesPerServing").asInt();
		String servingQuantity = foodNode.get("servingQuantity").asText();
		return new Food(foodName, caloriesPer100, caloriesPerServing, servingQuantity);
	}
}
