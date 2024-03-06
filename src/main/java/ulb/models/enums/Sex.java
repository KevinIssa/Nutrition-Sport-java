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
				throw new IllegalArgumentException("No valid sex given");
		}
	}
}
