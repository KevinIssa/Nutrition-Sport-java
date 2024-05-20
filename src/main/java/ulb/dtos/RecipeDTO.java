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

import java.util.List;

/**
 * This is a record class named RecipeDTO. A record is a special kind of class in Java that helps to model plain data aggregates with less ceremony than normal classes.
 * This record has three fields: name, foods, and personAmount.
 *
 * @param name The name of the recipe.
 * @param foods A list of FoodDTO objects representing the foods used in the recipe.
 * @param personAmount The number of persons for which the recipe is intended.
 */
public record RecipeDTO(String name, List<FoodDTO> foods, int personAmount) {

	/**
	 * This is a constructor for the RecipeDTO record.
	 * It takes two parameters: name and foods. The personAmount is set to 1 by default.
	 *
	 * @param name The name of the recipe.
	 * @param foods A list of FoodDTO objects representing the foods used in the recipe.
	 */
	public RecipeDTO(String name, List<FoodDTO> foods) {
		this(name, foods, 1);
	}

	/**
	 * This is a default constructor for the RecipeDTO record.
	 * It sets the name to an empty string, the foods to an empty list, and the personAmount to 1.
	 */
	public RecipeDTO() {
		this("", List.of(), 1);
	}
}
