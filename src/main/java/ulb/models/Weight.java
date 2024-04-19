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
 * Represents the weight of a person.
 */
public class Weight extends ValueObject<Float> {

	private static final int MIN_WEIGHT = 1; // Minimum weight allowed
	private static final int MAX_WEIGHT = 500; // Maximum weight allowed

	public Weight() {
		super();
	}

	public Weight(float weight) throws ValueObjectException {
		super(weight);
	}

	protected void checkValidity(Float weight) throws IllegalArgumentException, BadWeightException {
		if (weight <= MIN_WEIGHT) {
			throw new BadWeightException(STR."Weight must be greater than \{MIN_WEIGHT}");
		} else if (weight > MAX_WEIGHT) {
			throw new BadWeightException(STR."Weight must be less than \{MAX_WEIGHT}");
		}
	}

	@Override
	public String toString() {
		return STR."\{this.value} kg";
	}
}
