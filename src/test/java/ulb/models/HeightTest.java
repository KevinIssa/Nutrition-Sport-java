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
import ulb.exceptions.BadHeightException;
import ulb.exceptions.ValueObjectException;

class HeightTest {

	@Test
	void checkValidityThrowsExceptionForHeightLessThanMin() {
		assertThrows(BadHeightException.class, () -> new Height(1 - 0.1f));
	}

	@Test
	void checkValidityThrowsExceptionForHeightGreaterThanMax() {
		assertThrows(BadHeightException.class, () -> new Height(300 + 0.1f));
	}

	@Test
	void checkValidityDoesNotThrowExceptionForValidHeight() {
		assertDoesNotThrow(() -> new Height((1 + 300) / 2));
	}

	@Test
	void toStringReturnsCorrectFormatForPositiveHeight() throws ValueObjectException {
		Height height = new Height(150.5f);
		assertTrue(height.toString().equals("150.50 cm") || height.toString().equals("150,50 cm"));
	}
}
