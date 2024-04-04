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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ulb.models.enums.Sex;

public class TestProfile {

	@Test
	public void testProfile() {
		Profile profile = createProfile("Hugo", "Charels", 60, 175, LocalDate.of(2003, 5, 23));
		profile.save();
		assertEquals(profile, Profile.load());
	}

	@Test
	public void testDeleteProfile() {
		Profile profile = createProfile("lucas", "dubois", 70, 180, LocalDate.now());
		profile.save();
		profile.delete();
		assertFalse(Profile.isCreated());
	}

	private Profile createProfile(
			String firstName, String lastName, int weight, int height, LocalDate birthDate) {
		return new Profile(
				firstName, lastName, Sex.MALE, new Weight(weight), new Height(height), birthDate);
	}
}
