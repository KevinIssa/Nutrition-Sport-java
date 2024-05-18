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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ulb.exceptions.BadWeightException;
import ulb.exceptions.ValueObjectException;

class WeightTest {
	@Test
	void checkValidityThrowsExceptionForWeightEqualToMin() {
		assertThrows(BadWeightException.class, () -> new Weight(1));
	}

	@Test
	void checkValidityDoesNotThrowExceptionForWeightGreaterThanMin() {
		assertDoesNotThrow(() -> new Weight(1 + 1));
	}

	@Test
	void checkValidityDoesNotThrowExceptionForWeightEqualToMax() {
		assertDoesNotThrow(() -> new Weight(500));
	}

	@Test
	void checkValidityThrowsExceptionForWeightGreaterThanMax() {
		assertThrows(BadWeightException.class, () -> new Weight(500 + 1));
	}

	@Test
	void toStringReturnsCorrectStringForPositiveWeight() throws ValueObjectException {
		Weight weight = new Weight(70f);
		assertEquals("70.0 kg", weight.toString());
	}
}
