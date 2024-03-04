package ulb;

import ulb.models.ProfileModel;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;


public class ProfileTest {

	@Test
	public void testProfile() {
		 ProfileModel profile = new ProfileModel();
		 profile.setName("John Doe");
		 profile.setWeight(80.0);
		 profile.setHeight(1.80);
		 profile.setBirthDate(new Date());
		 profile.save();
		 assertFalse(profile.load());
		 assertEquals("John Doe", profile.getName());
		 assertEquals(80.0, profile.getWeight(), 0.01);
		 assertEquals(1.80, profile.getHeight(), 0.01);
		 assertNotNull(profile.getBirthDate());
	}
}
