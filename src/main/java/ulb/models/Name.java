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

public class Name {

	private String name;

	public Name() {}

	public Name(String name) throws BadNameException {
		this.checkValidity(name);
		this.name = name;
	}

	private void checkValidity(String name) throws BadNameException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty.");
		}
		if (!name.matches("^[a-zA-Z- ]+$")) {
			throw new BadNameException("Name can only contain letters, - and spaces.");
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
		Name name = (Name) obj;
		return this.name.equals(name.name);
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws BadNameException {
		this.checkValidity(name);
		this.name = name;
	}
}
