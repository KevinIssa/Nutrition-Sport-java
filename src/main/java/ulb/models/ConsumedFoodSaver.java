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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConsumedFoodSaver<T> {

	private String FOLDER_NAME = "src/main/resources";
	private String filename = "consumedFood";
	private ArrayList<ArrayList<String>> consumedFoods = new ArrayList<>();

	public ConsumedFoodSaver(ArrayList<ArrayList<String>> consumedFoods) {
		this.consumedFoods = consumedFoods;
	}

	String getFolderName() {
		return FOLDER_NAME;
	}

	private String getUniqueFilename(String baseFilename) {

		String filename = baseFilename;
		File file = new File(FOLDER_NAME + "/" + filename + ".json");
		int counter = 1;

		while (file.exists()) {
			filename = baseFilename + "(" + counter + ")";
			file = new File(FOLDER_NAME + "/" + filename + ".json");
			counter++;
		}

		return filename;
	}

	public void save(String alt_filename) {
		filename = alt_filename;
		save();
	}

	/**
	 * This method is responsible for saving the consumed food data to a JSON file.
	 */
	public void save() {
		// Ensure the directory exists
		// If the directory does not exist, it is created
		File folder = new File(FOLDER_NAME);
		String formatedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		if (!folder.exists()) {
			folder.mkdir();
		}

		// Create an ObjectMapper to convert Java objects to JSON and vice versa
		ObjectMapper mapper = new ObjectMapper();
		// Enable pretty printing of the JSON output
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			// Create a list to hold the JSON objects
			ArrayList<Map<String, Object>> jsonList = new ArrayList<>();
			// Iterate over the consumed food data
			for (ArrayList<String> consumedfoodData : consumedFoods) {
				// Create a map to hold the JSON object
				Map<String, Object> jsonMap = new LinkedHashMap<>();
				// Add the name, quantity, calories, and date to the map
				jsonMap.put("name", consumedfoodData.get(0));
				jsonMap.put("quantity", consumedfoodData.get(1));
				jsonMap.put("calories", consumedfoodData.get(2));
				// Format the date to "day-month-year"
				jsonMap.put("date", formatedDate);
				// Add the map to the list
				jsonList.add(jsonMap);
			}

			// Get a unique filename
			String uniqueFilename = getUniqueFilename("food " + formatedDate);

			// Write to the JSON file
			mapper.writeValue(new File(FOLDER_NAME + "/" + uniqueFilename + ".json"), jsonList);

		} catch (IOException e) {
			// Print the stack trace for any IOExceptions
			e.printStackTrace();
		}
	}
}
