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
 * This class represents a Height, which is a ValueObject that holds a Float value.
 * The height must be within a specified range (MIN_HEIGHT to MAX_HEIGHT).
 */
public class Height extends ValueObject<Float> {

	private static final int MIN_HEIGHT = 1; // Minimum height allowed
	private static final int MAX_HEIGHT = 300; // Maximum height allowed

	/**
	 * Default constructor for Height.
	 * Initializes the Height with no value.
	 */
	public Height() {
		super();
	}

	/**
	 * Constructor for Height with a specified height.
	 * @param height The height to be held by this Height object.
	 * @throws ValueObjectException If the height is not valid.
	 */
	public Height(float height) throws ValueObjectException {
		super(height);
	}

	/**
	 * Checks the validity of a specified height.
	 * The height must be greater than MIN_HEIGHT and less than MAX_HEIGHT.
	 * @param height The height to check.
	 * @throws BadHeightException If the height is not within the valid range.
	 */
	protected void checkValidity(Float height) throws BadHeightException {
		if (height <= MIN_HEIGHT) {
			throw new BadHeightException("la taille doit être supérieure à " + MIN_HEIGHT);
		} else if (height > MAX_HEIGHT) {
			throw new BadHeightException("la taille doit être inférieure à " + MAX_HEIGHT);
		}
	}

	/**
	 * Converts this Height object to a String.
	 * @return A string representation of this Height object, in the format "{value} cm".
	 */
	@Override
	public String toString() {
		return String.format("%.2f cm", value);
	}
}
