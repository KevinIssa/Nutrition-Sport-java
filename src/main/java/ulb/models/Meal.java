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

@JsonDeserialize(using = MealDeserializer.class)
public class Meal implements Consumable {

	private String name;

	@JsonSerialize(using = FoodListSerializer.class)
	private List<Map.Entry<Food, Integer>> ingredients = new ArrayList<>();

	public static final String FILENAME = "Custom Meals.json";

	Meal() {}

	Meal(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meal meal = (Meal) o;
		return Objects.equals(name, meal.name) && Objects.equals(ingredients, meal.ingredients);
	}

	public void addIngredient(Food food, Integer quantity) {
		Map.Entry<Food, Integer> entry = Map.entry(food, quantity);
		this.ingredients.add(entry);
	}

	@Override
	public double getCaloriesConsumedByGrams(int grams) {
		double totalCalories = 0;
		for (Map.Entry<Food, Integer> ingredient : ingredients) {
			totalCalories +=
					ingredient
							.getKey()
							.getCaloriesConsumedByGrams(ingredient.getValue().intValue());
		}
		return totalCalories;
	}

	public void save() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(new File(FILENAME), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Meal load(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			return mapper.readValue(new File(filename), Meal.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map.Entry<Food, Integer>> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Map.Entry<Food, Integer>> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Meal{" + "name='" + name + '\'' + ", ingredients=" + ingredients + '}';
	}
}

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

class MealDeserializer extends StdDeserializer<Meal> {
	public MealDeserializer() {
		this(null);
	}

	public MealDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Meal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		JsonNode mealNode = jp.getCodec().readTree(jp);
		String name = mealNode.get("name").asText();
		List<Map.Entry<Food, Integer>> ingredients = new ArrayList<>();

		JsonNode ingredientsNode = mealNode.get("ingredients");
		Iterator<JsonNode> iterator = ingredientsNode.elements();
		while (iterator.hasNext()) {
			JsonNode ingredientNode = iterator.next();
			JsonNode foodNode = ingredientNode.get("food");
			String foodName = foodNode.get("name").asText();
			int caloriesPer100 = foodNode.get("caloriesPer100").asInt();
			String servingQuantity = foodNode.get("servingQuantity").asText();
			Food food = new Food(foodName, caloriesPer100, servingQuantity);
			int quantity = ingredientNode.get("quantity").asInt();
			ingredients.add(Map.entry(food, quantity));
		}

		Meal meal = new Meal(name);
		meal.setIngredients(ingredients);
		return meal;
	}
}
