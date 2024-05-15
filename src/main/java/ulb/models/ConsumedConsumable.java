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

import java.time.LocalDateTime;

public class ConsumedConsumable {
	private Consumable consumable;
	private double quantityUnit;
	private LocalDateTime consumedTime;

	public ConsumedConsumable(
			Consumable consumable, double quantityUnit, LocalDateTime consumedTime) {
		this.consumable = consumable;
		this.quantityUnit = quantityUnit;
		this.consumedTime = consumedTime;
	}

	public Consumable getConsumable() {
		return consumable;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	public double getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(int quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public LocalDateTime getConsumedTime() {
		return consumedTime;
	}

	public void setConsumedTime(LocalDateTime consumedTime) {
		this.consumedTime = consumedTime;
	}
}
