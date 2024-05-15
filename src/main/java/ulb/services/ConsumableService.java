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
package ulb.services;

import java.util.List;
import ulb.dtos.ConsumableDTO;
import ulb.models.Consumable;
import ulb.repositories.ConsumableRepository;

public class ConsumableService {

	private final ConsumableRepository consumableRepository;

	public ConsumableService(ConsumableRepository consumableRepository) {
		this.consumableRepository = consumableRepository;
	}

	List<ConsumableDTO> loadConsumables() {
		return this.consumableRepository.loadAll().stream()
				.map(this::convertToConsumableDTO)
				.toList();
	}

	private ConsumableDTO convertToConsumableDTO(Consumable consumable) {
		return new ConsumableDTO(
				consumable.getName(), consumable.getUnit(), consumable.getServingQuantity());
	}

	private Consumable convertToConsumable(ConsumableDTO consumableDTO) {
		// TODO: implement this method
		return null;
	}
}
