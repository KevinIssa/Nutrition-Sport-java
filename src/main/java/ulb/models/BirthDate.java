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

import java.time.LocalDate;
import ulb.exceptions.BadBirthDateException;
import ulb.exceptions.ValueObjectException;

public class BirthDate extends ValueObject<LocalDate> {

	public BirthDate() {
		super();
	}

	public BirthDate(LocalDate value) throws ValueObjectException {
		super(value);
	}

	@Override
	protected void checkValidity(LocalDate birthDate) throws BadBirthDateException {
		LocalDate now = LocalDate.now();
		if (birthDate.isAfter(now)) {
			throw new BadBirthDateException("Birthdate cannot be in the future.");
		}
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}
