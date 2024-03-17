package ulb.models;

import org.junit.Test;

public class TestMeal {
    @Test
    public void testAddMeal(){
        Meal customMeal = new Meal("Omelette aux poivrons");
        Food egg = new Food("Egg", 97, "1 egg (60 g)");
        Food pepper = new Food("Pepper", 27, "1 pepper (75 g)");
        customMeal.addIngredient(egg, 3);
        customMeal.addIngredient(pepper, 1);
        customMeal.save();
    }
}
