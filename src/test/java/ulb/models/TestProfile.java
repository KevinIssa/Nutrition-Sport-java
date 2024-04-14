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

import java.io.File;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulb.models.enums.Sex;

public class TestProfile {
	private Profile profile;

	@BeforeEach
	public void setUp() {
		profile =
				new Profile(
						"John",
						"Doe",
						Sex.MALE,
						new Weight(70),
						new Height(180),
						LocalDate.of(1990, 1, 1));
	}

	@Test
	public void profileAttributesAreCorrectAfterCreation() {
		assertEquals("John", profile.getFirstName());
		assertEquals("Doe", profile.getLastName());
		assertEquals(Sex.MALE, profile.getSex());
		assertEquals(70, profile.getWeight());
		assertEquals(180, profile.getHeight());
		assertEquals(LocalDate.of(1990, 1, 1), profile.getBirthDate());
	}

	@Test
	public void profileAttributesCanBeUpdated() {
		profile.setFirstName("Jane");
		profile.setLastName("Smith");
		profile.setSex(Sex.FEMALE);
		profile.setWeight(60);
		profile.setHeight(170);
		profile.setBirthDate(LocalDate.of(1995, 1, 1));

		assertEquals("Jane", profile.getFirstName());
		assertEquals("Smith", profile.getLastName());
		assertEquals(Sex.FEMALE, profile.getSex());
		assertEquals(60, profile.getWeight());
		assertEquals(170, profile.getHeight());
		assertEquals(LocalDate.of(1995, 1, 1), profile.getBirthDate());
	}

	@Test
	public void profileCanBeSavedAndLoaded() {
		profile.save();
		Profile loadedProfile = Profile.load();

		assertEquals(profile, loadedProfile);
	}

	@Test
	public void profileCanBeDeleted() {
		profile.save();
		Profile.delete();

		assertFalse(Profile.isCreated());
	}

	@Test
	public void profileImageCanBeSaved() {
		String imagePath =
				"https://t3.ftcdn.net/jpg/03/53/11/00/360_F_353110097_nbpmfn9iHlxef4EDIhXB1tdTD0lcWhG9.jpg";
		Profile.saveImage(imagePath);

		File imageFile = new File(Profile.IMAGE_PATH);
		assertTrue(imageFile.exists());
	}
}
