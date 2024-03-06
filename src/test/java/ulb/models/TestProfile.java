package ulb.models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class TestProfile {

	@Test
	public void testProfile() {
		Profile profile = new Profile("John", "♂", 80, 180, LocalDate.of(1990, 1, 1));
		profile.save();
		Profile loadedProfile = Profile.load();
		assertEquals(profile, loadedProfile);
	}

}
