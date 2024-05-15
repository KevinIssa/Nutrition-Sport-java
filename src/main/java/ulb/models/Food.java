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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.enums.Unit;

/**
 * Represents a food item that can be consumed.
 */
// @JsonSerialize(using = FoodSerializer.class)
public class Food implements Consumable, Comparable<Food> {
	private static final Logger logger = LoggerFactory.getLogger(Food.class);

	private String name;
	private double caloriesPer100Unit; // grams or ml
	private double caloriesPerServing;
	private String servingQuantity; // number of grams or ml per serving
	private Unit unit;

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
	public Food(
			String name,
			double caloriesPer100,
			double caloriesPerServing,
			String servingQuantity,
			Unit unit) {
		this.name = name;
		this.caloriesPer100Unit = caloriesPer100;
		this.caloriesPerServing = caloriesPerServing;
		this.servingQuantity = servingQuantity;
		this.unit = unit;
	}

	/**
	 * Checks if this food item is equal to another object.
	 *
	 * @param obj The object to compare with this food item.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Food food = (Food) obj;
		return caloriesPer100Unit == food.caloriesPer100Unit
				&& name.equals(food.name)
				&& servingQuantity.equals(food.servingQuantity);
	}

	/**
	 * Retrieves the total calories consumed based on the specified number of grams.
	 *
	 * @param grams The number of grams consumed.
	 * @return The total calories consumed for the specified grams.
	 */
	@Override
	public double getCaloriesConsumedByUnit(double grams) {
		return (this.caloriesPer100Unit * grams) / 100;
	}

	/**
	 * Retrieves the total calories consumed based on the specified number of servings.
	 *
	 * @param servings The number of servings consumed.
	 * @return The total calories consumed for the specified servings.
	 */
	@Override
	public double getCaloriesConsumedByServing(double servings) {
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
				+ caloriesPer100Unit
				+ ", gramsPerServing="
				+ this.servingQuantity
				+ '}';
	}

	// Getters and setters for class attributes.
	// These are used by Jackson to serialize and deserialize JSON data.

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
	public double getCaloriesPer100Unit() {
		return caloriesPer100Unit;
	}

	/**
	 * Sets the number of calories per 100 grams of the food item.
	 *
	 * @param caloriesPer100Unit The number of calories per 100 grams.
	 */
	public void setCaloriesPer100Unit(int caloriesPer100Unit) {
		this.caloriesPer100Unit = caloriesPer100Unit;
	}

	/**
	 * Retrieves the number of calories per serving of the food item.
	 *
	 * @return The number of calories per serving.
	 */
	public double getCaloriesPerServing() {
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
	 * Sets the serving quantity of the food item.
	 *
	 * @param servingQuantity The serving quantity of the food item.
	 */
	public void setServingQuantity(String servingQuantity) {
		this.servingQuantity = servingQuantity;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Compares this Food object with another Food object based on their names.
	 * The comparison is case-insensitive.
	 *
	 * @param food The Food object to compare with.
	 * @return A negative integer, zero, or a positive integer as this name is less than, equal to, or greater than the specified name.
	 */
	@Override
	public int compareTo(Food food) {
		return this.getName().toLowerCase().compareTo(food.getName().toLowerCase());
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
			logger.error(
					"No match found in serving quantity {} for food {} match {}",
					servingQuantity,
					name,
					substring);
			return 0;
		}
	}
}
