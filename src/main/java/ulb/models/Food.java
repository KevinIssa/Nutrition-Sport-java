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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a food item that can be consumed.
 */
@JsonSerialize(using = FoodSerializer.class)
public class Food implements Consumable {

	private String name;
	private int caloriesPer100;
	private int caloriesPerServing;
	private String servingQuantity;

	/**
	 * Default constructor for the Food class.
	 */
	public Food() {}

	/**
	 * Parameterized constructor for the Food class.
	 *
	 * @param name             The name of the food item.
	 * @param caloriesPer100   The number of calories per 100 grams.
	 * @param caloriesPerServing The number of calories per serving.
	 * @param servingQuantity The serving quantity of the food item.
	 */
	public Food(String name, int caloriesPer100, int caloriesPerServing, String servingQuantity) {
		this.name = name;
		this.caloriesPer100 = caloriesPer100;
		this.caloriesPerServing = caloriesPerServing;
		this.servingQuantity = servingQuantity;
	}

	/**
	 * Checks if this food item is equal to another object.
	 *
	 * @param o The object to compare with this food item.
	 * @return True if the objects are equal, false otherwise.
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Food food = (Food) o;
		return caloriesPer100 == food.caloriesPer100
				&& name.equals(food.name)
				&& servingQuantity.equals(food.servingQuantity);
	}

	/**
	 * Retrieves the total calories consumed based on a single serving.
	 *
	 * @return The total calories consumed per serving.
	 */
	@JsonIgnore
	@Override
	public int getCaloriesConsumed() {
		return getCaloriesConsumedByServing(1);
	}

	/**
	 * Retrieves the total calories consumed based on the specified number of grams.
	 *
	 * @param grams The number of grams consumed.
	 * @return The total calories consumed for the specified grams.
	 */
	@Override
	public int getCaloriesConsumedByGrams(int grams) {
		return (this.caloriesPer100 * grams) / 100;
		// ps I have modified this because if this.caloriesPer100 < 100 , this.caloriesPer100/100
		// will always return 0
		// because an int divided by an int will always return an int
	}

	/**
	 * Retrieves the total calories consumed based on the specified number of servings.
	 *
	 * @param servings The number of servings consumed.
	 * @return The total calories consumed for the specified servings.
	 */
	@Override
	public int getCaloriesConsumedByServing(int servings) {
		return this.caloriesPerServing * servings;
	}

	/**
	 * Converts the Food object to a string representation.
	 *
	 * @return The string representation of the Food object.
	 */
	public String toString() {
		return "Food{"
				+ "name='"
				+ name
				+ '\''
				+ ", caloriesPer100="
				+ caloriesPer100
				+ ", gramsPerServing="
				+ this.servingQuantity
				+ '}';
	}

	// Getters and setters for class attributes.

	/**
	 * Retrieves the name of the food item.
	 *
	 * @return The name of the food item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the food item.
	 *
	 * @param name The name of the food item.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the number of calories per 100 grams of the food item.
	 *
	 * @return The number of calories per 100 grams.
	 */
	public int getCaloriesPer100() {
		return caloriesPer100;
	}

	/**
	 * Sets the number of calories per 100 grams of the food item.
	 *
	 * @param caloriesPer100 The number of calories per 100 grams.
	 */
	public void setCaloriesPer100(int caloriesPer100) {
		this.caloriesPer100 = caloriesPer100;
	}

	/**
	 * Retrieves the number of calories per serving of the food item.
	 *
	 * @return The number of calories per serving.
	 */
	public int getCaloriesPerServing() {
		return caloriesPerServing;
	}

	/**
	 * Sets the number of calories per serving of the food item.
	 *
	 * @param caloriesPerServing The number of calories per serving.
	 */
	public void setCaloriesPerServing(int caloriesPerServing) {
		this.caloriesPerServing = caloriesPerServing;
	}

	/**
	 * Retrieves the serving quantity of the food item.
	 *
	 * @return The serving quantity of the food item.
	 */
	public String getServingQuantity() {
		return this.servingQuantity;
	}

	/**
	 * Retrieves the true serving quantity of the food item.
	 * we dont care about the unit of the value , we just want the value
	 *
	 * @return The serving quantity of the food item.
	 */
	public int extractServingQuantityValue() {
		// Define the pattern to match digits
		Pattern pattern = Pattern.compile("\\d+");

		// Create a matcher to find the pattern in the input string

		int startPosition = servingQuantity.indexOf("(");
		String substring = this.servingQuantity.substring(startPosition);
		Matcher matcher = pattern.matcher(substring);

		// Find the first match
		if (matcher.find()) {
			// Extract the matched digits and convert to an integer
			return Integer.parseInt(matcher.group());
		} else {
			throw new RuntimeException("quantity not present in servingquantity");
		}
	}

	/**
	 * Retrieves the unit of the serving quantity of the food item.
	 * we dont care about the quantity of the serving, we just want the unit
	 *
	 * @return the unit of the serving
	 */
	public String getServingType() {
		int startposition = servingQuantity.indexOf("(");
		String substring = this.servingQuantity.substring(startposition);
		if (substring.contains("ml")) {
			return "ml";
		} else {
			return "g";
		}
	}

	/**
	 * Sets the serving quantity of the food item.
	 *
	 * @param servingQuantity The serving quantity of the food item.
	 */
	public void setServingQuantity(String servingQuantity) {
		this.servingQuantity = servingQuantity;
	}
}

/**
 * Serializer class to serialize Food objects to JSON format.
 */
class FoodSerializer extends JsonSerializer<Food> {

	/**
	 * Serializes a Food object to JSON format.
	 *
	 * @param food             The Food object to serialize.
	 * @param jsonGenerator    The JSON generator used for serialization.
	 * @param serializerProvider The serializer provider.
	 * @throws IOException If an I/O error occurs during serialization.
	 */
	@Override
	public void serialize(
			Food food, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", food.getName());
		jsonGenerator.writeNumberField("caloriesPer100", food.getCaloriesPer100());
		jsonGenerator.writeNumberField("caloriesPerServing", food.getCaloriesPerServing());
		jsonGenerator.writeStringField("servingQuantity", food.getServingQuantity());
		jsonGenerator.writeEndObject();
	}
}
