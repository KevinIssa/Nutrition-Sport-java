package ulb.models;

import org.junit.Test;
import ulb.models.enums.Sex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;

public class TestProfile {

	@Test
	public void testProfile() {
		Profile profile = new Profile("Hugo", "Charels", Sex.MALE, new Weight(60), new Height(175), LocalDate.of(2003, 5, 23));
		profile.save();
		Profile loadedProfile = Profile.load();
		assertEquals(profile, loadedProfile);
	}
	@Test
	public  void testDeleteProfile() {
		Profile profile = new Profile("lucas", "dubois", Sex.MALE, new Weight(70), new Height(180), java.time.LocalDate.now());
		profile.save();
		profile.delete();
		assertFalse(Profile.isCreated());

	}

}
