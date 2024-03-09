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

public class Height {

	private static final int MIN_HEIGHT = 1; // Minimum height allowed
	private static final int MAX_HEIGHT = 300; // Maximum height allowed

	private float height; // in cm

	public Height(float height) {
		validateHeight(height);
		this.height = height;
	}

	// Validate height value
	private void validateHeight(float height) {
		if (height <= MIN_HEIGHT) {
			throw new IllegalArgumentException("Height must be greater than " + MIN_HEIGHT);
		} else if (height > MAX_HEIGHT) {
			throw new IllegalArgumentException("Height must be less than " + MAX_HEIGHT);
		}
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		validateHeight(height);
		this.height = height;
	}

	@Override
	public String toString() {
		return "Height=" + height;
	}

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
