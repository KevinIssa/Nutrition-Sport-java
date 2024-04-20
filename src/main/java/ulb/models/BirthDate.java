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

/**
 * This class represents a BirthDate, which is a ValueObject that holds a LocalDate value.
 * The birth date must not be in the future.
 */
public class BirthDate extends ValueObject<LocalDate> {

	/**
	 * Default constructor for BirthDate.
	 * Initializes the BirthDate with no value.
	 */
	public BirthDate() {
		super();
	}

	/**
	 * Constructor for BirthDate with a specified date.
	 * @param value The date to be held by this BirthDate object.
	 * @throws ValueObjectException If the date is not valid.
	 */
	public BirthDate(LocalDate value) throws ValueObjectException {
		super(value);
	}

	/**
	 * Checks the validity of a specified date.
	 * The date must not be in the future.
	 * @param birthDate The date to check.
	 * @throws BadBirthDateException If the date is in the future.
	 */
	@Override
	protected void checkValidity(LocalDate birthDate) throws BadBirthDateException {
		LocalDate now = LocalDate.now();
		if (birthDate.isAfter(now)) {
			throw new BadBirthDateException("Birthdate cannot be in the future.");
		}
	}

	/**
	 * Converts this BirthDate object to a String.
	 * @return A string representation of this BirthDate object.
	 */
	@Override
	public String toString() {
		return this.value.toString();
	}
}
