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

	private float weight; // in kg

	public Weight(float weight) {
		if (weight <= 0) {
			throw new IllegalArgumentException("Weight must be greater than 0");
			// tu dois être dans une autre dimension
		} else if (weight > 300) {
			throw new IllegalArgumentException("Weight must be less than 300");
			// tes trop gros mec
		}
		this.weight = weight;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String toString() {
		return "Weight=" + weight;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Weight) {
			Weight weight = (Weight) obj;
			return this.weight == weight.getWeight();
		}
		return false;
	}

	public int hashCode() {
		return (int) weight;
	}
}
