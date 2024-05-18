package ulb.models;

import org.junit.jupiter.api.Test;
import ulb.enums.Unit;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void equalsReturnsTrueForSameObject() {
        Meal meal1 = new Meal("Pizza");
        assertTrue(meal1.equals(meal1));
    }

    @Test
    void equalsReturnsFalseForNull() {
        Meal meal1 = new Meal("Pizza");
        assertFalse(meal1.equals(null));
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        Meal meal1 = new Meal("Pizza");
        assertFalse(meal1.equals(new Object()));
    }

    @Test
    void equalsReturnsTrueForSameMealNameAndIngredients() {
        Meal meal1 = new Meal("Pizza");
        Meal meal2 = new Meal("Pizza");
        assertTrue(meal1.equals(meal2));
    }

    @Test
    void equalsReturnsFalseForDifferentMealName() {
        Meal meal1 = new Meal("Pizza");
        Meal meal2 = new Meal("Burger");
        assertFalse(meal1.equals(meal2));
    }

    @Test
    void equalsReturnsFalseForDifferentIngredients() {
        Meal meal1 = new Meal("Pizza");
        Meal meal2 = new Meal("Pizza");
        meal2.addIngredient(new Food("Cheese", 100, 100, "100g", Unit.GRAMS), 100);
        assertFalse(meal1.equals(meal2));
    }


    @Test
    void addIngredient() {
        Meal meal = new Meal("Pizza");
        Food food1 = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
        Food food2 = new Food("Tomato", 20, 20, "100g", Unit.GRAMS);
        meal.addIngredient(food1, 100);
        meal.addIngredient(food2, 50);
        assertEquals(2, meal.getIngredients().size());
    }


    @Test
    void getCaloriesConsumedByUnitReturnsCorrectValue() {
        Meal meal = new Meal("Pizza");
        Food food = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
        meal.addIngredient(food, 100);
        assertEquals(100, meal.getCaloriesConsumedByUnit(100));
    }

    @Test
    void getGramsForServingReturnsCorrectValue() {
        Meal meal = new Meal("Pizza");
        Food food = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
        meal.addIngredient(food, 100);
        assertEquals(100, meal.getGramsForServing(1));
    }

    @Test
    void getCaloriesConsumedByServingReturnsCorrectValue() {
        Meal meal = new Meal("Pizza");
        Food food = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
        meal.addIngredient(food, 100);
        assertEquals(100, meal.getCaloriesConsumedByServing(1));
    }


    @Test
    void toStringReturnsCorrectFormatForEmptyMeal() {
        Meal meal = new Meal();
        assertEquals("Meal{name='null', ingredients=[]}", meal.toString());
    }

    @Test
    void toStringReturnsCorrectFormatForMealWithNameOnly() {
        Meal meal = new Meal("Pizza");
        assertEquals("Meal{name='Pizza', ingredients=[]}", meal.toString());
    }

    @Test
    void toStringReturnsCorrectFormatForMealWithMultipleIngredients() {
        Meal meal = new Meal("Pizza");
        Food food1 = new Food("Cheese", 100, 100, "100g", Unit.GRAMS);
        Food food2 = new Food("Tomato", 20, 20, "100g", Unit.GRAMS);
        meal.addIngredient(food1, 100);
        meal.addIngredient(food2, 50);
        assertEquals("Meal{name='Pizza', ingredients=[Food{name='Cheese', caloriesPer100=100.0, gramsPerServing=100g}=100.0," +
                " Food{name='Tomato', caloriesPer100=20.0, " + "gramsPerServing=100g}=50.0]}", meal.toString());
    }
}