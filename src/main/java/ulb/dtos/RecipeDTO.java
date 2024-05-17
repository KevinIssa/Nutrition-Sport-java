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

public record RecipeDTO(String name, List<FoodDTO> foods, int personAmount) {

	public RecipeDTO(String name, List<FoodDTO> foods) {
		this(name, foods, 1);
	}

	public RecipeDTO() {
		this("", List.of(), 1);
	}
}
