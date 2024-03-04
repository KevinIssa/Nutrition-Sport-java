package ulb.models;

import org.junit.Assert;
import org.junit.Test;
import ulb.models.Sport;

public class TestCalorieCalculator {
    @Test
    public void testcompute (){
        CalorieCalculator caloriecalc = new CalorieCalculator(Sport.VOLLEYBALL, 3, 60,15);
        Assert.assertEquals(71, caloriecalc.compute(), 1);

    }

    @Test
    public void testrunning(){
        CalorieCalculator caloriecalc1 =  new CalorieCalculator(Sport.RUNNING, 3, 40,15);
        CalorieCalculator caloriecalc3 =  new CalorieCalculator(Sport.RUNNING, 1, 80,60);
        Assert.assertEquals(137, caloriecalc1.compute(), 1);
        Assert.assertEquals(672, caloriecalc3.compute(), 1);
    }
    @Test
    public void testvolleyball(){
        CalorieCalculator caloriecalc1 =  new CalorieCalculator(Sport.VOLLEYBALL, 3, 40,15);
        CalorieCalculator caloriecalc3 =  new CalorieCalculator(Sport.VOLLEYBALL, 1, 80,60);
        Assert.assertEquals(47, caloriecalc1.compute(), 1);
        Assert.assertEquals(252, caloriecalc3.compute(), 1);
    }

    @Test
    public void testwalking(){
        CalorieCalculator caloriecalc1 =  new CalorieCalculator(Sport.WALKING, 3, 40,15);
        CalorieCalculator caloriecalc3 =  new CalorieCalculator(Sport.WALKING, 1, 80,60);
        Assert.assertEquals(47, caloriecalc1.compute(), 1);
        Assert.assertEquals(252, caloriecalc3.compute(), 1);

    }
    @Test
    public void testbiking(){
        CalorieCalculator caloriecalc3 =  new CalorieCalculator(Sport.BIKING, 3, 40,15);
        CalorieCalculator caloriecalc1 =  new CalorieCalculator(Sport.BIKING, 1, 80,60);
        Assert.assertEquals(105, caloriecalc3.compute(), 1);
        Assert.assertEquals(336, caloriecalc1.compute(), 1);
    }
    @Test
    public void testswimming(){
        CalorieCalculator caloriecalc3 =  new CalorieCalculator(Sport.SWIMMING, 3, 40,15);
        CalorieCalculator caloriecalc2 =  new CalorieCalculator(Sport.SWIMMING, 2, 80,60);
        Assert.assertEquals(105, caloriecalc3.compute(), 1);
        Assert.assertEquals(672, caloriecalc2.compute(), 1);
    }

}
