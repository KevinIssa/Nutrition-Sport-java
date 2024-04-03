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

public class ConsumedFood {
	private String name;
	private int quantity;
	private int calories;

	public ConsumedFood(String name, int quantity, int calories) {
		this.name = name;
		this.quantity = quantity;
		this.calories = calories;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ConsumedFood)) return false;
		ConsumedFood food = (ConsumedFood) obj;
		return food.name.equals(name) && food.quantity == quantity && food.calories == calories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getCalories() {
		return calories;
	}
}
