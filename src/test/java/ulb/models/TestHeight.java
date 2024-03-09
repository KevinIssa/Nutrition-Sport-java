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
