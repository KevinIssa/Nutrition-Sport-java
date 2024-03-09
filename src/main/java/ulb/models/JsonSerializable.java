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

/**
 * This interface defines methods for saving and loading objects to and from JSON files.
 */
public interface JsonSerializable {
	/**
	 * Saves the implementing object to a JSON file.
	 *
	 * @param filename the name of the file to which the object will be saved
	 */
	void saveToFile(String filename);

	/**
	 * Loads an object from a JSON file.
	 *
	 * @param filename the name of the file from which the object will be loaded
	 * @return the loaded object
	 */
	JsonSerializable loadFromFile(String filename);
}
