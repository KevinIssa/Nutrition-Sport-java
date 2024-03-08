/* (C)2024 */
package ulb.models;

import org.junit.Assert;
import org.junit.Test;

public class TestWeight {

	@Test
	public void inAnOtherDimension() {
		try {
			Weight weight = new Weight(-5);
			Assert.fail();
		} catch (Exception e) {
			//
		}
	}

	@Test
	public void tooFat() {
		try {
			Weight weight = new Weight(500);
			Assert.fail();
		} catch (Exception e) {
			//
		}
	}
}
