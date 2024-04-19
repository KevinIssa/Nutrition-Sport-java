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

/**
 * Represents the height of an individual.
 */
public class Height {

	private static final int MIN_HEIGHT = 1; // Minimum height allowed
	private static final int MAX_HEIGHT = 300; // Maximum height allowed

	private float height; // in cm

	/**
	 * Constructor for the Height class.
	 *
	 * @param height The height value in centimeters.
	 * @throws IllegalArgumentException If the provided height is not within the allowed range.
	 */
	public Height(float height) throws BadHeightException {
		checkValidity(height);
		this.height = height;
	}

	/**
	 * Validates the provided height value.
	 *
	 * @param height The height value to validate.
	 * @throws IllegalArgumentException If the provided height is not within the allowed range.
	 */
	private void checkValidity(float height) throws BadHeightException {
		if (height <= MIN_HEIGHT) {
			throw new BadHeightException(STR."Height must be greater than \{MIN_HEIGHT}");
		} else if (height > MAX_HEIGHT) {
			throw new BadHeightException(STR."Height must be less than \{MAX_HEIGHT}");
		}
	}

	/**
	 * Retrieves the height value.
	 *
	 * @return The height value in centimeters.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the height value.
	 *
	 * @param height The height value to set in centimeters.
	 * @throws IllegalArgumentException If the provided height is not within the allowed range.
	 */
	public void setHeight(float height) throws BadHeightException {
		this.checkValidity(height);
		this.height = height;
	}

	/**
	 * Returns a string representation of the Height object.
	 *
	 * @return A string representing the height.
	 */
	@Override
	public String toString() {
		return String.valueOf(this.height);
	}

	/**
	 * Checks if this Height object is equal to another object.
	 *
	 * @param obj The object to compare with this Height object.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Height otherHeight = (Height) obj;
		return Float.compare(otherHeight.height, height) == 0;
	}
}
