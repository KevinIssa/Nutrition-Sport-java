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

public class Weight {

	private static final int MIN_WEIGHT = 1; // Minimum weight allowed
	private static final int MAX_WEIGHT = 500; // Maximum weight allowed

	private float weight; // in kg

	public Weight(float weight) {
		validateWeight(weight);
		this.weight = weight;
	}

	// Validate weight value
	private void validateWeight(float weight) {
		if (weight <= MIN_WEIGHT) {
			throw new IllegalArgumentException("Weight must be greater than " + MIN_WEIGHT);
		} else if (weight > MAX_WEIGHT) {
			throw new IllegalArgumentException("Weight must be less than " + MAX_WEIGHT);
		}
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		validateWeight(weight);
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Weight=" + weight;
	}

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
