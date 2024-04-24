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
package ulb.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;

/**
 * This is an abstract class for a JSON repository.
 * It provides basic CRUD operations for objects of type T.
 * The objects are serialized and deserialized using the Jackson library.
 *
 * @param <T> the type of the objects stored in the repository
 */
public abstract class JSONRepository<T> {
	// The ObjectMapper is a Jackson class used for converting Java objects to/from JSON.
	protected ObjectMapper mapper = new ObjectMapper();

	/**
	 * The constructor initializes the ObjectMapper with the necessary settings.
	 */
	protected JSONRepository() {
		// Enable pretty-printing of the JSON output.
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// Register the JavaTimeModule to support Java 8 date/time types.
		mapper.registerModule(new JavaTimeModule());
	}

	/**
	 * This method saves an object to a JSON file.
	 *
	 * @param object the object to save
	 * @param fileName the name of the file to save the object to
	 * @throws IOException if an I/O error occurs
	 */
	public void save(T object, String fileName) throws IOException {
		mapper.writeValue(new File(fileName), object);
	}

	/**
	 * This method loads an object from a JSON file.
	 *
	 * @param fileName the name of the file to load the object from
	 * @return the loaded object
	 * @throws IOException if an I/O error occurs
	 */
	public T load(String fileName) throws IOException {
		return mapper.readValue(new File(fileName), this.getObjectType());
	}

	/**
	 * This method deletes a JSON file.
	 *
	 * @param fileName the name of the file to delete
	 */
	public void delete(String fileName) {
		new File(fileName).delete();
	}

	/**
	 * This method returns the Class object for type T.
	 * It is used by the Jackson library to know the type of the objects to deserialize.
	 *
	 * @return the Class object for type T
	 */
	protected abstract Class<T> getObjectType();
}
