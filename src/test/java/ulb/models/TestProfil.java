package ulb.models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class TestProfil {

	@Test
	public void testProfile() {
		Profil profil = new Profil("Hugo", "Charels", "♂", 60, 175, LocalDate.of(2003, 5, 23));
		profil.save();
		Profil loadedProfil = Profil.load();
		assertEquals(profil, loadedProfil);
	}

}
