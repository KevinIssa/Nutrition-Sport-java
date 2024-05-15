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

import java.text.DecimalFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.enums.Unit;

public class Meal implements Consumable {
	private static final Logger logger = LoggerFactory.getLogger(Meal.class);

	private String name;
	private List<Map.Entry<Food, Double>> ingredients = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Meal() {}

	/**
	 * Constructor with name parameter.
	 *
	 * @param name The name of the meal.
	 */
	public Meal(String name) {
		this.name = name;
	}

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
	}

	/**
	 * Gets the total calories consumed by the meal.
	 *
	 * @return The total calories consumed.
	 */
	private double getCaloriesConsumed() {
		return getCaloriesConsumedByServing(1);
	}

	/**
	 * Gets the calories consumed by the meal for a given amount of grams.
	 *
	 * @param unit The grams of the meal.
	 * @return The calories consumed.
	 */
	@Override
	public double getCaloriesConsumedByUnit(double unit) {
		double totalGrams = getGramsForServing(1);
		return getCaloriesConsumed() * unit / totalGrams;
	}

	/**
	 * Gets the total grams for a given serving of the meal.
	 *
	 * @param servings The number of servings.
	 * @return The total grams.
	 */
	public double getGramsForServing(double servings) {
		double totalGrams = 0;
		logger.info("Calculating grams for servings: {}", servings);
		for (Map.Entry<Food, Double> ingredient : ingredients) {
			logger.info("Ingredient: {}", ingredient);
			totalGrams += ingredient.getValue();
		}
		logger.info("Total grams: {}", totalGrams);
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
			totalCalories += ingredient.getKey().getCaloriesConsumedByUnit(ingredient.getValue());
		}
		return totalCalories * servings;
	}

	// Getters and setters for class attributes.
	// These are used by Jackson to serialize and deserialize JSON data.

	/**
	 * Gets the name of the meal.
	 *
	 * @return The name of the meal.
	 */
	@Override
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

	public Unit getUnit() {
		return Unit.ALL;
	}

	@Override
	public String getServingQuantity() {
		DecimalFormat df = new DecimalFormat("#.##");
		String servingQuantity = df.format(getGramsForServing(1));
		return String.format("1 serving (%s g)", servingQuantity);
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
}
