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

import ulb.exceptions.BadNameException;
import ulb.exceptions.ValueObjectException;

public class Name extends ValueObject<String> {

	public Name() {
		super();
	}

	public Name(String name) throws ValueObjectException {
		super(name);
	}

	protected void checkValidity(String name) throws BadNameException {
		if (name == null || name.isEmpty()) {
			throw new BadNameException("Name cannot be null or empty.");
		} else if (!name.matches("^[a-zA-Z- ]+$")) {
			throw new BadNameException("Name can only contain letters, - and spaces.");
		}
	}

	@Override
	public String toString() {
		return this.value;
	}
}
