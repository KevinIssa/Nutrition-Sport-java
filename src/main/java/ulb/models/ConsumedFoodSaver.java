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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConsumedFoodSaver<T> {

	private String FOLDER_NAME = "src/main/resources";
	private String filename = "consumedFood";
	private ArrayList<T> consumedFoods = new ArrayList<>();

	public ConsumedFoodSaver(ArrayList<T> consumedFoods) {
		this.consumedFoods = consumedFoods;
	}

	String getFolderName() {
		return FOLDER_NAME;
	}

	public void save(String alt_filename) {
		filename = alt_filename;
		save();
	}

	public void save() {

		// Ensure the directory exists
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			folder.mkdir();
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {

			// Write to the JSON file
			mapper.writeValue(new File(FOLDER_NAME + "/" + filename + ".json"), consumedFoods);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addConsumedFood(T food) {
		consumedFoods.add(food);
	}

	public void setConsumedFoodsList(ArrayList<T> consumedFoods) {
		this.consumedFoods = consumedFoods;
	}

	public void setFolderName(String path) {
		FOLDER_NAME = path;
	}


}
