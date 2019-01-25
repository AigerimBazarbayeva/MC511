package fh.ooe.mcm.inactivitytracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fh.ooe.mcm.inactivitytracker.utils.CaloriesCalculator;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void caloriesCalculatorWorks() {

        Random r = new Random();
        List<String> activities = new ArrayList<>();

        for(int i = 0; i < 5760; i++) {
            activities.add(PhysicalActivities.classes.get(1));//r.nextInt(6)));
        }

        double caloriesBurned = CaloriesCalculator.getCaloriesBurned(activities, 60);

        assertEquals(6070.588236, caloriesBurned, 0.2);

    }


}