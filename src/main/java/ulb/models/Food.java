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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;

@JsonSerialize(using = FoodSerializer.class)
public class Food implements Consumable {

	private String name;
	private int caloriesPer100;
	private int caloriesPerServing;
	private String servingQuantity;

	public Food() {}

	public Food(String name, int caloriesPer100, int caloriesPerServing, String servingQuantity) {
		this.name = name;
		this.caloriesPer100 = caloriesPer100;
		this.caloriesPerServing = caloriesPerServing;
		this.servingQuantity = servingQuantity;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Food food = (Food) o;
		return caloriesPer100 == food.caloriesPer100
				&& name.equals(food.name)
				&& servingQuantity.equals(food.servingQuantity);
	}

	@JsonIgnore
	@Override
	public int getCaloriesConsumed() {
		return getCaloriesConsumedByServing(1);
	}

	@Override
	public int getCaloriesConsumedByGrams(int grams) {
		return (this.caloriesPer100 / 100) * grams;
	}

	@Override
	public int getCaloriesConsumedByServing(int servings) {
		return this.caloriesPerServing * servings;
	}

	public String toString() {
		return "Food{"
				+ "name='"
				+ name
				+ '\''
				+ ", caloriesPer100="
				+ caloriesPer100
				+ ", gramsPerServing="
				+ this.servingQuantity
				+ '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCaloriesPer100() {
		return caloriesPer100;
	}

	public void setCaloriesPer100(int caloriesPer100) {
		this.caloriesPer100 = caloriesPer100;
	}

	public int getCaloriesPerServing() {
		return caloriesPerServing;
	}

	public void setCaloriesPerServing(int caloriesPerServing) {
		this.caloriesPerServing = caloriesPerServing;
	}

	public String getServingQuantity() {
		return servingQuantity;
	}

	public void setServingQuantity(String servingQuantity) {
		this.servingQuantity = servingQuantity;
	}
}

class FoodSerializer extends JsonSerializer<Food> {
	@Override
	public void serialize(
			Food food, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", food.getName());
		jsonGenerator.writeNumberField("caloriesPer100", food.getCaloriesPer100());
		jsonGenerator.writeNumberField("caloriesPerServing", food.getCaloriesPerServing());
		jsonGenerator.writeStringField("servingQuantity", food.getServingQuantity());
		jsonGenerator.writeEndObject();
	}
}
