package ulb.models;

public class Weight {

	private float weight; // in kg

	public Weight(float weight) {
		this.weight = weight;
		if (weight <= 0) {
			throw new IllegalArgumentException("Weight must be greater than 0");
			// tu dois être dans une autre dimension
		} else if (weight > 300) {
			throw new IllegalArgumentException("Weight must be less than 300");
			// tes trop gros mec
		}
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
