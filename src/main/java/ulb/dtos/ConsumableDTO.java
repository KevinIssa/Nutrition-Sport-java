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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ulb.enums.Unit;

public record ConsumableDTO(String name, Unit unit, String servingQuantity) {

	public double extractServingQuantityValue() {
		// Define the pattern to match digits
		Pattern pattern = Pattern.compile("\\d+");

		// Create a matcher to find the pattern in the input string

		int startPosition = servingQuantity.indexOf("(");
		String substring = this.servingQuantity.substring(startPosition);
		Matcher matcher = pattern.matcher(substring);

		// Find the first match
		if (matcher.find()) {
			// Extract the matched digits and convert to an integer
			return Double.parseDouble(matcher.group());
		} else {
			return 0;
		}
	}
}
