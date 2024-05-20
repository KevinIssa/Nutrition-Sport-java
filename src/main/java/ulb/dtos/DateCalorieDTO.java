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
package ulb.dtos;

import java.time.LocalDate;

/**
 * This is a record class named DateCalorieDTO. A record is a special kind of class in Java that helps to model plain data aggregates with less ceremony than normal classes.
 * This record has three fields: date, calorieIntake, and calorieBurned.
 *
 * @param date The date for which the calorie data is recorded.
 * @param calorieIntake The total calories intake for the specified date.
 * @param calorieBurned The total calories burned for the specified date.
 */
public record DateCalorieDTO(LocalDate date, double calorieIntake, double calorieBurned) {

	/**
	 * This method is used to calculate the difference between the calorie intake and the calories burned.
	 * The method subtracts the calories burned from the calorie intake and returns the result.
	 *
	 * @return The difference between the calorie intake and the calories burned.
	 */
	public double getCalorieDifference() {
		return calorieIntake - calorieBurned;
	}

	/**
	 * This method is used to get the date for which the calorie data is recorded.
	 *
	 * @return The date for which the calorie data is recorded.
	 */
	public LocalDate getDate() {
		return date;
	}
}
