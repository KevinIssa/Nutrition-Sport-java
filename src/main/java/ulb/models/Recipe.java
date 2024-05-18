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

public class Recipe implements Consumable {
	private static final Logger logger = LoggerFactory.getLogger(Recipe.class);

	private String name;
	private List<Map.Entry<Consumable, Double>> ingredients = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Recipe() {}

	/**
	 * Constructor with name parameter.
	 *
	 * @param name The name of the meal.
	 */
	public Recipe(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Recipe recipe = (Recipe) obj;
		return Objects.equals(name, recipe.name) && Objects.equals(ingredients, recipe.ingredients);
	}

	/**
	 * Adds an ingredient to the meal.
	 *
	 * @param food     The food to add.
	 * @param quantity The quantity in gramme of the food.
	 */
	public void addIngredient(Consumable food, double quantity) {
		Map.Entry<Consumable, Double> entry = Map.entry(food, quantity);
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
		for (Map.Entry<Consumable, Double> ingredient : ingredients) { 
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
		for (Map.Entry<Consumable, Double> ingredient : ingredients) {
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
	public List<Map.Entry<Consumable, Double>> getIngredients() {
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
	public void setIngredients(List<Map.Entry<Consumable, Double>> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * Returns a string representation of the meal.
	 *
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		return "Recipe{" + "name='" + name + '\'' + ", ingredients=" + ingredients + '}';
	}
}
