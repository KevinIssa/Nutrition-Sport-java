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

public class Food implements Consumable {

	private String name;
	private int caloriesPer100;
	private String servingQuantity;

	public Food() {}

	public Food(String name, int caloriesPer100, String servingQuantity) {
		this.name = name;
		this.caloriesPer100 = caloriesPer100;
		this.servingQuantity = servingQuantity;
	}

	@Override
	public double getCaloriesConsumedByGrams(int grams) {
		return ((double) this.caloriesPer100 / 100) * grams;
	}

	public String toString() {
		return "Food{"
				+ "name='"
				+ name
				+ '\''
				+ ", caloriesPer100="
				+ caloriesPer100
				+ ", gramsPerServing="
				+ this.servingQuantity
				+ '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCaloriesPer100() {
		return caloriesPer100;
	}

	public void setCaloriesPer100(int caloriesPer100) {
		this.caloriesPer100 = caloriesPer100;
	}

	public String getServingQuantity() {
		return servingQuantity;
	}

	public void setServingQuantity(String servingQuantity) {
		this.servingQuantity = servingQuantity;
	}
}
