package ulb.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumedFoodTest {

    @Test
    void equalsReturnsTrueForIdenticalObjects() {
        ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
        ConsumedFood food2 = food1;
        assertTrue(food1.equals(food2));
    }

    @Test
    void equalsReturnsFalseForDifferentObjects() {
        ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
        ConsumedFood food2 = new ConsumedFood("Banana", 105, 100, "g");
        assertFalse(food1.equals(food2));
    }

    @Test
    void equalsReturnsFalseForNull() {
        ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
        ConsumedFood food2 = null;
        assertFalse(food1.equals(food2));
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        ConsumedFood food1 = new ConsumedFood("Apple", 52, 100, "g");
        String food2 = "Apple";
        assertFalse(food1.equals(food2));
    }

    @Test
    void toStringReturnsCorrectFormatForValidConsumedFood() {
        ConsumedFood food = new ConsumedFood("Apple", 52, 100, "g");
        String expected = "ConsumedFood{name='Apple', quantity=100.0, calories=52.0, unit='g'}";
        assertEquals(expected, food.toString());
    }

    @Test
    void toStringHandlesNullValues() {
        ConsumedFood food = new ConsumedFood(null, 0, 0, null);
        String expected = "ConsumedFood{name='null', quantity=0.0, calories=0.0, unit='null'}";
        assertEquals(expected, food.toString());
    }
}