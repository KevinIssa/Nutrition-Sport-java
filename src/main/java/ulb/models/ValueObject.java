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

import ulb.exceptions.ValueObjectException;

/**
 * This abstract class represents a ValueObject, a small object that represents a simple entity whose equality isn't based on identity.
 * @param <T> The type of the value that this ValueObject holds.
 */
public abstract class ValueObject<T> {

	protected T value;

	/**
	 * Default constructor for ValueObject.
	 */
	protected ValueObject() {}

	/**
	 * Constructor for ValueObject with a value.
	 * @param value The value to be held by this ValueObject.
	 * @throws ValueObjectException If the value is not valid.
	 */
	protected ValueObject(T value) throws ValueObjectException {
		this.checkValidity(value);
		this.value = value;
	}

	/**
	 * Checks if this ValueObject is equal to another object.
	 * @param obj The object to compare with.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		ValueObject<?> valueObject = (ValueObject<?>) obj;
		return this.value.equals(valueObject.value);
	}

	/**
	 * Abstract method to check the validity of a value.
	 * @param value The value to check.
	 * @throws ValueObjectException If the value is not valid.
	 */
	protected abstract void checkValidity(T value) throws ValueObjectException;

	/**
	 * Abstract method to convert this ValueObject to a String.
	 * @return A string representation of this ValueObject.
	 */
	@Override
	public abstract String toString();

	/**
	 * Gets the value held by this ValueObject.
	 * @return The value held by this ValueObject.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Sets the value held by this ValueObject.
	 * @param value The value to set.
	 * @throws ValueObjectException If the value is not valid.
	 */
	public void setValue(T value) throws ValueObjectException {
		this.checkValidity(value);
		this.value = value;
	}
}
