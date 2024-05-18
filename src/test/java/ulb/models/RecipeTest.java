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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ulb.enums.Unit;

class RecipeTest {

	@Test
	void equalsReturnsTrueForSameObject() {
		Recipe recipe1 = new Recipe("Pizza");
		assertTrue(recipe1.equals(recipe1));
	}

	@Test
	void equalsReturnsFalseForNull() {
		Recipe recipe1 = new Recipe("Pizza");
		assertFalse(recipe1.equals(null));
	}

	@Test
	void equalsReturnsFalseForDifferentClass() {
		Recipe recipe1 = new Recipe("Pizza");
		assertFalse(recipe1.equals(new Object()));
	}

	@Test
	void equalsReturnsTrueForSameRecipeNameAndIngredients() {
		Recipe recipe1 = new Recipe("Pizza");
		Recipe recipe2 = new Recipe("Pizza");
		assertTrue(recipe1.equals(recipe2));
	}

	@Test
	void equalsReturnsFalseForDifferentRecipeName() {
		Recipe recipe1 = new Recipe("Pizza");
		Recipe recipe2 = new Recipe("Burger");
		assertFalse(recipe1.equals(recipe2));
	}

	@Test
	void equalsReturnsFalseForDifferentIngredients() {
		Recipe recipe1 = new Recipe("Pizza");
		Recipe recipe2 = new Recipe("Pizza");
		recipe2.addIngredient(new Food("Cheese", 100, 100, "100g", Unit.GRAMS), 100);
		assertFalse(recipe1.equals(recipe2));
	}

	@Test
	void addIngredient() {
		Recipe recipe = new Recipe("Pizza");
		Food food1 = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
		Food food2 = new Food("Tomato", 20, 20, "100g", Unit.GRAMS);
		recipe.addIngredient(food1, 100);
		recipe.addIngredient(food2, 50);
		assertEquals(2, recipe.getIngredients().size());
	}

	@Test
	void getCaloriesConsumedByUnitReturnsCorrectValue() {
		Recipe recipe = new Recipe("Pizza");
		Food food = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
		recipe.addIngredient(food, 100);
		assertEquals(100, recipe.getCaloriesConsumedByUnit(100));
	}

	@Test
	void getGramsForServingReturnsCorrectValue() {
		Recipe recipe = new Recipe("Pizza");
		Food food = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
		recipe.addIngredient(food, 100);
		assertEquals(100, recipe.getGramsForServing(1));
	}

	@Test
	void getCaloriesConsumedByServingReturnsCorrectValue() {
		Recipe recipe = new Recipe("Pizza");
		Food food = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
		recipe.addIngredient(food, 100);
		assertEquals(100, recipe.getCaloriesConsumedByServing(1));
	}

	@Test
	void toStringReturnsCorrectFormatForEmptyRecipe() {
		Recipe recipe = new Recipe();
		assertEquals("Recipe" + "{name='null', ingredients=[]}", recipe.toString());
	}

	@Test
	void toStringReturnsCorrectFormatForRecipeWithNameOnly() {
		Recipe recipe = new Recipe("Pizza");
		assertEquals("Recipe" + "{name='Pizza', ingredients=[]}", recipe.toString());
	}

	@Test
	void toStringReturnsCorrectFormatForRecipeWithMultipleIngredients() {
		Recipe recipe = new Recipe("Pizza");
		Food food1 = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
		Food food2 = new Food("Tomato", 20, 20, "100g", Unit.GRAMS);
		recipe.addIngredient(food1, 100);
		recipe.addIngredient(food2, 50);
		assertEquals(
				"Recipe"
						+ "{name='Pizza', ingredients=[Food{name='Cheese', caloriesPer100=100.0,"
						+ " gramsPerServing=100g}=100.0, Food{name='Tomato', caloriesPer100=20.0, "
						+ "gramsPerServing=100g}=50.0]}",
				recipe.toString());
	}
}
