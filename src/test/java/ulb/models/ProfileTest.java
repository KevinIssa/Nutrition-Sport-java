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
import ulb.enums.Sex;
import ulb.exceptions.*;

class ProfileTest {

	@Test
	void constructProfileWithValidAttributes() {
		assertDoesNotThrow(
				() -> new Profile("Jane", "Doe", Sex.FEMALE, 60, 165, LocalDate.of(1995, 1, 1)));
	}

	@Test
	void constructProfileWithInvalidFirstName() {
		assertThrows(
				BadNameException.class,
				() -> new Profile("", "Doe", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1)));
	}

	@Test
	void constructProfileWithInvalidLastName() {
		assertThrows(
				BadNameException.class,
				() -> new Profile("John", "", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1)));
	}

	@Test
	void constructProfileWithInvalidWeight() {
		assertThrows(
				BadWeightException.class,
				() -> new Profile("John", "Doe", Sex.MALE, -70, 180, LocalDate.of(1990, 1, 1)));
	}

	@Test
	void constructProfileWithInvalidHeight() {
		assertThrows(
				BadHeightException.class,
				() -> new Profile("John", "Doe", Sex.MALE, 70, -180, LocalDate.of(1990, 1, 1)));
	}

	@Test
	void constructProfileWithInvalidBirthDate() {
		assertThrows(
				BadBirthDateException.class,
				() -> new Profile("John", "Doe", Sex.MALE, 70, 180, LocalDate.of(2090, 1, 1)));
	}

	@Test
	void equalsReturnsFalseForNull() throws ValueObjectException {
		Profile profile = new Profile("John", "Doe", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1));
		assertFalse(profile.equals(null));
	}

	@Test
	void equalsReturnsFalseForDifferentClass() throws ValueObjectException {
		Profile profile = new Profile("John", "Doe", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1));
		assertFalse(profile.equals(new Object()));
	}

	@Test
	void equalsReturnsTrueForSameData() throws ValueObjectException {
		Profile profile1 = new Profile("John", "Doe", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1));
		Profile profile2 = new Profile("John", "Doe", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1));
		assertTrue(profile1.equals(profile2));
	}

	@Test
	void equalsReturnsFalseForDifferentData() throws ValueObjectException {
		Profile profile1 = new Profile("John", "Doe", Sex.MALE, 70, 180, LocalDate.of(1990, 1, 1));
		Profile profile2 = new Profile("Jane", "Doe", Sex.FEMALE, 60, 165, LocalDate.of(1995, 1, 1));
		assertFalse(profile1.equals(profile2));
	}
}
