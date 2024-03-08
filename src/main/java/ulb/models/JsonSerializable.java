/* (C)2024 */
package ulb.models;

public interface JsonSerializable {
	void saveToFile(String filename);

	JsonSerializable loadFromFile(String filename);
}
