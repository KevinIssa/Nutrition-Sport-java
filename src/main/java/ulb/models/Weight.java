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

import ulb.exceptions.BadWeightException;
import ulb.exceptions.ValueObjectException;

/**
 * This class represents a Weight, which is a ValueObject that holds a Float value.
 * The weight must be within a specified range (MIN_WEIGHT to MAX_WEIGHT).
 */
public class Weight extends ValueObject<Float> {

	private static final int MIN_WEIGHT = 1; // Minimum weight allowed
	private static final int MAX_WEIGHT = 500; // Maximum weight allowed

	/**
	 * Default constructor for Weight.
	 * Initializes the Weight with no value.
	 */
	public Weight() {
		super();
	}

	/**
	 * Constructor for Weight with a specified weight.
	 * @param weight The weight to be held by this Weight object.
	 * @throws ValueObjectException If the weight is not valid.
	 */
	public Weight(float weight) throws ValueObjectException {
		super(weight);
	}

	/**
	 * Checks the validity of a specified weight.
	 * The weight must be greater than MIN_WEIGHT and less than MAX_WEIGHT.
	 * @param weight The weight to check.
	 * @throws IllegalArgumentException If the weight is not a Float.
	 * @throws BadWeightException If the weight is not within the valid range.
	 */
	protected void checkValidity(Float weight) throws BadWeightException {
		if (weight <= MIN_WEIGHT) {
			throw new BadWeightException("le poids doit être supérieur à " + MIN_WEIGHT);
		} else if (weight > MAX_WEIGHT) {
			throw new BadWeightException("le poids doit être inférieur à " + MAX_WEIGHT);
		}
	}

	/**
	 * Converts this Weight object to a String.
	 * @return A string representation of this Weight object, in the format "{value} kg".
	 */
	@Override
	public String toString() {
		return this.value + " kg";
	}
}
