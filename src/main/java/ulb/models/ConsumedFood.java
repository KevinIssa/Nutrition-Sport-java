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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a food item that has been consumed.
 * This class is responsible for managing the consumed food data.
 */
public class ConsumedFood {
	private static final Logger logger = LoggerFactory.getLogger(ConsumedFood.class);
	private String name;
	private int quantity; // in grams
	private int calories; // consumed
	private String unit; // g or ml

	/**
	 * Constructor initializing the name, quantity, and calories of the consumed food.
	 * @param name Name of the consumed food
	 * @param quantity Quantity of the consumed food
	 * @param calories Calories of the consumed food
	 */
	public ConsumedFood(String name, int quantity, int calories, String unit) {
		this.name = name;
		this.quantity = quantity;
		this.calories = calories;
		this.unit = unit;
		logger.info("ConsumedFood object created {}", this);
	}

	/**
	 * Overrides the equals method to compare ConsumedFoodDTO objects based on their name, quantity, and calories.
	 * @param obj Object to be compared with
	 * @return boolean indicating whether the objects are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ConsumedFood food)) return false;
		return food.name.equals(name) && food.quantity == quantity && food.calories == calories;
	}

	/**
	 * Overrides the toString method for the ConsumedFood class.
	 * This method is used to provide a string representation of the ConsumedFood object.
	 * The string representation includes the name, quantity, calories, and unit of the consumed food.
	 *
	 * @return A string representation of the ConsumedFood object.
	 */
	@Override
	public String toString() {
		return STR."ConsumedFood{name='\{
				name}\{
				'\''}, quantity=\{
				quantity}, calories=\{
				calories}, unit='\{
				unit}\{
				'\''}\{
				'}'}";
	}

	// Getters and setters for class attributes.

	/**
	 * Getter for the name field.
	 * @return Name of the consumed food
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name field.
	 * @param name Name of the consumed food
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the quantity field.
	 * @return Quantity of the consumed food
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Setter for the quantity field.
	 * @param quantity Quantity of the consumed food
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Getter for the calories field.
	 * @return Calories of the consumed food
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * Setter for the calories field.
	 * @param calories Calories of the consumed food
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}

	/**
	 * Getter for the type field.
	 * @return Type of the consumed food
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Setter for the type field.
	 * @param unit Type of the consumed food
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
