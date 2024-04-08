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

/**
 * Represents a food item that has been consumed.
 * This class is responsible for managing the consumed food data.
 */
public class ConsumedFood {
	// Name of the consumed food
	private String name;
	// Quantity of the consumed food
	private int quantity;
	// Calories of the consumed food
	private int calories;
	private String type;

	/**
	 * Constructor initializing the name, quantity, and calories of the consumed food.
	 * @param name Name of the consumed food
	 * @param quantity Quantity of the consumed food
	 * @param calories Calories of the consumed food
	 */
	public ConsumedFood(String name, int quantity, int calories, String type) {
		this.name = name;
		this.quantity = quantity;
		this.calories = calories;
		this.type = type;
	}

	/**
	 * Overrides the equals method to compare ConsumedFood objects based on their name, quantity, and calories.
	 * @param obj Object to be compared with
	 * @return boolean indicating whether the objects are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ConsumedFood)) return false;
		ConsumedFood food = (ConsumedFood) obj;
		return food.name.equals(name) && food.quantity == quantity && food.calories == calories;
	}

	/**
	 * Setter for the name field.
	 * @param name Name of the consumed food
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the name field.
	 * @return Name of the consumed food
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the quantity field.
	 * @param quantity Quantity of the consumed food
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Getter for the quantity field.
	 * @return Quantity of the consumed food
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Setter for the calories field.
	 * @param calories Calories of the consumed food
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}

	/**
	 * Getter for the calories field.
	 * @return Calories of the consumed food
	 */
	public int getCalories() {
		return calories;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
}
