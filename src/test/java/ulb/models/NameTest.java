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
import ulb.exceptions.BadNameException;
import ulb.exceptions.ValueObjectException;

class NameTest {

	@Test
	void checkValidityThrowsExceptionForNullName() {
		assertThrows(BadNameException.class, () -> new Name(null));
	}

	@Test
	void checkValidityThrowsExceptionForEmptyName() {
		assertThrows(BadNameException.class, () -> new Name(""));
	}

	@Test
	void checkValidityThrowsExceptionForNameWithNumbers() {
		assertThrows(BadNameException.class, () -> new Name("John123"));
	}

	@Test
	void checkValidityThrowsExceptionForNameWithSpecialCharacters() {
		assertThrows(BadNameException.class, () -> new Name("John@Doe"));
	}

	@Test
	void checkValidityAcceptsNameWithLettersHyphensAndSpaces() {
		assertDoesNotThrow(() -> new Name("John-Doe"));
	}

	@Test
	void toStringReturnsEmptyStringForNameWithNoValue() {
		Name name = new Name();
		assertEquals(null, name.toString());
	}

	@Test
	void toStringReturnsCorrectStringForNameWithValue() throws ValueObjectException {
		Name name = new Name("John-Doe");
		assertEquals("John-Doe", name.toString());
	}
}
