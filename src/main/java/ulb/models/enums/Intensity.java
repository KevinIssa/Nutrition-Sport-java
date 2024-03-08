/* (C)2024 */
package ulb.models.enums;

public enum Intensity {
	SLOW, // 🙂
	MODERATE, // 😮‍💨
	INTENSE; // 🥵

	public static Intensity fromString(String selectedIntensity) {
		switch (selectedIntensity) {
			case "Slow":
				return SLOW;
			case "Moderate":
				return MODERATE;
			case "Intense":
				return INTENSE;
			default:
				throw new IllegalArgumentException("Invalid intensity: " + selectedIntensity);
		}
	}
}
