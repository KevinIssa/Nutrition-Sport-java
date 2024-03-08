/* (C)2024 */
package ulb.models.enums;

public enum Sex {
	MALE,
	FEMALE;

	public static Sex fromString(String sex) {
		switch (sex) {
			case "♂":
				return MALE;
			case "♀":
				return FEMALE;
			default:
				throw new IllegalArgumentException("Invalid sex: " + sex);
		}
	}

	public String toString() {
		switch (this) {
			case MALE:
				return "♂";
			case FEMALE:
				return "♀";
		}
		return null;
	}
}
