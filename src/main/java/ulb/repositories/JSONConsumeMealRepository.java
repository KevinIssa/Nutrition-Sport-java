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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import ulb.models.ConsumedFood;
import ulb.models.ConsumedMeal;

public class JSONConsumeMealRepository extends JSONRepository<ConsumedMeal>
		implements ConsumeMealRepository {
	private static final String FOLDER_NAME = "consumed_meals";

	public JSONConsumeMealRepository() {
		super();
		this.mapper.registerModule(
				new SimpleModule()
						.addDeserializer(ConsumedMeal.class, new ConsumedMealDeserializer()));
		this.mapper.addMixIn(ConsumedMeal.class, ConsumedMealMixin.class);
	}

	@Override
	public void save(ConsumedMeal consumedMeal) {
		File folder = new File(FOLDER_NAME);
		if (!folder.exists()) {
			// logger.info("Creating activities folder");
			folder.mkdir();
		}
		String fileName =
				STR."\{
						FOLDER_NAME}/\{
						consumedMeal
								.getDate()
								.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"))}.json";
		try {
			this.save(consumedMeal, fileName);
		} catch (IOException e) {
			// TODO: Handle this exception
			e.printStackTrace();
			// logger.error("Error saving consumed meal to file: " + fileName);
		}
	}

	@Override
	public List<ConsumedMeal> loadAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		List<ConsumedMeal> consumedMeals = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				try {
					consumedMeals.add(this.load(file.getPath()));
				} catch (IOException _) {
					//
					// logger.error("Error loading consumed meal from file: " + file.getPath());
				}
			}
		}
		return consumedMeals;
	}

	@Override
	public void delete(ConsumedMeal consumedMeal) {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				try {
					ConsumedMeal loadedConsumedMeal = this.load(file.getPath());
					if (consumedMeal.equals(loadedConsumedMeal)) {
						file.delete();
						break;
					}
				} catch (IOException _) {
					// logger.error("Error loading consumed meal from file: " + file.getPath());
				}
			}
		}
	}

	@Override
	public void delete(ConsumedFood consumedFood, LocalDateTime date) {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		boolean isDeleted = false;
		if (files != null) {
			for (File file : files) {
				try {
					ConsumedMeal loadedConsumedMeal = this.load(file.getPath());
					if (loadedConsumedMeal.getDate().equals(date)){
						for (ConsumedFood Food : loadedConsumedMeal.getConsumedFoods()){
							if (consumedFood.equals(Food)) {
								loadedConsumedMeal.getConsumedFoods().remove(Food);
								isDeleted = true;
								break;
							}
						}
					}
					if (loadedConsumedMeal.getConsumedFoods().isEmpty() && isDeleted) {
						file.delete();
						break;
					} else if (isDeleted){
						file.delete();
						this.save(loadedConsumedMeal, file.getPath());
						break;
					}

				} catch (IOException _) {
					// logger.error("Error loading consumed meal from file: " + file.getPath());
				}
			}
		}
	}

	@Override
	public void deleteAll() {
		File folder = new File(FOLDER_NAME);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
	}

	@Override
	protected Class<ConsumedMeal> getObjectType() {
		return ConsumedMeal.class;
	}
}

/**
 * Mixin class for the ConsumedMeal object.
 */
abstract class ConsumedMealMixin {
	@JsonIgnore
	abstract int getCaloriesConsumed();
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
				String text = element.get("unit").asText();
				meal.addConsumedFood(name, quantity, calories, text);
			}
		}
		JsonNode dateNode = node.get("date");
		if (dateNode.isArray() && dateNode.size() == 5) {
			int year = dateNode.get(0).asInt();
			int month = dateNode.get(1).asInt();
			int day = dateNode.get(2).asInt();
			int hour = dateNode.get(3).asInt();
			int minute = dateNode.get(4).asInt();
			meal.setDate(LocalDateTime.of(year, month, day, hour, minute));
		}
		return meal;
	}
}
