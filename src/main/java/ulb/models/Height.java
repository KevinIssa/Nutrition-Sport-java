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

	private float height; // in cm

	public Height(float height) {
		this.height = height;
		if (height <= 0) {
			throw new IllegalArgumentException("Height must be greater than 0");
			// mec tes a l'envers
		} else if (height > 300) {
			throw new IllegalArgumentException("Height must be less than 300");
			// redescend un peu
		}
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String toString() {
		return "Height=" + height;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Height) {
			Height height = (Height) obj;
			return this.height == height.getHeight();
		}
		return false;
	}

	public int hashCode() {
		return (int) height;
	}
}
