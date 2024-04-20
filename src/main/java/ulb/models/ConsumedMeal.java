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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumedMeal {
	private static final Logger logger = LoggerFactory.getLogger(ConsumedMeal.class);
	private final List<ConsumedFood> consumedFoods = new ArrayList<>();
	private LocalDateTime date = LocalDateTime.now().withNano(0);

	/**
	 * Overrides the equals method to compare ConsumedMeal objects based on their date and consumedFoods list.
	 *
	 * @param obj Object to be compared with
	 * @return boolean indicating whether the objects are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConsumedMeal)) return false;
		ConsumedMeal meal = (ConsumedMeal) obj;
		return meal.date.equals(date) && meal.consumedFoods.equals(consumedFoods);
	}

	/**
	 * Adds a ConsumedFoodDTO object to the consumedFoods list.
	 *
	 * @param food ConsumedFoodDTO object to be added
	 */
	public void addConsumedFood(ConsumedFood food) {
		consumedFoods.add(food);
	}

	/**
	 * Creates a new ConsumedFoodDTO object and adds it to the consumedFoods list.
	 *
	 * @param name     Name of the food
	 * @param quantity Quantity of the food consumed
	 * @param calories Calories of the food consumed
	 */
	public void addConsumedFood(String name, int quantity, int calories, String type) {
		consumedFoods.add(new ConsumedFood(name, quantity, calories, type));
		logger.info("Added food: {} qty: {} cal: {} type: {}", name, quantity, calories, type);
	}

	/**
	 * Calculates the total calories consumed in the meal.
	 *
	 * @return Total calories consumed
	 */
	public int getCaloriesConsumed() {
		return consumedFoods.stream().mapToInt(ConsumedFood::getCalories).sum();
	}

	/**
	 * This method is used to get the list of consumed foods.
	 *
	 * @return List of ConsumedFood objects representing the foods consumed in the meal.
	 */
	public List<ConsumedFood> getConsumedFoods() {
		return consumedFoods;
	}

	/**
	 * Getter for the date field.
	 *
	 * @return Date and time when the meal was consumed
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Setter for the date field.
	 *
	 * @param date Date and time when the meal was consumed
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
