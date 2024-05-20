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

/**
 * This is a record class named ConsumableDTO. A record is a special kind of class in Java that helps to model plain data aggregates with less ceremony than normal classes.
 * This record has three fields: name, unit, and servingQuantity.
 *
 * @param name The name of the consumable item.
 * @param unit The unit of the consumable item.
 * @param servingQuantity The serving quantity of the consumable item.
 */
public record ConsumableDTO(String name, Unit unit, String servingQuantity) {

	/**
	 * This method is used to extract the numeric value from the servingQuantity string.
	 * The servingQuantity string is expected to have a format where the numeric value is enclosed in parentheses.
	 * For example, "2 cups (500g)", where "500" is the numeric value.
	 *
	 * The method uses regular expressions to find the numeric value in the string.
	 * If no numeric value is found, the method returns 0.
	 *
	 * @return The numeric value extracted from the servingQuantity string, or 0 if no numeric value is found.
	 */
	public double extractServingQuantityValue() {
		// Define the pattern to match digits
		Pattern pattern = Pattern.compile("\\d+");
		// Find the position of the opening parenthesis in the servingQuantity string
		int startPosition = servingQuantity.indexOf("(");
		// Extract the substring from the servingQuantity string starting from the opening
		// parenthesis
		String substring = this.servingQuantity.substring(startPosition);
		Matcher matcher = pattern.matcher(substring);
		if (matcher.find()) {
			return Double.parseDouble(matcher.group());
		} else {
			return 0;
		}
	}
}
