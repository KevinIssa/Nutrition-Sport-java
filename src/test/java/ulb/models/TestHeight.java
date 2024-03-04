package ulb.models;

import org.junit.Assert;
import org.junit.Test;


public class TestHeight {

	@Test
	public void inAnOtherDimension() {
		try {
			Height height = new Height(-5);
			Assert.fail();
		} catch (Exception e) {
			//
		}
	}

	@Test
	public void tooHigh() {
		try {
			Height height = new Height(500);
			Assert.fail();
		} catch (Exception e) {
			//
		}
	}
}
