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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a meal consumed by a user.
 * This class is responsible for managing the consumed meal data and operations related to it.
 * It implements the JsonSerializable interface for serialization and deserialization purposes.
 */
@JsonDeserialize(using = ConsumedMealDeserializer.class)
public class ConsumedMeal implements JsonSerializable {

	// Folder name where the consumed meals data will be stored
	public static final String FOLDER_NAME = "consumed_meals";

	// List of foods consumed in the meal
	@JsonSerialize(using = ConsumedFoodListSerializer.class)
	private List<ConsumedFood> consumedFoods;

	// Date and time when the meal was consumed
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss")
	private LocalDateTime date;

	/**
	 * Default constructor initializing the consumedFoods list and setting the current date and time.
	 */
	public ConsumedMeal() {
		consumedFoods = new ArrayList<>();
		date = LocalDateTime.now().withNano(0);
	}

	/**
	 * Overrides the equals method to compare ConsumedMeal objects based on their date and consumedFoods list.
	 * @param obj Object to be compared with
	 * @return boolean indicating whether the objects are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConsumedMeal)) return false;
		ConsumedMeal meal = (ConsumedMeal) obj;
		return meal.date.equals(date) && meal.consumedFoods.equals(consumedFoods);
	}

	/**
	 * Adds a ConsumedFood object to the consumedFoods list.
	 * @param food ConsumedFood object to be added
	 */
	public void addConsumedFood(ConsumedFood food) {
		consumedFoods.add(food);
	}

	/**
	 * Creates a new ConsumedFood object and adds it to the consumedFoods list.
	 * @param name Name of the food
	 * @param quantity Quantity of the food consumed
	 * @param calories Calories of the food consumed
	 */
	public void addConsumedFood(String name, int quantity, int calories) {
		consumedFoods.add(new ConsumedFood(name, quantity, calories));
	}

	/**
	 * Deletes all the consumed meals data from the storage.
	 */
	public static void clearAllConsumedMeals() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
	}

	/**
	 * Calculates the total calories consumed in the meal.
	 * @return Total calories consumed
	 */
	@JsonIgnore
	public int getCaloriesConsumed() {
		return consumedFoods.stream().mapToInt(ConsumedFood::getCalories).sum();
	}

	/**
	 * Saves the consumed meal data to a file.
	 */
	public void save() {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			folder.mkdir();
		}
		String filename =
				FOLDER_NAME
						+ "/"
						+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
						+ ".json";
		saveToFile(filename);
	}

	/**
	 * Loads the consumed meal data from a file.
	 * @param filename Name of the file from which the data is to be loaded
	 * @return ConsumedMeal object containing the loaded data
	 */
	public static ConsumedMeal load(String filename) {
		return (ConsumedMeal) new ConsumedMeal().loadFromFile(filename);
	}

	/**
	 * Getter for the date field.
	 * @return Date and time when the meal was consumed
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Setter for the date field.
	 * @param date Date and time when the meal was consumed
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * Saves the consumed meal data to a file.
	 * @param filename Name of the file where the data is to be saved
	 */
	public void saveToFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(filename), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the consumed meal data from a file.
	 * @param filename Name of the file from which the data is to be loaded
	 * @return JsonSerializable object containing the loaded data
	 */
	public JsonSerializable loadFromFile(String filename) {
		File file = new File(filename);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(file, ConsumedMeal.class);
		} catch (IOException e) {
			return null;
		}
	}
}

/**
 * Serializer class for the consumedFoods list.
 */
class ConsumedFoodListSerializer extends JsonSerializer<List<ConsumedFood>> {

	/**
	 * Serializes the consumedFoods list to JSON format.
	 * @param consumedFoods List of ConsumedFood objects to be serialized
	 * @param jsonGenerator JsonGenerator object used for writing JSON content
	 * @param serializerProvider SerializerProvider object used for accessing secondary serializers
	 */
	@Override
	public void serialize(
			List<ConsumedFood> consumedFoods,
			JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartArray();
		for (ConsumedFood consumedFood : consumedFoods) {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStringField("name", consumedFood.getName());
			jsonGenerator.writeNumberField("quantity", consumedFood.getQuantity());
			jsonGenerator.writeNumberField("calories", consumedFood.getCalories());
			jsonGenerator.writeEndObject();
		}
		jsonGenerator.writeEndArray();
	}
}

/**
 * Deserializer class for the ConsumedMeal object.
 */
class ConsumedMealDeserializer extends StdDeserializer<ConsumedMeal> {

	/**
	 * Default constructor specifying the type to be deserialized.
	 */
	public ConsumedMealDeserializer() {
		super(ConsumedMeal.class);
	}

	/**
	 * Deserializes the JSON content into a ConsumedMeal object.
	 * @param jp JsonParser object used for reading JSON content
	 * @param ctxt DeserializationContext object used for accessing contextual information during deserialization
	 * @return ConsumedMeal object containing the deserialized data
	 */
	@Override
	public ConsumedMeal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = jp.getCodec().readTree(jp);
		ConsumedMeal meal = new ConsumedMeal();
		JsonNode consumedFoodsNode = node.get("consumedFoods");
		if (consumedFoodsNode.isArray()) {
			for (JsonNode element : consumedFoodsNode) {
				String name = element.get("name").asText();
				int quantity = element.get("quantity").asInt();
				int calories = element.get("calories").asInt();
				meal.addConsumedFood(name, quantity, calories);
			}
		}
		String date = node.get("date").asText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		meal.setDate(LocalDateTime.parse(date, formatter));
		return meal;
	}
}
