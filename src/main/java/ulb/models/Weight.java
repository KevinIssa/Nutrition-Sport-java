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

/**
 * Represents the weight of a person.
 */
public class Weight {

	private static final int MIN_WEIGHT = 1; // Minimum weight allowed
	private static final int MAX_WEIGHT = 500; // Maximum weight allowed

	private float weight; // in kg

	/**
	 * Constructs a Weight object with the specified weight.
	 * @param weight The weight value in kilograms.
	 * @throws IllegalArgumentException if the weight is less than or equal to MIN_WEIGHT or greater than MAX_WEIGHT.
	 */
	public Weight(float weight) throws BadWeightException {
		this.checkValidity(weight);
		this.weight = weight;
	}

	// Validate weight value

	/**
	 * Validates the weight value.
	 * @param weight The weight value to validate.
	 * @throws IllegalArgumentException if the weight is out of the allowed range.
	 */
	private void checkValidity(float weight) throws IllegalArgumentException, BadWeightException {
		if (weight <= MIN_WEIGHT) {
			throw new BadWeightException("Weight must be greater than " + MIN_WEIGHT);
		} else if (weight > MAX_WEIGHT) {
			throw new BadWeightException("Weight must be less than " + MAX_WEIGHT);
		}
	}

	/**
	 * Gets the weight value.
	 * @return The weight value in kilograms.
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * Sets the weight value.
	 * @param weight The new weight value to set in kilograms.
	 * @throws IllegalArgumentException if the new weight is out of the allowed range.
	 */
	public void setWeight(float weight) throws BadWeightException {
		this.checkValidity(weight);
		this.weight = weight;
	}

	/**
	 * Returns a string representation of the Weight object.
	 * @return A string representation including the weight value.
	 */
	@Override
	public String toString() {
		return "Weight=" + weight;
	}

	/**
	 * Checks if this Weight object is equal to another object.
	 * @param obj The object to compare with.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Weight otherWeight = (Weight) obj;
		return Float.compare(otherWeight.weight, weight) == 0;
	}
}
