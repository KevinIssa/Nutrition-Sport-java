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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ulb.models.enums.Intensity;
import ulb.models.enums.Sport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FoodListSerializer extends JsonSerializer<List<Map.Entry<Food, Integer>>> {
	@Override
	public void serialize(List<Map.Entry<Food, Integer>> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		gen.writeStartObject();
		for (Map.Entry<Food, Integer> entry : value) {
			gen.writeFieldName(entry.getKey().getName());
			gen.writeNumber(entry.getValue());
		}
		gen.writeEndObject();
	}
}

public class Meal implements Consumable {

	private String name;
	@JsonSerialize(using = FoodListSerializer.class)
	private List<Map.Entry<Food, Integer>> ingredients = new ArrayList<>();
	public static final String FILENAME = "Custom Meals.json";

	Meal() {}

	Meal(String name) {
		this.name = name;
	}

	public void addIngredient(Food food, Integer quantity) {
		Map.Entry<Food, Integer> entry = Map.entry(food, quantity);
		this.ingredients.add(entry);
	}

	@Override
	public double getCaloriesConsumedByGrams(int grams) {
		double totalCalories = 0;
		for (Map.Entry<Food, Integer> ingredient : ingredients) {
			totalCalories += ingredient.getKey().getCaloriesConsumedByGrams(ingredient.getValue().intValue());
		}
		return totalCalories;
	}

	public void save(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.registerModule(new JavaTimeModule());
		try {
			mapper.writeValue(new File(FILENAME), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
