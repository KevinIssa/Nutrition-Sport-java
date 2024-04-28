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

/**
 * This class represents a Name, which is a ValueObject that holds a String value.
 * The name must be non-null, non-empty, and can only contain letters, hyphens (-), and spaces.
 */
public class Name extends ValueObject<String> {

	/**
	 * Default constructor for Name.
	 * Initializes the Name with no value.
	 */
	public Name() {
		super();
	}

	/**
	 * Constructor for Name with a specified name.
	 * @param name The name to be held by this Name object.
	 * @throws ValueObjectException If the name is not valid.
	 */
	public Name(String name) throws ValueObjectException {
		super(name);
	}

	/**
	 * Checks the validity of a specified name.
	 * The name must be non-null, non-empty, and can only contain letters, hyphens (-), and spaces.
	 * @param name The name to check.
	 * @throws BadNameException If the name is not valid.
	 */
	protected void checkValidity(String name) throws BadNameException {
		if (name == null || name.isEmpty()) {
			throw new BadNameException("Nom ne peut pas être vide.");
		} else if (!name.matches("^[a-zA-Z- ]+$")) {
			throw new BadNameException(
					"le nom ne peut contenir que des lettres, des tirets (-) et des espaces.");
		}
	}

	/**
	 * Converts this Name object to a String.
	 * @return A string representation of this Name object.
	 */
	@Override
	public String toString() {
		return this.value;
	}
}
