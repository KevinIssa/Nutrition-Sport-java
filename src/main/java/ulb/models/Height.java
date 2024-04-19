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

import ulb.exceptions.BadHeightException;
import ulb.exceptions.ValueObjectException;

/**
 * Represents the height of an individual.
 */
public class Height extends ValueObject<Float> {

	private static final int MIN_HEIGHT = 1; // Minimum height allowed
	private static final int MAX_HEIGHT = 300; // Maximum height allowed

	public Height() {
		super();
	}

	public Height(float height) throws ValueObjectException {
		super(height);
	}

	/**
	 * Validates the provided height value.
	 *
	 * @param height The height value to validate.
	 * @throws IllegalArgumentException If the provided height is not within the allowed range.
	 */
	protected void checkValidity(Float height) throws BadHeightException {
		if (height <= MIN_HEIGHT) {
			throw new BadHeightException(STR."Height must be greater than \{MIN_HEIGHT}");
		} else if (height > MAX_HEIGHT) {
			throw new BadHeightException(STR."Height must be less than \{MAX_HEIGHT}");
		}
	}

	@Override
	public String toString() {
		return String.format("%.2f cm", value);
	}
}
