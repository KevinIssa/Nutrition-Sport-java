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

public abstract class ValueObject<Type> {

	protected Type value;

	public ValueObject() {}

	public ValueObject(Type value) throws ValueObjectException {
		this.checkValidity(value);
		this.value = value;
	}

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

	protected abstract void checkValidity(Type value) throws ValueObjectException;

	@Override
	public abstract String toString();

	public Type getValue() {
		return value;
	}

	public void setValue(Type value) throws ValueObjectException {
		this.checkValidity(value);
		this.value = value;
	}
}
