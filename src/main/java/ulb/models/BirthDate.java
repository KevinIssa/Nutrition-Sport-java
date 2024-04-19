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

public class BirthDate {

	private LocalDate birthDate;

	public BirthDate() {}

	public BirthDate(LocalDate birthDate) throws BadBirthDateException {
		this.checkValidity(birthDate);
		this.birthDate = birthDate;
	}

	private void checkValidity(LocalDate birthDate) throws BadBirthDateException {
		LocalDate now = LocalDate.now();
		if (birthDate.isAfter(now)) {
			throw new BadBirthDateException("Birthdate cannot be in the future.");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		BirthDate birthDate = (BirthDate) obj;
		return this.birthDate.equals(birthDate.birthDate);
	}

	@Override
	public String toString() {
		return birthDate.toString();
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) throws BadBirthDateException {
		this.checkValidity(birthDate);
		this.birthDate = birthDate;
	}
}
