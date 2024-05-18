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

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ulb.exceptions.BadBirthDateException;
import ulb.exceptions.ValueObjectException;

class BirthDateTest {

	@Test
	void checkValidityWithPastDate() throws ValueObjectException {
		BirthDate birthDate = new BirthDate(LocalDate.now().minusDays(1));
		birthDate.checkValidity(birthDate.getValue());
	}

	@Test
	void checkValidityWithCurrentDate() throws ValueObjectException {
		BirthDate birthDate = new BirthDate(LocalDate.now());
		birthDate.checkValidity(birthDate.getValue());
	}

	@Test
	void checkValidityWithFutureDate() {
		assertThrows(
				BadBirthDateException.class,
				() -> {
					BirthDate birthDate = new BirthDate(LocalDate.now().plusDays(1));
					birthDate.checkValidity(birthDate.getValue());
				});
	}

	@Test
	void toStringReturnsCorrectFormatForValidDate() throws ValueObjectException {
		BirthDate birthDate = new BirthDate(LocalDate.of(2000, 1, 1));
		assertEquals("2000-01-01", birthDate.toString());
	}
}
